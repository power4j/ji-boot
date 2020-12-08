package com.power4j.flygon.common.data.crud.constant;

import java.util.function.Function;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
public enum  OpFlagEnum {
	/**
	 * 普通数据
	 */
	NORMAL(0),
	/**
	 * 系统保护数据
	 */
	SYS_LOCKED(1);

	private final int value;

	OpFlagEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	/**
	 * 解析
	 *
	 * @param value    被解析的数据,可以是null
	 * @param defValue 默认值
	 * @return 如果解析失败返回默认值
	 */
	public static OpFlagEnum parseOrDefault(final Integer value, final OpFlagEnum defValue) {
		if (value == null) {
			return defValue;
		}
		for (OpFlagEnum o : OpFlagEnum.values()) {
			if (o.value == value.intValue()) {
				return o;
			}
		}
		return defValue;
	}

	/**
	 * 解析
	 *
	 * @param value 被解析的数据
	 * @return 如果解析失败返回 null
	 */
	public static OpFlagEnum parseOrNull(final Integer value) {
		return parseOrDefault(value, null);
	}

	/**
	 * 解析
	 *
	 * @param value   被解析的数据
	 * @param thrower 异常抛出器
	 * @return 如果解析失败抛出异常
	 */
	public static OpFlagEnum parseOrThrow(final Integer value, Function<Integer, RuntimeException> thrower) {
		OpFlagEnum o = parseOrDefault(value, null);
		if (o == null) {
			throw thrower.apply(value);
		}
		return o;
	}

	/**
	 * 解析
	 *
	 * @param value 被解析的数据
	 * @return 如果解析失败抛出 IllegalArgumentException
	 */
	public static OpFlagEnum parse(final Integer value) throws IllegalArgumentException {
		return parseOrThrow(value, (v) -> new IllegalArgumentException("Invalid value : " + v));
	}
}
