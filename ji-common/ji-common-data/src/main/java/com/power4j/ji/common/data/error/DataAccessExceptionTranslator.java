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

package com.power4j.ji.common.data.error;

import com.power4j.ji.common.core.config.AppProperties;
import com.power4j.ji.common.core.context.RequestContext;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.translator.AbstractExceptionHandler;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Slf4j
@Order(100)
@Configuration(proxyBeanMethods = false)
@RestControllerAdvice
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class DataAccessExceptionTranslator extends AbstractExceptionHandler {

	public DataAccessExceptionTranslator(AppProperties appProperties, ApplicationEventPublisher publisher,
			RequestContext requestContext) {
		super(appProperties, publisher, requestContext);
	}

	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse<Object> handleException(DataAccessException e) {
		log.error("数据库访问异常", e);
		ApiResponse<Object> result = ApiResponseUtil
				.fail(String.format("数据库访问异常(%s),请联系管理员", e.getClass().getSimpleName()));
		publishEvent(e);
		return result;
	}

}
