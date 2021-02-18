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

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 将常见的请求错误以比较友好的方式返回前端
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class RestExceptionTranslator {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(MissingServletRequestParameterException e) {
		log.warn("缺少请求参数:{}", e.getMessage());
		return ApiResponse.of(SysErrorCodes.E_PARAM_MISS, "缺少请求参数",
				String.format("%s(%s)", e.getParameterName(), e.getParameterType()));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(MethodArgumentTypeMismatchException e) {
		log.warn("请求参数错误:{}", e.getMessage());
		return ApiResponse.of(SysErrorCodes.E_PARAM_TYPE, "请求参数错误", e.getName());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(MethodArgumentNotValidException e) {
		log.warn("请求参数验证失败:{}", e.getMessage());
		FieldError error = e.getBindingResult().getFieldError();
		return ApiResponse.of(SysErrorCodes.E_PARAM_INVALID, "请求参数错误",
				String.format("%s: %s", error.getField(), error.getDefaultMessage()));
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(BindException e) {
		log.warn("请求参数绑定失败:{}", e.getMessage());
		FieldError error = e.getBindingResult().getFieldError();
		return ApiResponse.of(SysErrorCodes.E_PARAM_BIND, "请求参数错误",
				String.format("%s: %s", error.getField(), error.getDefaultMessage()));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(ConstraintViolationException e) {
		log.warn("请求参数验证失败:{}", e.getMessage());
		ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
		String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
		return ApiResponse.of(SysErrorCodes.E_PARAM_INVALID, "请求参数错误",
				String.format("%s: %s", path, violation.getMessage()));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(HttpMessageNotReadableException e) {
		log.error("请求消息不能读取:{}", e.getMessage());
		return ApiResponse.of(SysErrorCodes.E_MSG_NOT_READABLE, "请求参数错误");
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse<Object> handleException(NoHandlerFoundException e) {
		log.error("请求资源不存在:{}", e.getMessage());
		return ApiResponse.of(SysErrorCodes.E_NOT_FOUND, "请求资源不存在",
				String.format("%s - %s", e.getHttpMethod(), e.getRequestURL()));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ApiResponse<Object> handleException(HttpRequestMethodNotSupportedException e) {
		log.error("不支持当前请求方法:{}", e.getMessage());
		return ApiResponse.of(SysErrorCodes.E_METHOD_NOT_SUPPORTED, String.format("不支持当前请求方法: %s", e.getMethod()));
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ApiResponse<Object> handleException(HttpMediaTypeNotSupportedException e) {
		log.error("请求的媒体类型不支持:{}", e.getMessage());
		return ApiResponse.of(SysErrorCodes.E_METHOD_NOT_SUPPORTED,
				String.format("请求的媒体类型不支持: %s", e.getContentType().toString()), String.format("支持的媒体类型为: %s",
						e.getSupportedMediaTypes().stream().map(MediaType::toString).collect(Collectors.joining(","))));
	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ApiResponse<Object> handleException(HttpMediaTypeNotAcceptableException e) {
		String message = e.getMessage() + " " + CharSequenceUtil.join(StrUtil.COMMA, e.getSupportedMediaTypes());
		log.error("不接受的媒体类型:{}", message);
		return ApiResponse.of(SysErrorCodes.E_METHOD_NOT_SUPPORTED, "不接受的媒体类型", String.format("支持的媒体类型为: %s",
				e.getSupportedMediaTypes().stream().map(MediaType::toString).collect(Collectors.joining(","))));
	}

}