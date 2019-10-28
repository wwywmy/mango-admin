package com.abc.mango.admin.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginBean {
	private String account;
	private String password;
	private String captcha;
}
