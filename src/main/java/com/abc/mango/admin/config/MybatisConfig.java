package com.abc.mango.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.abc.mango.admin.mapper")
public class MybatisConfig {

}
