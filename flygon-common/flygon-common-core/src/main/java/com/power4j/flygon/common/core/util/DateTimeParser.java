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

package com.power4j.flygon.common.core.util;

import cn.hutool.core.lang.Pair;
import com.power4j.flygon.common.core.constant.DateTimePattern;
import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 时间解析
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 11/17/20
 * @since 1.0
 */
@UtilityClass
public class DateTimeParser {

	public final static DateTimeFormatter DEFAULT_YEAR_MONTH_FORMATTER = DateTimeFormatter
			.ofPattern(DateTimePattern.YEAR_MONTH);

	public final static DateTimeFormatter DEFAULT_MONTH_DAY_FORMATTER = DateTimeFormatter
			.ofPattern(DateTimePattern.MONTH_DAY);

	public final static DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DateTimePattern.DATE);

	public final static DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern(DateTimePattern.TIME);

	public final static DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter
			.ofPattern(DateTimePattern.DATETIME);

	public final static DateTimeFormatter UTC_DATETIME_FORMATTER = DateTimeFormatter
			.ofPattern(DateTimePattern.DATETIME_UTC);

	private static List<Pair<String, DateTimeFormatter>> YEAR_MONTH_FORMAT_REGISTRY = new ArrayList<>(3);

	private static List<Pair<String, DateTimeFormatter>> MONTH_DAY_FORMAT_REGISTRY = new ArrayList<>(3);

	private static List<Pair<String, DateTimeFormatter>> DATE_FORMAT_REGISTRY = new ArrayList<>(3);

	private static List<Pair<String, DateTimeFormatter>> TIME_FORMAT_REGISTRY = new ArrayList<>(3);

	private static List<Pair<String, DateTimeFormatter>> DATE_TIME_FORMAT_REGISTRY = new ArrayList<>(3);

	static {

		// @formatter: off

		YEAR_MONTH_FORMAT_REGISTRY.add(new Pair<>("^\\d{4}-\\d{2}$", DEFAULT_YEAR_MONTH_FORMATTER));
		YEAR_MONTH_FORMAT_REGISTRY.add(new Pair<>("^\\d{4} {1}\\d{2}$", DateTimeFormatter.ofPattern("yyyy MM")));
		YEAR_MONTH_FORMAT_REGISTRY.add(new Pair<>("^\\d{6}$", DateTimeFormatter.ofPattern("yyyyMM")));

		MONTH_DAY_FORMAT_REGISTRY.add(new Pair<>("^\\d{2}-\\d{2}$", DEFAULT_MONTH_DAY_FORMATTER));
		MONTH_DAY_FORMAT_REGISTRY.add(new Pair<>("^\\d{2} {1}\\d{2}$", DateTimeFormatter.ofPattern("MM dd")));
		MONTH_DAY_FORMAT_REGISTRY.add(new Pair<>("^\\d{4}$", DateTimeFormatter.ofPattern("MMdd")));

		DATE_FORMAT_REGISTRY.add(new Pair<>("^\\d{4}-\\d{2}-\\d{2}$", DEFAULT_DATE_FORMATTER));
		DATE_FORMAT_REGISTRY.add(new Pair<>("^\\d{4} {1}\\d{2} {1}\\d{2}$", DateTimeFormatter.ofPattern("yyyy MM dd")));
		DATE_FORMAT_REGISTRY.add(new Pair<>("^\\d{8}$", DateTimeFormatter.ofPattern("yyyyMMdd")));

		TIME_FORMAT_REGISTRY.add(new Pair<>("^\\d{2}:\\d{2}:\\d{2}$", DEFAULT_TIME_FORMATTER));
		TIME_FORMAT_REGISTRY.add(new Pair<>("^\\d{2} {1}\\d{2} {1}\\d{2}$", DateTimeFormatter.ofPattern("HH mm ss")));
		TIME_FORMAT_REGISTRY.add(new Pair<>("^\\d{6}$", DateTimeFormatter.ofPattern("HHmmss")));

		DATE_TIME_FORMAT_REGISTRY
				.add(new Pair<>("^\\d{4}-\\d{2}-\\d{2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$", DEFAULT_DATETIME_FORMATTER));
		DATE_TIME_FORMAT_REGISTRY
				.add(new Pair<>("^\\d{4}-\\d{2}-\\d{2}.*T.*\\d{1,2}:\\d{1,2}:\\d{1,2}.*..*$", UTC_DATETIME_FORMATTER));
		DATE_TIME_FORMAT_REGISTRY.add(new Pair<>("^\\d{8} {1}\\d{6}$", DateTimeFormatter.ofPattern("yyyyMMdd HHmmss")));
		DATE_TIME_FORMAT_REGISTRY.add(new Pair<>("^\\d{14}$", DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

		// @formatter: on

	}

	/**
	 * 获取 DateTimeFormatter
	 * @param dateStr
	 * @return
	 */
	public Optional<DateTimeFormatter> getYearMonthFormatter(String dateStr) {
		return getFirstMatches(YEAR_MONTH_FORMAT_REGISTRY, dateStr);
	}

	/**
	 * 获取 DateTimeFormatter
	 * @param dateStr
	 * @return
	 */
	public Optional<DateTimeFormatter> getMonthDayFormatter(String dateStr) {
		return getFirstMatches(MONTH_DAY_FORMAT_REGISTRY, dateStr);
	}

	/**
	 * 获取 DateTimeFormatter
	 * @param dateStr
	 * @return
	 */
	public Optional<DateTimeFormatter> getDateFormatter(String dateStr) {
		return getFirstMatches(DATE_FORMAT_REGISTRY, dateStr);
	}

	/**
	 * 获取 DateTimeFormatter
	 * @param timeStr
	 * @return
	 */
	public Optional<DateTimeFormatter> getTimeFormatter(String timeStr) {
		return getFirstMatches(TIME_FORMAT_REGISTRY, timeStr);
	}

	/**
	 * 获取 DateTimeFormatter
	 * @param dateTimeStr
	 * @return
	 */
	public Optional<DateTimeFormatter> getDateTimeFormatter(String dateTimeStr) {
		return getFirstMatches(DATE_TIME_FORMAT_REGISTRY, dateTimeStr);
	}

	private Optional<DateTimeFormatter> getFirstMatches(List<Pair<String, DateTimeFormatter>> registry, String text) {
		for (Pair<String, DateTimeFormatter> pair : registry) {
			if (text.matches(pair.getKey())) {
				return Optional.of(pair.getValue());
			}
		}
		return Optional.empty();
	}

	/**
	 * 解析 YearMonth
	 * @param text
	 * @return
	 */
	public Optional<YearMonth> parseYearMonth(String text) {
		return getYearMonthFormatter(text).map(o -> YearMonth.parse(text, o));
	}

	/**
	 * 解析 MonthDay
	 * @param text
	 * @return
	 */
	public Optional<MonthDay> parseMonthDay(String text) {
		return getMonthDayFormatter(text).map(o -> MonthDay.parse(text, o));
	}

	/**
	 * 解析 LocalDate
	 * @param text
	 * @return
	 */
	public Optional<LocalDate> parseDate(String text) {
		return getDateFormatter(text).map(o -> LocalDate.parse(text, o));
	}

	/**
	 * 解析 LocalTime
	 * @param text
	 * @return
	 */
	public Optional<LocalTime> parseTime(String text) {
		return getTimeFormatter(text).map(o -> LocalTime.parse(text, o));
	}

	/**
	 * 解析 LocalDateTime
	 * @param text
	 * @return
	 */
	public Optional<LocalDateTime> parseDateTime(String text) {
		return getDateTimeFormatter(text).map(o -> LocalDateTime.parse(text, o));
	}

}
