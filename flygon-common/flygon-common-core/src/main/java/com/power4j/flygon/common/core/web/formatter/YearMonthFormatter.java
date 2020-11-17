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

package com.power4j.flygon.common.core.web.formatter;

import com.power4j.flygon.common.core.util.DateTimeParser;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.Locale;

/**
 * YearMonth 解析
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 11/17/20
 * @since 1.0
 */
public class YearMonthFormatter implements Formatter<YearMonth> {

	@Override
	public YearMonth parse(String text, Locale locale) throws ParseException {
		return DateTimeParser.parseYearMonth(text).orElseThrow(() -> new ParseException(text, 0));
	}

	@Override
	public String print(YearMonth object, Locale locale) {
		return DateTimeParser.DEFAULT_YEAR_MONTH_FORMATTER.format(object);
	}

}
