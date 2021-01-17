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

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ErrorEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String appName;

	private LocalDateTime timeUtc;

	private String ex;

	private String exMsg;

	private String exStack;

	@Nullable
	private String requestId;

	@Nullable
	private String accountId;

	private String requestUri;

	private String requestMethod;

	@Nullable
	private String requestQueryString;

	private Map<String, String> extraInfo = new HashMap<>();

}
