package ${package.Mapper};

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ${package.Entity}.${entity};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.javafaker.Faker;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ${entity}Test {

	@Resource
	private ${entity}Mapper ${entity?uncap_first}Mapper;

	@Test
	public void selectByIdTest() {
		${entity} ${entity?uncap_first} = ${entity?uncap_first}Mapper.selectById(10L);
		log.info(${entity?uncap_first}.toString());
	}
	
	@Test
	public void selectList() {
		QueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>();
		List<${entity}> ${entity?uncap_first}List = ${entity?uncap_first}Mapper.selectList(queryWrapper);
		${entity?uncap_first}List.forEach(e -> {
			log.info(e.toString());
		});
	}

	@Test
	public void insert() {
		${entity} ${entity?uncap_first} = new ${entity}();
		<#list table.fields as field>
		<#if field.name == "id">
		${entity?uncap_first}.set${field.capitalName}(IdUtil.createSnowflake(1L, 1L).nextId());
		<#elseif field.name == "name">
		${entity?uncap_first}.set${field.capitalName}(new Faker(Locale.CHINA).name().fullName());
		<#else>
		${entity?uncap_first}.set${field.capitalName}(RandomUtil.randomString(4));
        </#if>		
		</#list>
		
		int count = ${entity?uncap_first}Mapper.insert(${entity?uncap_first});
		log.info("count={}",count);
		log.info("${entity?uncap_first}={}",${entity?uncap_first});
	}
	
}
