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

package com.power4j.ji.admin.modules.demo.job;

import com.power4j.ji.common.data.dict.annotation.DictValue;
import com.power4j.ji.common.data.dict.annotation.Item;
import com.power4j.ji.common.data.dict.annotation.MapDict;

import java.util.function.Function;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/27
 * @since 1.0
 */
@MapDict(
		items = { @Item(value = "1", label = "一", style = "info"), @Item(value = "2", label = "二", style = "warning") })
public enum DemoTypeEnum {

	/**
	 * One
	 */
	One("one"),
	/**
	 * Two
	 */
	Two("two");

	private final String value;

	DemoTypeEnum(String value) {
		this.value = value;
	}

	@DictValue
	public String getValue() {
		return value;
	}

	/**
	 * 解析
	 * @param value 被解析的数据,可以是null
	 * @param defValue 默认值
	 * @return 如果解析失败返回默认值
	 */
	public static DemoTypeEnum parseOrDefault(final String value, final DemoTypeEnum defValue) {
		if (value == null) {
			return defValue;
		}
		for (DemoTypeEnum o : DemoTypeEnum.values()) {
			if (o.getValue().equals(value)) {
				return o;
			}
		}
		return defValue;
	}

	/**
	 * 解析
	 * @param value 被解析的数据
	 * @return 如果解析失败返回 null
	 */
	public static DemoTypeEnum parseOrNull(final String value) {
		return parseOrDefault(value, null);
	}

	/**
	 * 解析
	 * @param value 被解析的数据
	 * @param thrower 异常抛出器
	 * @return 如果解析失败抛出异常
	 */
	public static DemoTypeEnum parseOrThrow(final String value, Function<String, RuntimeException> thrower) {
		DemoTypeEnum o = parseOrDefault(value, null);
		if (o == null) {
			throw thrower.apply(value);
		}
		return o;
	}

	/**
	 * 解析
	 * @param value 被解析的数据
	 * @return 如果解析失败抛出 IllegalArgumentException
	 */
	public static DemoTypeEnum parse(final String value) throws IllegalArgumentException {
		return parseOrThrow(value, (v) -> new IllegalArgumentException("Invalid value : " + v));
	}

}
