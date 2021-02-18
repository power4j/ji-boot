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

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

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
	@Nullable
	public Integer parseInt(@Nullable String str, @Nullable Integer defaultValue) {
		try {
			return CharSequenceUtil.isBlank(str) ? defaultValue : Integer.parseInt(str);
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
	@Nullable
	public Long parseLong(@Nullable String str, @Nullable Long defaultValue) {
		try {
			return CharSequenceUtil.isBlank(str) ? defaultValue : Long.parseLong(str);
		}
		catch (NumberFormatException e) {
			return defaultValue;
		}
	}

}
