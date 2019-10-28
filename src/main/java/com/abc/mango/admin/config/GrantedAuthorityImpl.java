package com.abc.mango.admin.config;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5339546832196026013L;
	
	private String authority;

	public GrantedAuthorityImpl(String authority) {
		this.authority = authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

}
