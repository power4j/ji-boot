package com.power4j.flygon.common.security.util;

import com.power4j.flygon.common.security.LoginUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

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

	public Optional<LoginUser> getLoginUser() {
		return getAuthentication().map(authentication -> {
			Object principal = authentication.getPrincipal();
			return (principal instanceof LoginUser) ? (LoginUser) principal : null;
		});
	}

	public Optional<String> getLoginUsername() {
		return getLoginUser().map(loginUser -> loginUser.getUsername());
	}

}
