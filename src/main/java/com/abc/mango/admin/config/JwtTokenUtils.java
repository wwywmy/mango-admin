package com.abc.mango.admin.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2935497246524955171L;
	/**
	 * 用户名称
	 */
	private static final String USERNAME = Claims.SUBJECT;
	/**
	 * 创建时间
	 */
	private static final String CREATED = "created";
	/**
	 * 权限列表
	 */
	private static final String AUTHORITIES = "authorities";
	/**
	 * 密钥
	 */
	private static final String SECRET = "abcdefgh";
	/**
	 * 有效期12小时
	 */
	private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

	/**
	 * 生成令牌
	 *
	 * @param userDetails 用户
	 * @return 令牌
	 */
	public static String generateToken(Authentication authentication) {
		Map<String, Object> claims = new HashMap<>(3);
		claims.put(USERNAME, SecurityUtils.getUsername(authentication));
		claims.put(CREATED, new Date());
		claims.put(AUTHORITIES, authentication.getAuthorities());
		return generateToken(claims);
	}

	/**
	 * 从数据声明生成令牌
	 *
	 * @param claims 数据声明
	 * @return 令牌
	 */
	private static String generateToken(Map<String, Object> claims) {
		Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}

	public static Authentication getAuthenticationFromToken(HttpServletRequest request) {
		Authentication authentication = null;
		String token = JwtTokenUtils.getToken(request);

		if (token != null) {
			// 请求令牌不能为空
			if (SecurityUtils.getAuthentication() == null) {
				// 上下文中 Authentication 为空
				Claims claims = getClaimsFromToken(token);

				if (null == claims) {
					return null;
				}

				String username = claims.getSubject();
				if (null == username) {
					return null;
				}

				if (isTokenExpired(token)) {
					return null;
				}

				Object authors = claims.get(AUTHORITIES);

				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				if (authors != null && authors instanceof List) {
					for (Object object : (List) authors) {
						authorities.add(new GrantedAuthorityImpl((String) ((Map) object).get("authority")));
					}
				}

				authentication = new JwtAuthenticationToken(username, null, authorities, token);

			} else {
				if (validateToken(token, SecurityUtils.getUsername())) {
					// 上下文中 Authentication 非空，且请求停牌合法
					// 直接返回当前登录认证信息
					authentication = SecurityUtils.getAuthentication();
				}
			}
		}

		return authentication;
	}

	public static String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String tokenHead = "Bearer ";

		if (null == token) {
			token = request.getHeader("token");
		} else if (token.contains(tokenHead)) {
			token = token.substring(tokenHead.length());
		}

		if ("" == token) {
			token = null;
		}

		return token;
	}

	/**
	 * 判断令牌是否过期
	 *
	 * @param token 令牌
	 * @return 是否过期
	 */
	public static Boolean isTokenExpired(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			Date expiration = claims.getExpiration();
			return expiration.before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 从令牌中获取数据声明
	 *
	 * @param token 令牌
	 * @return 数据声明
	 */
	private static Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	/**
	 * 验证令牌
	 * 
	 * @param token
	 * @param username
	 * @return
	 */
	public static Boolean validateToken(String token, String username) {
		String userName = getUsernameFromToken(token);
		return (userName.equals(username) && !isTokenExpired(token));
	}

	/**
	 * 从令牌中获取用户名
	 *
	 * @param token 令牌
	 * @return 用户名
	 */
	public static String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}
}
