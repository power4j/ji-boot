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

import com.power4j.ji.common.core.constant.DateTimePattern;
import com.sun.org.apache.regexp.internal.RE;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/21
 * @since 1.0
 */
@UtilityClass
public class DateTimeUtil {

	private final static DateTimeFormatter LOGGING_FORMATTER = DateTimeFormatter
			.ofPattern(DateTimePattern.DATETIME_ISO8601);

	/**
	 * 转换
	 * @param date
	 * @return
	 */
	@Nullable
	public LocalDateTime toLocalDateTime(@Nullable Date date) {
		if (date == null) {
			return null;
		}
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * UTC 时间
	 * @return
	 */
	public LocalDateTime utcNow() {
		return LocalDateTime.now(ZoneOffset.UTC);
	}

	/**
	 * 日志友好的格式
	 * @param localDateTime
	 * @return
	 */
	@Nullable
	public String forLogging(@Nullable LocalDateTime localDateTime) {
		return localDateTime == null ? null : LOGGING_FORMATTER.format(localDateTime);
	}

}
