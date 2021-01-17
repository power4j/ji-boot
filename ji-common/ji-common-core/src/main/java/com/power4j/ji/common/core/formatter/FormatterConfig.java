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

package com.power4j.ji.common.core.formatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.YearMonth;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
public class FormatterConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatterForFieldType(YearMonth.class, new YearMonthFormatter());
		registry.addFormatterForFieldType(MonthDay.class, new MonthDayFormatter());
		registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
		registry.addFormatterForFieldType(LocalTime.class, new LocalTimeFormatter());
		registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
	}

}
