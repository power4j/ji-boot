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

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/15
 * @since 1.0
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class ThreadStore {

	private final static TransmittableThreadLocal<Map<String, Object>> STORE = new TransmittableThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<>(16);
		}
	};

	public <T> Optional<T> get(String key) {
		return Optional.ofNullable(STORE.get().get(key)).map(o -> (T) o);
	}

	public <T> Optional<T> get(String key, Supplier<T> supplyIfAbsent) {
		return Optional.ofNullable((T) STORE.get().computeIfAbsent(key, (k) -> supplyIfAbsent.get()));
	}

	public <T> T put(String key, T val) {
		STORE.get().put(key, val);
		return val;
	}

	public <T> T putIfAbsent(String key, T val) {
		STORE.get().putIfAbsent(key, val);
		return val;
	}

	public void clear() {
		STORE.remove();
	}

}
