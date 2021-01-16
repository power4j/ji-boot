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

package com.power4j.flygon.common.cache.caffeine;

import com.github.benmanes.caffeine.cache.Policy;
import com.power4j.flygon.common.cache.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/6
 * @since 1.0
 */
@Slf4j
public class CaffeineAutoCacheManager extends CaffeineCacheManager {

	@Override
	protected com.github.benmanes.caffeine.cache.Cache<Object, Object> createNativeCaffeineCache(String name) {
		com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCaffeineCache = super.createNativeCaffeineCache(
				name);
		Duration duration = CacheUtil.parseTtl(name).orElse(null);
		if (duration != null) {
			Policy.Expiration<Object, Object> expiration = nativeCaffeineCache.policy().expireAfterWrite().orElse(null);
			if (expiration == null) {
				log.warn("time-to-live expiration policy does not support by this cache,check caffeine config");
			}
			else {
				expiration.setExpiresAfter(duration.toMillis(), TimeUnit.MILLISECONDS);
			}
		}
		return nativeCaffeineCache;
	}

}
