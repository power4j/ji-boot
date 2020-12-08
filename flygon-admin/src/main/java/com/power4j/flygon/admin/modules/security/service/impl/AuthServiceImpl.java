package com.power4j.flygon.admin.modules.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.power4j.flygon.admin.modules.security.service.AuthService;
import com.power4j.flygon.admin.modules.sys.dao.SysUserMapper;
import com.power4j.flygon.admin.modules.sys.entity.SysUser;
import com.power4j.flygon.common.security.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final SysUserMapper sysUserMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Wrapper<SysUser> wrapper = new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername,
				username);
		SysUser entity = sysUserMapper.selectOne(wrapper);
		if (entity == null) {
			log.debug(String.format("用户不存在:{}", username));
			throw new UsernameNotFoundException(String.format("用户不存在:{}", username));
		}
		LoginUser loginUser = new LoginUser(entity.getUsername(), entity.getPassword(), Collections.emptyList());
		loginUser.setUid(entity.getId());
		loginUser.setName(entity.getName());
		return loginUser;
	}

}
