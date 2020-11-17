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

package com.power4j.flygon.common.core.validate;

import lombok.experimental.UtilityClass;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Validator Helper
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
@UtilityClass
public class ValidateHelper {

	private static ResourceBundleMessageSource getMessageSource() {
		ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
		bundleMessageSource.setDefaultEncoding("UTF-8");
		bundleMessageSource.setBasenames("i18n/validation");
		return bundleMessageSource;
	}

	/**
	 * 校验对象的约束条件(快速失败)
	 * @param object 被校验的对象
	 * @param groups 校验组
	 * @param <T>
	 * @return 违例的约束,如果没有违例情况返回空的Set
	 */
	public static <T> Set<ConstraintViolation<T>> check(T object, Class<?>... groups) {
		Locale.setDefault(LocaleContextHolder.getLocale());
		Validator validator = Validation.byDefaultProvider().configure()
				.messageInterpolator(new ResourceBundleMessageInterpolator(
						new MessageSourceResourceBundleLocator(getMessageSource())))
				.buildValidatorFactory().getValidator();

		return validator.validate(object, groups);
	}

	/**
	 * 校验对象
	 * @param exceptionSupplier 自定义异常抛出
	 * @param object 待校验对象
	 * @param groups 待校验的组
	 */
	public static <T, E extends Throwable> void validate(Function<Set<ConstraintViolation<T>>, E> exceptionSupplier,
			T object, Class<?>... groups) throws E {
		Set<ConstraintViolation<T>> constraintViolations = check(object, groups);
		if (!constraintViolations.isEmpty()) {
			throw exceptionSupplier.apply(constraintViolations);
		}
	}

	/**
	 * 校验对象
	 * @param object 待校验对象
	 * @param groups 待校验的组
	 * @throws ValidationException
	 */
	public static <T> void validate(T object, Class<?>... groups) throws ValidationException {
		validate((constraintViolations) -> new ValidationException(constraintViolations.iterator().next().getMessage()),
				object, groups);
	}

	/**
	 * 校验对象的约束条件(完整校验)
	 * @param object
	 * @param groups
	 * @throws ValidationException
	 */
	public static <T> void validateAll(T object, Class<?>... groups) throws ValidationException {
		validate((constraintViolations) -> new ValidationException(
				constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","))),
				object, groups);
	}

}
