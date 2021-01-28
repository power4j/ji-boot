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

package com.power4j.ji.admin.modules.sys.constant;

import com.power4j.ji.common.data.dict.annotation.DictValue;
import com.power4j.ji.common.data.dict.annotation.Label;
import com.power4j.ji.common.data.dict.annotation.MapDict;
import com.power4j.ji.common.data.dict.annotation.Remarks;
import com.power4j.ji.common.data.dict.annotation.Styled;

import java.util.function.Function;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/28
 * @since 1.0
 */
@MapDict(code = "sys_resource_type", name = "资源类型")
public enum ResourceTypeEnum {

	/**
	 * 菜单
	 */
	@Label("菜单")
	@Styled("success")
	MENU("1"),
	/**
	 * 按钮
	 */
	@Label("按钮")
	@Styled("warning")
	BUTTON("2"),
	/**
	 * 路由
	 */
	@Label("路由")
	@Styled("info")
	@Remarks("路由不会展示")
	ROUTE("3");

	@DictValue
	private final String value;

	ResourceTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * 解析
	 * @param value 被解析的数据,可以是null
	 * @param defValue 默认值
	 * @return 如果解析失败返回默认值
	 */
	public static ResourceTypeEnum parseOrDefault(final String value, final ResourceTypeEnum defValue) {
		if (value == null) {
			return defValue;
		}
		for (ResourceTypeEnum o : ResourceTypeEnum.values()) {
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
	public static ResourceTypeEnum parseOrNull(final String value) {
		return parseOrDefault(value, null);
	}

	/**
	 * 解析
	 * @param value 被解析的数据
	 * @param thrower 异常抛出器
	 * @return 如果解析失败抛出异常
	 */
	public static ResourceTypeEnum parseOrThrow(final String value, Function<String, RuntimeException> thrower) {
		ResourceTypeEnum o = parseOrDefault(value, null);
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
	public static ResourceTypeEnum parse(final String value) throws IllegalArgumentException {
		return parseOrThrow(value, (v) -> new IllegalArgumentException("Invalid value : " + v));
	}

}
