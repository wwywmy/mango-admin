package com.abc.mango.admin.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.abc.mango.admin.config.SecurityUtils;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter{

	@Autowired
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//获取token，并且检查登录状态
		SecurityUtils.checkAuthentication(request);
		chain.doFilter(request, response);
		
	}

	
}
