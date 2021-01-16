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

package com.power4j.flygon.common.core.config;

import com.power4j.flygon.common.core.constant.CommonConstant;
import com.power4j.flygon.common.core.util.NumUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/14
 * @since 1.0
 */
@Getter
@Setter
@ConfigurationProperties(CommonConstant.PROPERTY_PREFIX)
public class FlygonProperties implements EnvironmentAware, EnvironmentCapable {

	private Environment environment;

	/**
	 * 放置一些自定义配置
	 */
	private Map<String, String> prop = new HashMap<>();

	public Optional<Long> getLong(String key) {
		return getStr(key).map(v -> NumUtil.parseLong(v));
	}

	public Optional<Integer> getInt(String key) {
		return getStr(key).map(v -> NumUtil.parseInt(v));
	}

	public Optional<Boolean> getBool(String key) {
		return getStr(key).map(v -> {
			if (Boolean.TRUE.toString().equalsIgnoreCase(v)) {
				return Boolean.TRUE;
			}
			else if (Boolean.FALSE.toString().equalsIgnoreCase(v)) {
				return Boolean.FALSE;
			}
			else {
				return null;
			}
		});
	}

	public Optional<String> getStr(String key) {
		return Optional.ofNullable(prop.get(key));
	}

	public boolean exists(String key) {
		return prop.containsKey(key);
	}

	public Map<String, String> dump() {
		return new HashMap<>(prop);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public Environment getEnvironment() {
		return environment;
	}

}
