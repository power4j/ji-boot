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

package com.power4j.ji.common.data.crud.constant;

import java.util.function.Function;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
public enum SysCtlFlagEnum {

	/**
	 * 普通数据
	 */
	NORMAL(0),
	/**
	 * 系统保护数据
	 */
	SYS_LOCKED(1);

	private final int value;

	SysCtlFlagEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	/**
	 * 解析
	 * @param value 被解析的数据,可以是null
	 * @param defValue 默认值
	 * @return 如果解析失败返回默认值
	 */
	public static SysCtlFlagEnum parseOrDefault(final Integer value, final SysCtlFlagEnum defValue) {
		if (value == null) {
			return defValue;
		}
		for (SysCtlFlagEnum o : SysCtlFlagEnum.values()) {
			if (o.value == value.intValue()) {
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
	public static SysCtlFlagEnum parseOrNull(final Integer value) {
		return parseOrDefault(value, null);
	}

	/**
	 * 解析
	 * @param value 被解析的数据
	 * @param thrower 异常抛出器
	 * @return 如果解析失败抛出异常
	 */
	public static SysCtlFlagEnum parseOrThrow(final Integer value, Function<Integer, RuntimeException> thrower) {
		SysCtlFlagEnum o = parseOrDefault(value, null);
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
	public static SysCtlFlagEnum parse(final Integer value) throws IllegalArgumentException {
		return parseOrThrow(value, (v) -> new IllegalArgumentException("Invalid value : " + v));
	}

}
