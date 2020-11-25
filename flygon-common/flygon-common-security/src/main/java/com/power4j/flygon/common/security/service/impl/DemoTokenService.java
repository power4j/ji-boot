package com.power4j.flygon.common.security.service.impl;

import cn.hutool.core.lang.UUID;
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

	private final Map<String, Authentication> authenticationMap = new ConcurrentHashMap<>(32);

	private final Map<String, ApiToken> tokenMap = new ConcurrentHashMap<>(32);

	@Override
	public Authentication loadAuthentication(String tokenValue) {
		ApiToken apiToken = tokenMap.get(tokenValue);
		if (apiToken == null || apiToken.getExpireIn().isBefore(LocalDateTime.now())) {
			authenticationMap.remove(tokenValue);
			return null;
		}
		return authenticationMap.get(tokenValue);
	}

	@Override
	public boolean deleteToken(String tokenValue) {
		tokenMap.remove(tokenValue);
		return authenticationMap.remove(tokenValue) != null;
	}

	@Override
	public ApiToken createToken(Authentication authentication) {
		ApiToken apiToken = new ApiToken().setToken(UUID.fastUUID().toString())
				.setExpireIn(LocalDateTime.now().plusHours(48L)).setIssuedBy("power4j.com");
		tokenMap.put(apiToken.getToken(), apiToken);
		authenticationMap.put(apiToken.getToken(), authentication);
		return apiToken;
	}

}
