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

package com.power4j.flygon.common.core.constant;

/**
 * Error code
 * <ul>
 * <li>General Error 0 ~ 1000</li>
 * </ul>
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 11/17/20
 * @since 1.0
 */
public interface ErrorCodes {

	// -----------------------------------------------------------------
	// Common error code : -9999 ~ 9999
	// -----------------------------------------------------------------

	/**
	 * 失败
	 */
	int GEN_FAIL = -1;

	/**
	 * 成功
	 */
	int GEN_OK = 200;

	/**
	 * 无效请求
	 */
	int GEN_BAD_REQUEST = 400;

	/**
	 * 未认证
	 */
	int GEN_UNAUTHORIZED = 401;

	/**
	 * 无权限
	 */
	int GEN_FORBIDDEN = 403;

	/**
	 * 资源不存在
	 */
	int GEN_NOT_FOUND = 404;

	/**
	 * 服务器错误
	 */
	int GEN_SERVER_ERROR = 500;

	// -----------------------------------------------------------------
	// Biz error : 20000 ~ 29999
	// -----------------------------------------------------------------

	/**
	 * 资源已经存在
	 */
	int BIZ_EXISTS = 20000;

}
