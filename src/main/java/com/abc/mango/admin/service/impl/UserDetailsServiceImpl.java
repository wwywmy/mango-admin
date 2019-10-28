package com.abc.mango.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.abc.mango.admin.entity.SysUser;
import com.abc.mango.admin.service.ISysUserService;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ISysUserService sysUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = sysUserService.getByUsername(username);
		if (sysUser == null) {
			throw new UsernameNotFoundException("该用户不存在");
		}

		return null;
	}

}
