package com.abc.mango.admin.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {
	
	@Bean
	public DefaultKaptcha producer() {
		Properties properties = new Properties();
		properties.put("kaptcha.border", "no");
		//properties.put("kaptcha.textproducer.font.color", "black");
		properties.put("kaptcha.textproducer.font.color", "red");
		properties.put("kaptcha.textproducer.char.space", "5");
		properties.put("kaptcha.textproducer.char.length", "4");
		properties.put("kaptcha.textproducer.char.string", "0123456789");//默认字符 abcde2345678gfynmnpwx
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
