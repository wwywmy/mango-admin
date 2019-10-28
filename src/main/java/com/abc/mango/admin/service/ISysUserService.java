package com.abc.mango.admin.service;

import com.abc.mango.admin.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2019-10-27
 */
public interface ISysUserService extends IService<SysUser> {

	SysUser getByUsername(String username);
}
