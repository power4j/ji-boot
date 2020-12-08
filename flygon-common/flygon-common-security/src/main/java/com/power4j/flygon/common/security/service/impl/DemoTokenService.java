package com.power4j.flygon.common.security.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import com.power4j.flygon.common.security.LoginUser;
import com.power4j.flygon.common.security.model.ApiToken;
import com.power4j.flygon.common.security.service.TokenService;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * For test,Not thread safe
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/25
 * @since 1.0
 */
public class DemoTokenService implements TokenService {

	private final Map<String, ApiToken> tokenMap = new ConcurrentHashMap<>(32);

	@Override
	public ApiToken loadApiToken(String tokenValue) {
		ApiToken apiToken = tokenMap.get(tokenValue);
		if (apiToken == null || apiToken.getExpireIn().isBefore(LocalDateTime.now())) {
			return null;
		}
		return apiToken;
	}

	@Override
	public boolean deleteToken(String tokenValue) {
		return tokenMap.remove(tokenValue) != null;
	}

	@Override
	public ApiToken createToken(Authentication authentication) {
		Assert.isInstanceOf(LoginUser.class, authentication.getPrincipal());
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		ApiToken apiToken = new ApiToken().setToken(UUID.fastUUID().toString())
				.setExpireIn(LocalDateTime.now().plusHours(48L)).setUsername(loginUser.getUsername()).setName("power4j")
				.setUuid(loginUser.getUid()).setIssuedBy("power4j.com");
		tokenMap.put(apiToken.getToken(), apiToken);
		return apiToken;
	}

}
