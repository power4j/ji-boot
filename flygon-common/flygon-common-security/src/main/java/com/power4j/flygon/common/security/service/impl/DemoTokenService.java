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
		ApiToken apiToken = new ApiToken();
		apiToken.setToken(UUID.fastUUID().toString());
		apiToken.setExpireIn(LocalDateTime.now().plusHours(48L));
		apiToken.setUsername(loginUser.getUsername());
		apiToken.setName("power4j");
		apiToken.setUuid(loginUser.getUid());
		apiToken.setIssuedBy("power4j.com");
		tokenMap.put(apiToken.getToken(), apiToken);
		return apiToken;
	}

}
