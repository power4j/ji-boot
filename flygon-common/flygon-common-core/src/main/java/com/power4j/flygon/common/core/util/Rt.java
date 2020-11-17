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

package com.power4j.flygon.common.core.util;

import com.power4j.flygon.common.core.constant.ErrorCodes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Wrapper for response data
 * <p>
 *
 * @author CJ (jclazz@outlook.com)
 * @date 11/17/20
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@ApiModel("响应")
public class Rt<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "错误代码")
	private Integer code;

	@ApiModelProperty(value = "提示信息")
	private String msg;

	@ApiModelProperty(value = "业务数据")
	private T data;

	public static <E> Rt<E> of(int code, String msg, E data) {
		return new Rt<E>().setCode(code).setMsg(msg).setData(data);
	}

	public static <E> Rt<E> of(int code, String msg) {
		return of(code, msg, null);
	}

	public static <E> Rt<E> of(int code) {
		return of(code, null, null);
	}

	public static <E> Rt<E> ok(String msg, E data) {
		return of(ErrorCodes.GEN_OK, msg, data);
	}

	public static <E> Rt<E> ok(E data) {
		return ok(null, data);
	}

	public static <E> Rt<E> ok() {
		return of(ErrorCodes.GEN_OK, null, null);
	}

	public static <E> Rt<E> fail(String msg) {
		return of(ErrorCodes.GEN_FAIL, msg, null);
	}

	public static <E> Rt<E> fail() {
		return of(ErrorCodes.GEN_FAIL, null, null);
	}

	public static <E> Rt<E> badRequest(String msg) {
		return of(ErrorCodes.GEN_BAD_REQUEST, msg);
	}

	public static <E> Rt<E> unauthorized(String msg) {
		return of(ErrorCodes.GEN_UNAUTHORIZED, msg);
	}

	public static <E> Rt<E> forbidden(String msg) {
		return of(ErrorCodes.GEN_FORBIDDEN, msg);
	}

	public static <E> Rt<E> notFound(String msg) {
		return of(ErrorCodes.GEN_NOT_FOUND, msg);
	}

	public static <E> Rt<E> serverError(String msg) {
		return of(ErrorCodes.GEN_SERVER_ERROR, msg);
	}

	/**
	 * 如果数据为{@code null}就视为失败
	 * @param data
	 * @param <E>
	 * @return
	 */
	public static <E> Rt<E> nullAsFail(E data) {
		return data == null ? fail() : ok(null, data);
	}

	/**
	 * 如果数据为{@code null}就视为失败
	 * @param data
	 * @param <E>
	 * @return
	 */
	public static <E> Rt<E> nullAsFail(Optional<E> data) {
		return data.map(o -> ok(o)).orElse(fail(null));
	}

	/**
	 * 转换数据类型
	 * @param predicate
	 * @param func
	 * @param <U>
	 * @return
	 */
	public <U> Rt<U> map(Predicate<? super T> predicate, Function<? super T, ? extends U> func) {
		return predicate.test(data) ? of(code, msg, func.apply(data)) : of(code, msg, null);
	}

	/**
	 * 转换数据类型
	 * @param func
	 * @param <U>
	 * @return
	 */
	public <U> Rt<U> mapIfPresent(Function<? super T, ? extends U> func) {
		return map((x) -> x != null, func);
	}

}
