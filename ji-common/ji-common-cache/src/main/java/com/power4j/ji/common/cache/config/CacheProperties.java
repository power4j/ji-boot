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

import com.power4j.ji.common.core.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/29
 * @since 1.0
 */
@Getter
@Setter
@ConfigurationProperties(CacheProperties.PREFIX)
public class CacheProperties {

	public static final String PREFIX = CommonConstant.PROPERTY_PREFIX + ".cache";

	private CacheType type = CacheType.NONE;

	private Caffeine caffeine = new Caffeine();

	private Redisson redisson = new Redisson();

	/**
	 * Comma-separated list of cache names to create if supported by the underlying cache
	 * manager. Usually, this disables the ability to create additional caches on-the-fly.
	 */
	private List<String> cacheNames = new ArrayList<>();

	/**
	 * Caffeine 配置.
	 */
	@Getter
	@Setter
	public static class Caffeine {

		/**
		 * The spec to use to create caches. See CaffeineSpec for more details on the spec
		 * format.
		 */
		private String spec;

	}

	/**
	 * Redisson 配置
	 */
	@Getter
	@Setter
	public static class Redisson {

		/**
		 * ttl 默认值(毫秒)
		 */
		private long ttl = 0;

		/**
		 * maxIdleTime 默认值(毫秒)
		 */
		private long maxIdleTime = 0;

		/**
		 * maxSize 默认值
		 */
		private int maxSize = 0;

		/**
		 * 配置文件位置,比如 {@code classpath:/cache-config.yaml}
		 */
		@Nullable
		private String configFile;

	}

}
