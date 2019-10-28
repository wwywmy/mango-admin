package com.abc.mango.admin.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abc.mango.admin.config.JwtAuthenticationToken;
import com.abc.mango.admin.config.SecurityUtils;
import com.abc.mango.admin.entity.SysUser;
import com.abc.mango.admin.service.ISysUserService;
import com.abc.mango.admin.util.PasswordUtils;
import com.abc.mango.admin.vo.HttpResult;
import com.abc.mango.admin.vo.LoginBean;
import com.baomidou.mybatisplus.core.toolkit.IOUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@RestController
public class SysLoginController {

	@Autowired
	private Producer producer;

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到shiro session（注意：如果没有securityManager配置，则暂时无法工作，测试时先注释掉）
		// ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		// 保存到request session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		// out.flush();
		IOUtils.closeQuietly(out);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
		String username = loginBean.getAccount();
		String password = loginBean.getPassword();
		String captcha = loginBean.getCaptcha();

		Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

		if (kaptcha == null) {
			return HttpResult.error("验证码已失效");
		}

		if (!kaptcha.equals(captcha)) {
			return HttpResult.error("验证码不正确");
		}

		SysUser sysUser = sysUserService.getByUsername(username);

		if (sysUser == null) {
			return HttpResult.error("账号不存在");
		}

		if (!PasswordUtils.matches(sysUser.getSalt(), password, sysUser.getPassword())) {
			return HttpResult.error("密码不正确");
		}

		if (sysUser.getStatus() == 0) {
			return HttpResult.error("账号已被锁定，请联系管理员");
		}

		JwtAuthenticationToken jwtAuthenticationToken = SecurityUtils.login(request, username, password,
				authenticationManager);

		return HttpResult.ok(jwtAuthenticationToken);
	}
}
