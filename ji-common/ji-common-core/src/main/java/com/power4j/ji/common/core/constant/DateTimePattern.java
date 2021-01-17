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

package com.power4j.ji.common.core.constant;

/**
 * 日期格式
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
public interface DateTimePattern {

	String YEAR = "yyyy";

	String YEAR_MONTH = "yyyy-MM";

	String MONTH_DAY = "MM-dd";

	String DATETIME = "yyyy-MM-dd HH:mm:ss";

	String DATETIME_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	String DATETIME_MS_PURE = "yyyyMMddHHmmssSSS";

	String DATETIME_UTC_ZONE = "yyyy-MM-dd'T'HH:mm:ssZ";

	String DATETIME_ISO8601 = "yyyy-MM-dd HH:mm:ss,SSS";

	String DATE = "yyyy-MM-dd";

	String TIME = "HH:mm:ss";

}
