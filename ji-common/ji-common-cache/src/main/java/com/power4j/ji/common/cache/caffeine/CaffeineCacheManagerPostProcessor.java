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

package com.power4j.ji.common.cache.caffeine;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/6
 * @since 1.0
 */
@Slf4j
@ConditionalOnClass({ Caffeine.class, CaffeineCacheManager.class })
@ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "caffeine")
public class CaffeineCacheManagerPostProcessor implements ApplicationContextAware, BeanPostProcessor {

	private ApplicationContext applicationContext;

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean.getClass().equals(CaffeineCacheManager.class)) {
			log.info("Wrap {} : {}", beanName, bean.getClass().getName());
			return getCaffeineCacheManager();
		}
		return bean;
	}

	private CaffeineCacheManager getCaffeineCacheManager() {

		CacheProperties cacheProperties = applicationContext.getBean(CacheProperties.class);
		CacheManagerCustomizers customizers = getBeanOrNull(CacheManagerCustomizers.class);
		Caffeine<Object, Object> caffeine = getBeanOrNull(Caffeine.class);
		CaffeineSpec caffeineSpec = getBeanOrNull(CaffeineSpec.class);
		CacheLoader<Object, Object> cacheLoader = getBeanOrNull(CacheLoader.class);

		CaffeineCacheManager cacheManager = createCacheManager(cacheProperties, caffeine, caffeineSpec, cacheLoader);
		List<String> cacheNames = cacheProperties.getCacheNames();
		if (!CollectionUtils.isEmpty(cacheNames)) {
			cacheManager.setCacheNames(cacheNames);
		}
		return customizers.customize(cacheManager);
	}

	private CaffeineCacheManager createCacheManager(CacheProperties cacheProperties, Caffeine<Object, Object> caffeine,
			CaffeineSpec caffeineSpec, CacheLoader<Object, Object> cacheLoader) {
		CaffeineCacheManager cacheManager = new CaffeineAutoCacheManager();
		setCacheBuilder(cacheProperties, caffeineSpec, caffeine, cacheManager);

		if (cacheLoader != null) {
			cacheManager.setCacheLoader(cacheLoader);
		}
		return cacheManager;
	}

	private void setCacheBuilder(CacheProperties cacheProperties, CaffeineSpec caffeineSpec,
			Caffeine<Object, Object> caffeine, CaffeineCacheManager cacheManager) {
		String specification = cacheProperties.getCaffeine().getSpec();

		if (caffeine != null) {
			cacheManager.setCaffeine(caffeine);
		}
		else if (caffeineSpec != null) {
			cacheManager.setCaffeineSpec(caffeineSpec);
		}
		else if (StringUtils.hasText(specification)) {
			cacheManager.setCacheSpecification(specification);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	protected <T> T getBeanOrNull(Class<T> type) {
		try {
			return applicationContext.getBean(type);
		}
		catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

}
