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

package com.power4j.ji.common.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static Optional<Object> getBean(String name) {
		try {
			return Optional.of(applicationContext.getBean(name));
		}
		catch (NoSuchBeanDefinitionException e) {
			return Optional.empty();
		}
	}

	public static <T> Optional<T> getBean(Class<T> requiredType) {
		try {
			return Optional.of(applicationContext.getBean(requiredType));
		}
		catch (NoSuchBeanDefinitionException | BeanNotOfRequiredTypeException e) {
			return Optional.empty();
		}
	}

	public static <T> Optional<T> getBean(String name, Class<T> requiredType) {
		try {
			return Optional.of(applicationContext.getBean(name, requiredType));
		}
		catch (NoSuchBeanDefinitionException | BeanNotOfRequiredTypeException e) {
			return Optional.empty();
		}
	}

	public static void publishEvent(ApplicationEvent event) {
		applicationContext.publishEvent(event);
	}

	public static void publishEvent(Object event) {
		applicationContext.publishEvent(event);
	}

}
