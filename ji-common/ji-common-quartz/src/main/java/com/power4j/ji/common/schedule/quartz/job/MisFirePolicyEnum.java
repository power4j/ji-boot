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

package com.power4j.ji.common.schedule.quartz.job;

import com.power4j.kit.common.data.dict.annotation.DictValue;
import com.power4j.kit.common.data.dict.annotation.Label;
import com.power4j.kit.common.data.dict.annotation.MapDict;
import com.power4j.kit.common.data.dict.annotation.Remarks;
import com.power4j.kit.common.data.dict.annotation.Styled;

import java.util.function.Function;

/**
 * CronSchedule MisFire 处理策略
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
@MapDict(code = "mis_fire_policy", name = "调度丢失处理策略")
public enum MisFirePolicyEnum {

	/**
	 * 立即执行一次,然后开始正常调度
	 */
	@Label("立即执行一次,然后开始正常调度")
	@Styled("success")
	@Remarks("适用一般使用场景")
	RESCUE_ONE("0"),
	/**
	 * 立即执行所有丢失的触发点,然后开始正常调度
	 */
	@Label("立即执行所有丢失的触发点,然后开始正常调度")
	@Styled("warning")
	@Remarks("服务长时间停机后启动,可能会引起大量任务调度")
	RESCUE_ALL("1"),
	/**
	 * 不处理,正常调度
	 */
	@Label("不处理,正常调度")
	@Styled("info")
	RESCUE_NONE("2");

	@DictValue
	private final String value;

	MisFirePolicyEnum(String value) {
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
	public static MisFirePolicyEnum parseOrDefault(final String value, final MisFirePolicyEnum defValue) {
		if (value == null) {
			return defValue;
		}
		for (MisFirePolicyEnum o : MisFirePolicyEnum.values()) {
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
	public static MisFirePolicyEnum parseOrNull(final String value) {
		return parseOrDefault(value, null);
	}

	/**
	 * 解析
	 * @param value 被解析的数据
	 * @param thrower 异常抛出器
	 * @return 如果解析失败抛出异常
	 */
	public static MisFirePolicyEnum parseOrThrow(final String value, Function<String, RuntimeException> thrower) {
		MisFirePolicyEnum o = parseOrDefault(value, null);
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
	public static MisFirePolicyEnum parse(final String value) throws IllegalArgumentException {
		return parseOrThrow(value, (v) -> new IllegalArgumentException("Invalid value : " + v));
	}

}
