/*
 * Copyright 2020 ChenJun (power4j@outlook.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.power4j.ji.admin.modules.security.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.power4j.ji.admin.modules.security.service.AuthService;
import com.power4j.ji.admin.modules.social.common.service.SocialBindingService;
import com.power4j.ji.admin.modules.sys.constant.StatusEnum;
import com.power4j.ji.admin.modules.sys.entity.SysResource;
import com.power4j.ji.admin.modules.sys.entity.SysRole;
import com.power4j.ji.admin.modules.sys.entity.SysUser;
import com.power4j.ji.admin.modules.sys.service.SysResourceService;
import com.power4j.ji.admin.modules.sys.service.SysRoleService;
import com.power4j.ji.admin.modules.sys.service.SysUserService;
import com.power4j.ji.common.core.constant.SecurityConstant;
import com.power4j.ji.common.security.LoginUser;
import com.power4j.ji.common.security.social.SocialLoginException;
import com.power4j.ji.common.security.social.SocialLoginHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final SocialBindingService socialBindingService;

	private final SysUserService sysUserService;

	private final SysRoleService sysRoleService;

	private final SysResourceService sysResourceService;

	/**
	 * Key: Social type
	 */
	private final Map<String, SocialLoginHandler> handlerMap;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return sysUserService.getByUsername(username).map(this::buildUserDetails)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("用户不存在:%s", username)));
	}

	public String getSocialLoginId(String type, String code) throws SocialLoginException {
		final SocialLoginHandler handler = Objects.nonNull(handlerMap) ? handlerMap.get(type) : null;
		if (Objects.isNull(handler)) {
			throw new SocialLoginException("Not support:" + type);
		}

		String openId = handler.getUserId(code);
		if (Objects.isNull(openId)) {
			throw new SocialLoginException("第三方系统返回用户ID为空");
		}
		return openId;
	}

	@Override
	public UserDetails loadBySocial(String socialKey, String state) throws UsernameNotFoundException {
		String openId;
		try {
			openId = getSocialLoginId(socialKey, state);
		}
		catch (SocialLoginException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
		return socialBindingService.findByOpenId(socialKey, openId).flatMap(o -> sysUserService.getByUserId(o.getUid()))
				.map(this::buildUserDetails).orElseThrow(() -> new UsernameNotFoundException("用户不存在,请先绑定"));
	}

	protected UserDetails buildUserDetails(SysUser user) throws UsernameNotFoundException {
		Set<String> roles = sysRoleService.listForUser(user.getUsername(), null).stream()
				.filter(o -> StatusEnum.NORMAL.getValue().equals(o.getStatus())).map(SysRole::getCode)
				.collect(Collectors.toSet());

		Set<String> resources = sysResourceService.listForRoles(roles).stream()
				.filter(o -> CharSequenceUtil.isNotBlank(o.getPermission())).map(SysResource::getPermission)
				.collect(Collectors.toSet());

		List<String> authorityCodes = new ArrayList<>(32);
		authorityCodes.addAll(roles.stream().map(o -> SecurityConstant.ROLE_PREFIX + o).collect(Collectors.toList()));
		authorityCodes.addAll(resources);
		List<GrantedAuthority> authorityList = authorityCodes.stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		LoginUser loginUser = new LoginUser(user.getUsername(), user.getPassword(), authorityList);
		loginUser.setUid(user.getId());
		loginUser.setName(user.getName());
		return loginUser;
	}

}
