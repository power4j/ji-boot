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

package com.power4j.ji.common.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Wrapper for response data
 * <p>
 *
 * @author CJ (jclazz@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(title = "响应")
public class ApiResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(title = "错误代码")
	private Integer code;

	@Schema(title = "提示信息")
	@Nullable
	private String msg;

	@Schema(title = "业务数据")
	@Nullable
	private T data;

	public static <E> ApiResponse<E> of(int code, @Nullable String msg, @Nullable E data) {
		return new ApiResponse<E>().setCode(code).setMsg(msg).setData(data);
	}

	public static <E> ApiResponse<E> of(int code, @Nullable String msg) {
		return of(code, msg, null);
	}

	public static <E> ApiResponse<E> of(int code) {
		return of(code, null, null);
	}

	/**
	 * 转换数据类型
	 * @param predicate
	 * @param func
	 * @param <U>
	 * @return
	 */
	public <U> ApiResponse<U> map(Predicate<? super T> predicate, Function<? super T, ? extends U> func) {
		return predicate.test(data) ? of(code, msg, func.apply(data)) : of(code, msg, null);
	}

	/**
	 * 转换数据类型
	 * @param func
	 * @param <U>
	 * @return
	 */
	public <U> ApiResponse<U> mapIfPresent(Function<? super T, ? extends U> func) {
		return map((x) -> x != null, func);
	}

}
