package com.abc.mango.admin.config;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4638628018788766289L;

	private String token;

	public JwtAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public JwtAuthenticationToken(Object principal, Object credentials, String token) {
		super(principal, credentials);
		this.token = token;
	}

	public JwtAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, String token) {
		super(principal, credentials, authorities);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
