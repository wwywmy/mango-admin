package com.abc.mango.admin.mapper;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.abc.mango.admin.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.javafaker.Faker;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysUserTest {

	@Resource
	private SysUserMapper sysUserMapper;

	@Test
	public void selectByIdTest() {
		SysUser sysUser = sysUserMapper.selectById(10L);
		log.info(sysUser.toString());
	}
	
	@Test
	public void selectList() {
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
		List<SysUser> sysUserList = sysUserMapper.selectList(queryWrapper);
		sysUserList.forEach(e -> {
			log.info(e.toString());
		});
	}

	@Test
	public void insert() {
		SysUser sysUser = new SysUser();
		sysUser.setId(IdUtil.createSnowflake(1L, 1L).nextId());
		sysUser.setName(new Faker(Locale.CHINA).name().fullName());
		sysUser.setNickName(RandomUtil.randomString(4));
		sysUser.setAvatar(RandomUtil.randomString(4));
		sysUser.setPassword(RandomUtil.randomString(4));
		sysUser.setSalt(RandomUtil.randomString(4));
		sysUser.setEmail(RandomUtil.randomString(4));
		sysUser.setMobile(RandomUtil.randomString(4));
		sysUser.setStatus(Integer.parseInt(RandomUtil.randomString("01",1)));
		sysUser.setDeptId(RandomUtil.randomLong());
		sysUser.setCreateBy(RandomUtil.randomString(4));
		sysUser.setCreateTime(new Date());
		sysUser.setLastUpdateBy(RandomUtil.randomString(4));
		sysUser.setLastUpdateTime(new Date());
		sysUser.setDelFlag(RandomUtil.randomBoolean()?-1:0);
		
		int count = sysUserMapper.insert(sysUser);
		log.info("count={}",count);
		log.info("sysUser={}",sysUser);
	}
	
	@Test
	public void deleteByIdTest() {
		int count = sysUserMapper.deleteById(1L);
		log.info(Integer.toString(count));
	}
	
}
