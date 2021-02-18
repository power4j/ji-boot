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

package com.power4j.ji.common.security.util;

import cn.hutool.core.text.CharSequenceUtil;
import com.power4j.ji.common.core.constant.SecurityConstant;
import com.power4j.ji.common.security.LoginUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/23
 * @since 1.0
 */
@UtilityClass
public class SecurityUtil {

	public Optional<Authentication> getAuthentication() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
	}

	/**
	 * 当前登录用户
	 * @return
	 */
	public Optional<LoginUser> getLoginUser() {
		return getAuthentication().map(authentication -> {
			Object principal = authentication.getPrincipal();
			return (principal instanceof LoginUser) ? (LoginUser) principal : null;
		});
	}

	/**
	 * 当前登录用户的用户名
	 * @return
	 */
	public Optional<String> getLoginUsername() {
		return getLoginUser().map(loginUser -> loginUser.getUsername());
	}

	/**
	 * 当前登录用户的ID
	 * @return
	 */
	public Optional<Long> getLoginUserId() {
		return getLoginUser().map(loginUser -> loginUser.getUid());
	}

	/**
	 * 当前登录用户的权限列表
	 * @return
	 */
	public Set<String> getLoginUserAuthorities() {
		return getLoginUser().map(
				loginUser -> loginUser.getAuthorities().stream().map(o -> o.getAuthority()).collect(Collectors.toSet()))
				.orElse(Collections.emptySet());
	}

	/**
	 * 当前登录用户的角色列表
	 * @return
	 */
	public Set<String> getLoginUserRoles() {
		return getLoginUserAuthorities().stream().filter(o -> o.startsWith(SecurityConstant.ROLE_PREFIX))
				.map(o -> CharSequenceUtil.removePrefix(o, SecurityConstant.ROLE_PREFIX)).collect(Collectors.toSet());
	}

}
