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

package com.power4j.ji.common.cache.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.power4j.ji.common.cache.caffeine.CaffeineAutoCacheManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/29
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Caffeine.class, CaffeineCacheManager.class })
@ConditionalOnProperty(prefix = CacheProperties.PREFIX, name = "type", havingValue = "caffeine")
public class CaffeineCacheConfig {

	@Bean
	public CaffeineCacheManager cacheManager(CacheProperties cacheProperties,
			ObjectProvider<Caffeine<Object, Object>> caffeine, ObjectProvider<CaffeineSpec> caffeineSpec,
			ObjectProvider<CacheLoader<Object, Object>> cacheLoader) {
		CaffeineCacheManager cacheManager = createCacheManager(cacheProperties, caffeine, caffeineSpec, cacheLoader);
		List<String> cacheNames = cacheProperties.getCacheNames();
		if (!CollectionUtils.isEmpty(cacheNames)) {
			cacheManager.setCacheNames(cacheNames);
		}
		return cacheManager;
	}

	private CaffeineCacheManager createCacheManager(CacheProperties cacheProperties,
			ObjectProvider<Caffeine<Object, Object>> caffeine, ObjectProvider<CaffeineSpec> caffeineSpec,
			ObjectProvider<CacheLoader<Object, Object>> cacheLoader) {
		CaffeineCacheManager cacheManager = new CaffeineAutoCacheManager();
		setCacheBuilder(cacheProperties, caffeineSpec.getIfAvailable(), caffeine.getIfAvailable(), cacheManager);
		cacheLoader.ifAvailable(cacheManager::setCacheLoader);
		return cacheManager;
	}

	private void setCacheBuilder(CacheProperties cacheProperties, @Nullable CaffeineSpec caffeineSpec,
			@Nullable Caffeine<Object, Object> caffeine, CaffeineCacheManager cacheManager) {
		String specification = cacheProperties.getCaffeine().getSpec();
		if (StringUtils.hasText(specification)) {
			cacheManager.setCacheSpecification(specification);
		}
		else if (caffeineSpec != null) {
			cacheManager.setCaffeineSpec(caffeineSpec);
		}
		else if (caffeine != null) {
			cacheManager.setCaffeine(caffeine);
		}
	}

}
