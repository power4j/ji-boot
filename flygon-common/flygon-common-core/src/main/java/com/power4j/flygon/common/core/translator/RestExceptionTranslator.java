package com.power4j.flygon.common.core.translator;

import cn.hutool.core.util.StrUtil;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
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

import javax.validation.ConstraintViolationException;

/**
 * Taken form mica
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
public class RestExceptionTranslator extends AbstractExceptionTranslator {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(MissingServletRequestParameterException e) {
		log.warn("缺少请求参数:{}", e.getMessage());
		String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
		return ApiResponse.of(SysErrorCodes.E_PARAM_MISS, message);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(MethodArgumentTypeMismatchException e) {
		log.warn("请求参数格式错误:{}", e.getMessage());
		String message = String.format("请求参数格式错误: %s", e.getName());
		return ApiResponse.of(SysErrorCodes.E_PARAM_TYPE, message);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(MethodArgumentNotValidException e) {
		log.warn("参数验证失败:{}", e.getMessage());
		return handleException(e.getBindingResult());
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(BindException e) {
		log.warn("参数绑定失败:{}", e.getMessage());
		return handleException(e.getBindingResult());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(ConstraintViolationException e) {
		log.warn("参数验证失败:{}", e.getMessage());
		return handleException(e.getConstraintViolations());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse<Object> handleException(NoHandlerFoundException e) {
		log.error("404没找到请求:{}", e.getMessage());
		return ApiResponse.of(SysErrorCodes.E_NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<Object> handleException(HttpMessageNotReadableException e) {
		log.error("消息不能读取:{}", e.getMessage());
		return ApiResponse.of(SysErrorCodes.E_MSG_NOT_READABLE, e.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ApiResponse<Object> handleException(HttpRequestMethodNotSupportedException e) {
		log.error("不支持当前请求方法:{}", e.getMessage());
		return ApiResponse.of(SysErrorCodes.E_METHOD_NOT_SUPPORTED, e.getMessage());
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ApiResponse<Object> handleException(HttpMediaTypeNotSupportedException e) {
		log.error("不支持当前媒体类型:{}", e.getMessage());
		return ApiResponse.of(SysErrorCodes.E_METHOD_NOT_SUPPORTED, e.getMessage());
	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ApiResponse<Object> handleException(HttpMediaTypeNotAcceptableException e) {
		String message = e.getMessage() + " " + StrUtil.join(StrUtil.COMMA, e.getSupportedMediaTypes());
		log.error("不接受的媒体类型:{}", message);
		return ApiResponse.of(SysErrorCodes.E_MEDIA_TYPE_NOT_SUPPORTED, message);
	}

}