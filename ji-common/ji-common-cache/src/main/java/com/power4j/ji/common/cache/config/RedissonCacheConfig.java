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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.power4j.ji.common.cache.redisson.RedissonAutoCacheManager;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/29
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = CacheProperties.PREFIX, name = "type", havingValue = "redisson")
public class RedissonCacheConfig {

	@Bean
	@ConditionalOnMissingBean
	public Codec jsonJacksonCodec(ObjectProvider<ObjectMapper> mappers) {
		ObjectMapper objectMapper = mappers.getIfAvailable();
		return objectMapper == null ? new JsonJacksonCodec() : new JsonJacksonCodec(objectMapper);
	}

	@Bean
	@ConditionalOnBean(Codec.class)
	public RedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer(Codec codec) {
		return (configuration -> configuration.setCodec(codec));
	}

	@Bean
	public CacheManager cacheManager(CacheProperties cacheProperties, RedissonClient redissonClient,
			ObjectProvider<Codec> codecs) {
		CacheConfig config = new CacheConfig(cacheProperties.getRedisson().getTtl().toMillis(),
				cacheProperties.getRedisson().getMaxIdleTime().toMillis());
		config.setMaxSize(cacheProperties.getRedisson().getMaxSize());
		RedissonAutoCacheManager cacheManager = new RedissonAutoCacheManager(redissonClient,
				cacheProperties.getRedisson().getConfigFile());
		cacheManager.setGlobalConfig(config);
		Codec codec = codecs.getIfAvailable();
		if (codec != null) {
			cacheManager.setCodec(codec);
		}
		return cacheManager;
	}

}
