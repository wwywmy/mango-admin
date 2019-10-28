package com.abc.mango.admin.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.abc.mango.admin.util.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

	public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
		setUserDetailsService(userDetailsService);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			log.info("Authentication failed:no credentials provided");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();
		String salt = ((JwtUserDetails) userDetails).getSalt();

		if (!new PasswordEncoder(salt).matches(userDetails.getPassword(), presentedPassword)) {
			log.info("Authentication failed:password does not match");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

	}

}
