package com.power4j.flygon.common.core.translator;

import com.power4j.flygon.common.core.exception.RtException;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

/**
 * 处理未捕获异常
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Slf4j
@Order
@RestControllerAdvice
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ExceptionTranslator {

	private final ApplicationEventPublisher publisher;

	@ExceptionHandler(RtException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse<Object> handleException(RtException e) {
		log.error("未处理的业务异常", e);
		ApiResponse<Object> result = ApiResponseUtil
				.fail(String.format("%s - %s", e.getClass().getSimpleName(), e.getMessage()));
		ErrorEventUtil.publishEvent(publisher, e);
		return result;
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse<Object> handleException(Throwable e) {
		log.error(String.format("未知异常 - %s", e.getClass().getName()), e);
		ErrorEventUtil.publishEvent(publisher, e);
		return ApiResponseUtil.fail(String.format("%s - %s", e.getClass().getSimpleName(), e.getMessage()));
	}

}
