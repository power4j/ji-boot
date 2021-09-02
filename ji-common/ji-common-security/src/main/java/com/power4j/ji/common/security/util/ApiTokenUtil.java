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

package com.power4j.ji.common.security.util;

import cn.hutool.core.text.CharSequenceUtil;
import com.power4j.ji.common.core.constant.SecurityConstant;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/27
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class ApiTokenUtil {

	/**
	 * 获取 token值
	 * @param request
	 * @return 没有值返回null
	 */
	public Optional<String> getApiTokenValue(HttpServletRequest request) {
		String value = request.getHeader(SecurityConstant.HEADER_TOKEN_KEY);
		if (CharSequenceUtil.isNotEmpty(value)) {
			log.trace("请求头中的{}:{}", SecurityConstant.HEADER_TOKEN_KEY, value);
			return Optional.of(value);
		}
		String[] values = request.getParameterValues(SecurityConstant.PARAMETER_TOKEN_KEY);
		if (Objects.nonNull(value) && values.length > 0) {
			log.trace("请求参数的{}:{}", SecurityConstant.HEADER_TOKEN_KEY, values);
			return Optional.of(values[0]);
		}
		return Optional.empty();
	}

}
