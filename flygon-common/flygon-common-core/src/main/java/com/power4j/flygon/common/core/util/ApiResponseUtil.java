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

import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.model.ApiResponse;

import java.util.Optional;

/**
 * 常用响应数据
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
public class ApiResponseUtil {

	/**
	 * 成功
	 * @param msg
	 * @param data
	 * @param <E>
	 * @return
	 */
	public static <E> ApiResponse<E> ok(String msg, E data) {
		return ApiResponse.of(SysErrorCodes.E_OK, msg, data);
	}

	/**
	 * 成功
	 * @param data
	 * @param <E>
	 * @return
	 */
	public static <E> ApiResponse<E> ok(E data) {
		return ok(null, data);
	}

	/**
	 * 成功
	 * @param <E>
	 * @return
	 */
	public static <E> ApiResponse<E> ok() {
		return ApiResponse.of(SysErrorCodes.E_OK, null, null);
	}

	/**
	 * 失败
	 * @param msg
	 * @param <E>
	 * @return
	 */
	public static <E> ApiResponse<E> fail(String msg) {
		return ApiResponse.of(SysErrorCodes.E_FAIL, msg, null);
	}

	/**
	 * 失败
	 * @param <E>
	 * @return
	 */
	public static <E> ApiResponse<E> fail() {
		return ApiResponse.of(SysErrorCodes.E_FAIL, null, null);
	}

	/**
	 * 如果数据为{@code null}就视为失败
	 * @param data
	 * @param <E>
	 * @return
	 */
	public static <E> ApiResponse<E> nullAsFail(E data) {
		return data == null ? fail() : ok(null, data);
	}

	/**
	 * 如果数据为{@code null}就视为失败
	 * @param data
	 * @param <E>
	 * @return
	 */
	public static <E> ApiResponse<E> nullAsFail(Optional<E> data) {
		return data.map(o -> ok(o)).orElse(fail(null));
	}

	public static <E> ApiResponse<E> badRequest(String msg) {
		return ApiResponse.of(SysErrorCodes.E_BAD_REQUEST, msg);
	}

	public static <E> ApiResponse<E> unauthorized(String msg) {
		return ApiResponse.of(SysErrorCodes.E_UNAUTHORIZED, msg);
	}

	public static <E> ApiResponse<E> forbidden(String msg) {
		return ApiResponse.of(SysErrorCodes.E_FORBIDDEN, msg);
	}

	public static <E> ApiResponse<E> notFound(String msg) {
		return ApiResponse.of(SysErrorCodes.E_NOT_FOUND, msg);
	}

	public static <E> ApiResponse<E> nullAsNotFound(E data, String msg) {
		return data == null ? notFound(msg) : ok(data);
	}

	public static <E> ApiResponse<E> conflict(String msg) {
		return ApiResponse.of(SysErrorCodes.E_CONFLICT, msg);
	}

	public static <E> ApiResponse<E> serverError(String msg) {
		return ApiResponse.of(SysErrorCodes.E_SERVER_ERROR, msg);
	}

}
