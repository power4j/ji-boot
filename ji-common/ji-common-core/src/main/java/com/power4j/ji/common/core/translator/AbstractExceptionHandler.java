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

package com.power4j.ji.common.core.translator;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.UUID;
import com.power4j.ji.common.core.config.AppProperties;
import com.power4j.ji.common.core.context.RequestContext;
import com.power4j.ji.common.core.util.DateTimeUtil;
import com.power4j.ji.common.core.util.HttpServletRequestUtil;
import org.springframework.context.ApplicationEventPublisher;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/16
 * @since 1.0
 */
public abstract class AbstractExceptionHandler {

	private final AppProperties appProperties;

	private final ApplicationEventPublisher publisher;

	private final RequestContext requestContext;

	public AbstractExceptionHandler(AppProperties appProperties, ApplicationEventPublisher publisher,
			RequestContext requestContext) {
		this.appProperties = appProperties;
		this.publisher = publisher;
		this.requestContext = requestContext;
	}

	protected void publishEvent(Throwable e) {
		ErrorEvent errorEvent = new ErrorEvent();
		errorEvent.setId(UUID.fastUUID().toString());
		errorEvent.setAppName(appProperties.getEnvironment().getProperty("spring.application.name", "未知应用"));
		errorEvent.setTimeUtc(DateTimeUtil.utcNow());
		errorEvent.setEx(e.getClass().getName());
		errorEvent.setExMsg(e.getMessage());
		errorEvent.setExStack(ExceptionUtil.stacktraceToString(e, 5000));
		errorEvent.setRequestId(requestContext.getRequestId().orElse(null));
		errorEvent.setAccountId(requestContext.getAccountId().orElse(null));

		HttpServletRequest request = HttpServletRequestUtil.getCurrentRequest().get();
		errorEvent.setRequestMethod(request.getMethod());
		errorEvent.setRequestUri(request.getRequestURI());
		errorEvent.setRequestQueryString(request.getQueryString());
		publisher.publishEvent(errorEvent);
	}

}
