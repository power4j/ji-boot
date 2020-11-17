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

package com.power4j.flygon.common.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.power4j.flygon.common.core.constant.DateTimePattern;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Java8 日期类型序列化支持
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 11/17/20
 * @since 1.0
 */
public class Java8TimeModel extends SimpleModule {

	public Java8TimeModel() {
		this.addSerializer(Year.class, new YearSerializer(DateTimeFormatter.ofPattern(DateTimePattern.YEAR)));
		this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DateTimePattern.DATE)));
		this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateTimePattern.TIME)));
		this.addSerializer(LocalDateTime.class,
				new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateTimePattern.DATETIME)));
		this.addSerializer(YearMonth.class,
				new YearMonthSerializer(DateTimeFormatter.ofPattern(DateTimePattern.YEAR_MONTH)));
		this.addSerializer(MonthDay.class,
				new MonthDaySerializer(DateTimeFormatter.ofPattern(DateTimePattern.MONTH_DAY)));

		this.addDeserializer(Year.class, new YearDeserializer(DateTimeFormatter.ofPattern(DateTimePattern.YEAR)));
		this.addDeserializer(LocalDate.class,
				new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateTimePattern.DATE)));
		this.addDeserializer(LocalTime.class,
				new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateTimePattern.TIME)));
		this.addDeserializer(LocalDateTime.class,
				new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateTimePattern.DATETIME)));
		this.addDeserializer(YearMonth.class,
				new YearMonthDeserializer(DateTimeFormatter.ofPattern(DateTimePattern.YEAR_MONTH)));
		this.addDeserializer(MonthDay.class,
				new MonthDayDeserializer(DateTimeFormatter.ofPattern(DateTimePattern.MONTH_DAY)));
	}

}
