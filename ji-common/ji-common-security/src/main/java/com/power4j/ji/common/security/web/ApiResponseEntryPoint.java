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

package com.power4j.ji.common.security.web;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power4j.ji.common.core.model.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/9/2
 * @since 1.0
 */
public class ApiResponseEntryPoint extends AbstractAuthenticationEntryPoint {
	@Getter
	@Setter
	private ObjectMapper jsonMapper = new ObjectMapper();

	@Override
	protected void renderResponse(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		ApiResponse<?> result = makeApiResponse(authException);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(determineHttpResponseStatusCode(authException));
		PrintWriter printWriter = response.getWriter();
		printWriter.println(getJsonMapper().writeValueAsString(result));
	}

	protected int determineHttpResponseStatusCode(AuthenticationException authException){
		return HttpStatus.HTTP_OK;
	}
}
