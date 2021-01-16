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

package com.power4j.flygon.common.cache.util;

import com.power4j.flygon.common.cache.constant.CommonCacheConstant;
import com.power4j.flygon.common.core.util.NumUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/5
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class CacheUtil {

	private final static int POS_INVALID = -1;

	public TimeUnit parseTimeUnit(@Nullable String str, TimeUnit defVal) {
		char lastChar = Character.toLowerCase(str.charAt(str.length() - 1));
		switch (lastChar) {
		case 'd':
			return TimeUnit.DAYS;
		case 'h':
			return TimeUnit.HOURS;
		case 'm':
			return TimeUnit.MINUTES;
		case 's':
			return TimeUnit.SECONDS;
		default:
			return defVal;
		}
	}

	public Optional<Duration> parseTtl(@Nullable String str) {
		int ttlPos;
		if (str == null || str.isEmpty()
				|| (ttlPos = str.lastIndexOf(CommonCacheConstant.TTL_SEPARATOR)) == POS_INVALID) {
			return Optional.empty();
		}

		String ttl = str.substring(ttlPos);
		TimeUnit timeUnit = parseTimeUnit(ttl, null);
		if (timeUnit == null) {
			log.warn("Cache TTL invalid format; was {}, must end with one of [dDhHmMsS]", ttl);
			timeUnit = TimeUnit.SECONDS;
		}
		Long duration = NumUtil.parseLong(ttl.substring(1, ttl.length() - 1), null);
		if (duration == null) {
			log.warn("Fail to parse cache TTL, invalid value : {}", ttl);
			return Optional.empty();
		}
		return Optional.of(Duration.ofNanos(timeUnit.toNanos(duration)));
	}

}
