package com.power4j.flygon.common.core.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
@UtilityClass
public class NumUtil extends NumberUtil {

	/**
	 * 解析 int
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public Integer parseInt(String str, Integer defaultValue) {
		try {
			return StrUtil.isBlank(str) ? defaultValue : Integer.parseInt(str);
		}
		catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 解析 long
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public Long parseLong(String str, Long defaultValue) {
		try {
			return StrUtil.isBlank(str) ? defaultValue : Long.parseLong(str);
		}
		catch (NumberFormatException e) {
			return defaultValue;
		}
	}

}
