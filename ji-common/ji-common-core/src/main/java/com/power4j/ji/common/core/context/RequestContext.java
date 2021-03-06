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

package com.power4j.ji.common.core.context;

import com.power4j.ji.common.core.util.HttpServletRequestUtil;
import com.power4j.ji.common.core.util.ThreadStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/16
 * @since 1.0
 */
@RequiredArgsConstructor
public class RequestContext {

	public final static String REQUEST_CONTEXT_KEY = "request_context";

	private final ReqContextProperties contextProperties;

	public Optional<String> getRequestId() {
		return Optional.ofNullable(getHeaders().getFirst(contextProperties.getHeaderMapping().getRequestId()));
	}

	public Optional<String> getAccountId() {
		return Optional.ofNullable(getHeaders().getFirst(contextProperties.getHeaderMapping().getAccountId()));
	}

	public void setRequestId(@Nullable String val) {
		getHeaders().set(contextProperties.getHeaderMapping().getRequestId(), val);
	}

	public void setAccountId(@Nullable String val) {
		getHeaders().set(contextProperties.getHeaderMapping().getAccountId(), val);
	}

	protected HttpHeaders getHeaders() {
		return ThreadStore.get(REQUEST_CONTEXT_KEY, this::loadHeader).orElse(new HttpHeaders());
	}

	private HttpHeaders loadHeader() {
		return HttpServletRequestUtil.pickupHeaders(
				HttpServletRequestUtil.getCurrentRequest()
						.orElseThrow(() -> new IllegalStateException("No request in current thread")),
				contextProperties.getHeaders());
	}

}
