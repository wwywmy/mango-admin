package com.abc.mango.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.abc.mango.admin.security.JwtAuthenticationFilter;
import com.abc.mango.admin.security.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll().antMatchers("/webjars/**").permitAll().antMatchers("/druid/**").permitAll()
				.antMatchers("/").permitAll().antMatchers("login").permitAll().antMatchers("/swagger-ui.html")
				.permitAll().antMatchers("/swagger-resources/**").permitAll().antMatchers("/v2/api-docs/**").permitAll()
				.antMatchers("/webjars/springfox-swagger-ui/**").permitAll().antMatchers("/captcha.jpg**").permitAll()
				.antMatchers("/actuator/**").permitAll().anyRequest().authenticated();

		httpSecurity.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

		httpSecurity.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()),
				UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}
}
