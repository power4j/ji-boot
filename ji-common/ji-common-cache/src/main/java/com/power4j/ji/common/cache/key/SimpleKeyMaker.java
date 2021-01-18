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

package com.power4j.ji.common.cache.key;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/6
 * @since 1.0
 */
public class SimpleKeyMaker {

	private final static int MAX_KEY_KEEP = 64;

	/**
	 * 生成 key
	 * @param params
	 * @return
	 */
	public String makeKeyStr(@Nullable Collection<Object> params) {
		if (params == null || params.isEmpty()) {
			return "";
		}
		String key = params.stream().sorted().map(Object::toString).collect(Collectors.joining("_"));
		if (key.length() > MAX_KEY_KEEP) {
			return key.substring(0, MAX_KEY_KEEP) + "_" + Integer.toHexString(key.hashCode());
		}
		return key;
	}

}
