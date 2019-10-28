package com.abc.mango.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abc.mango.admin.entity.SysUser;
import com.abc.mango.admin.service.ISysUserService;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

	@Autowired
	private ISysUserService sysUserService;

	@RequestMapping(value = "/findById/{id}", method = RequestMethod.POST)
	public SysUser findById(@PathVariable("id") Long id) {
		return sysUserService.getById(id);
	}
}
