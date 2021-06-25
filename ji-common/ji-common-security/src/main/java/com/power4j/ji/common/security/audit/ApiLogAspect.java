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

package com.power4j.ji.common.security.audit;

import cn.hutool.core.text.CharSequenceUtil;
import com.power4j.coca.kit.common.number.Num;
import com.power4j.coca.kit.common.text.StringPool;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.util.HttpServletRequestUtil;
import com.power4j.ji.common.core.util.SpringContextUtil;
import com.power4j.ji.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/6/25
 * @since 1.0
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class ApiLogAspect {

	@Around("@annotation(apiLog)")
	public Object around(ProceedingJoinPoint point, ApiLog apiLog) throws Throwable {

		String module = Optional.of(apiLog.module()).map(CharSequenceUtil::trimToNull).orElse(getModuleName(point));
		String tag = Optional.of(apiLog.tag()).map(CharSequenceUtil::trimToNull).orElse(getMethodName(point));
		final AccessEvent event = createAccessEvent(module, tag);
		Object obj;
		final Instant startTime = Instant.now();
		try {
			obj = point.proceed();
			if (obj instanceof ApiResponse) {
				Integer code = ((ApiResponse<?>) obj).getCode();
				event.setResponseCode(Objects.nonNull(code) ? code.toString() : StringPool.EMPTY);
			}
		}
		catch (Throwable e) {
			event.setEx(CharSequenceUtil.maxLength(e.getClass().getName(), 200));
			event.setExMsg(CharSequenceUtil.maxLength(e.getMessage(), 200));
			throw e;
		}
		finally {
			int ms = (int) (Duration.between(startTime, Instant.now()).toMillis());
			event.setDuration(ms);
		}
		try {
			SpringContextUtil.publishEvent(event);
		}
		catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return obj;
	}

	protected String getMethodName(ProceedingJoinPoint point) {
		return String.format("%s#%s", point.getTarget().getClass().getSimpleName(), point.getSignature().getName());
	}

	protected String getModuleName(ProceedingJoinPoint point) {
		return CharSequenceUtil.subAfter(point.getTarget().getClass().getPackage().getName(), StringPool.DOT, true);
	}

	protected AccessEvent createAccessEvent(String module, String name) {
		AccessEvent event = new AccessEvent();
		event.setModule(CharSequenceUtil.subSufByLength(module, 100));
		event.setApiTag(CharSequenceUtil.subSufByLength(name, 100));
		event.setAccessAt(LocalDateTime.now());
		HttpServletRequestUtil.getCurrentRequest().ifPresent(request -> {
			event.setMethod(request.getMethod());
			event.setPath(CharSequenceUtil.maxLength(request.getRequestURI(), 200));
			event.setQuery(CharSequenceUtil.maxLength(request.getQueryString(), 200));
			event.setLocation(HttpServletRequestUtil.getRemoteAddr(request, StringPool.EMPTY));
		});
		SecurityUtil.getLoginUser().ifPresent(user -> {
			event.setUid(user.getUid());
			event.setUsername(user.getUsername());
		});
		// default values
		event.setDuration(Num.ZERO);
		event.setResponseCode(StringPool.EMPTY);
		event.setEx(StringPool.EMPTY);
		event.setExMsg(StringPool.EMPTY);
		return event;
	}

}
