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

package com.power4j.ji.common.captcha.config;

import com.power4j.ji.common.captcha.filter.CaptchaFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/11
 * @since 1.0
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaAutoConfig {

	@Bean
	public CaptchaFilter captchaFilter(CaptchaProperties captchaProperties, CacheManager cacheManager) {
		if (captchaProperties.getConsumer() == null || captchaProperties.getConsumer().isEmpty()) {
			log.warn("No captcha consumer");
		}
		CaptchaFilter filter = new CaptchaFilter(cacheManager);
		filter.setCodeUrl(captchaProperties.getCodeUrl());
		filter.setValidateUrls(captchaProperties.getConsumer());
		filter.setExpireSeconds(captchaProperties.getExpire());
		return filter;
	}

}
