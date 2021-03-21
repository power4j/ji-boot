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

package com.power4j.ji.common.core.servlet;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import com.power4j.ji.common.core.constant.CommonConstant;
import com.power4j.ji.common.core.context.ReqContextProperties;
import com.power4j.ji.common.core.context.RequestContext;
import com.power4j.ji.common.core.util.HttpServletRequestUtil;
import com.power4j.ji.common.core.util.ThreadStore;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/14
 * @since 1.0
 */
@RequiredArgsConstructor
public class HttpServletRequestListener implements ServletRequestListener {

	private final ReqContextProperties contextProperties;

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		MDC.remove(CommonConstant.MDC_REQUEST_ID);
		MDC.remove(CommonConstant.MDC_ACCOUNT_ID);
		ThreadStore.clear();
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) sre.getServletRequest();
		final String requestIdName = contextProperties.getHeaderMapping().getRequestId();
		final String accountIdName = contextProperties.getHeaderMapping().getAccountId();

		HttpHeaders headers = HttpServletRequestUtil.pickupHeaders(httpServletRequest, contextProperties.getHeaders());
		if (CollectionUtil.isEmpty(headers.get(requestIdName))) {
			headers.add(requestIdName, UUID.fastUUID().toString(true));
		}

		ThreadStore.put(RequestContext.REQUEST_CONTEXT_KEY, headers);

		MDC.put(CommonConstant.MDC_REQUEST_ID, headers.getFirst(requestIdName));
		MDC.put(CommonConstant.MDC_ACCOUNT_ID, headers.getFirst(accountIdName));
	}

}
