package com.abc.mango.admin.service.impl;

import org.springframework.stereotype.Service;

import com.abc.mango.admin.entity.SysUser;
import com.abc.mango.admin.mapper.SysUserMapper;
import com.abc.mango.admin.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-10-27
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	public SysUser getByUsername(String username) {
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();

		queryWrapper.lambda().eq(SysUser::getNickName, username);

		SysUser sysUser = super.getBaseMapper().selectOne(queryWrapper);

		return sysUser;
	}
}
