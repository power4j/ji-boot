package com.power4j.flygon.common.data.error;

import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.translator.ErrorEventUtil;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;
import java.sql.SQLException;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
@Configuration(proxyBeanMethods = false)
@RestControllerAdvice
@RequiredArgsConstructor
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class DataAccessExceptionTranslator {

	private final ApplicationEventPublisher publisher;

	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse<Object> handleException(DataAccessException e) {
		log.error("数据库访问异常", e);
		ApiResponse<Object> result = ApiResponseUtil
				.fail(String.format("数据库访问异常(%s),请联系管理员", e.getClass().getSimpleName()));
		ErrorEventUtil.publishEvent(publisher, e);
		return result;
	}

	@ExceptionHandler(SQLException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse<Object> handleException(SQLException e) {
		log.error("数据库访问异常", e);
		ApiResponse<Object> result = ApiResponseUtil
				.fail(String.format("数据库访问异常(%s),请联系管理员", e.getClass().getSimpleName()));
		ErrorEventUtil.publishEvent(publisher, e);
		return result;
	}

}
