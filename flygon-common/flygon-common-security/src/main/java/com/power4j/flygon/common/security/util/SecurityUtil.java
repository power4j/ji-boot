package com.power4j.flygon.common.security.util;

import com.power4j.flygon.common.security.LoginUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
	 * 当前登录用户的角色列表
	 * @return
	 */
	public List<String> getLoginUserRoles() {
		return getLoginUser()
				.map(loginUser -> loginUser.getAuthorities().stream().map(o -> o.getAuthority()).collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}
}
