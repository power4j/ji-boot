package com.power4j.flygon.common.core.translator;

import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.model.ApiResponse;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
public class AbstractExceptionTranslator {

	/**
	 * 处理 BindingResult
	 * @param result BindingResult
	 * @return ApiResponse
	 */
	protected ApiResponse<Object> handleException(BindingResult result) {
		FieldError error = result.getFieldError();
		String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
		return ApiResponse.of(SysErrorCodes.E_PARAM_BIND, message);
	}

	/**
	 * 处理 ConstraintViolation
	 * @param violations 校验结果
	 * @return ApiResponse
	 */
	protected ApiResponse<Object> handleException(Set<ConstraintViolation<?>> violations) {
		ConstraintViolation<?> violation = violations.iterator().next();
		String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
		String message = String.format("%s:%s", path, violation.getMessage());
		return ApiResponse.of(SysErrorCodes.E_PARAM_INVALID, message);
	}

}
