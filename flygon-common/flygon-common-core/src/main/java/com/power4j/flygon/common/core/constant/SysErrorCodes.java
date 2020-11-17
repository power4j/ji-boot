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
 * @date 2020-11-17
 * @since 1.0
 */
public interface SysErrorCodes {

	// -----------------------------------------------------------------
	// Common error code : -9999 ~ 19999
	// -----------------------------------------------------------------

	/**
	 * 失败
	 */
	int E_FAIL = -1;

	/**
	 * 成功
	 */
	int E_OK = 200;

	/**
	 * 无效请求
	 */
	int E_BAD_REQUEST = 400;

	/**
	 * 未认证
	 */
	int E_UNAUTHORIZED = 401;

	/**
	 * 无权限
	 */
	int E_FORBIDDEN = 403;

	/**
	 * 资源不存在
	 */
	int E_NOT_FOUND = 404;

	/**
	 * 资源冲突
	 */
	int E_CONFLICT = 409;

	/**
	 * 服务器错误
	 */
	int E_SERVER_ERROR = 500;

	/**
	 * 参数绑定失败
	 */
	int E_PARAM_MISS = 10101;

	/**
	 * 参数绑定失败
	 */
	int E_PARAM_BIND = 10102;

	/**
	 * 参数校验失败
	 */
	int E_PARAM_INVALID = 10103;

	/**
	 * 参数类型错误
	 */
	int E_PARAM_TYPE = 10104;

	/**
	 * 消息不可读
	 */
	int E_MSG_NOT_READABLE = 10105;

	/**
	 * 不支持的方法
	 */
	int E_METHOD_NOT_SUPPORTED = 10106;

	/**
	 * 不支持的媒体类型
	 */
	int E_MEDIA_TYPE_NOT_SUPPORTED = 10107;

	/**
	 * 数据已经存在
	 */
	int E_DATA_EXISTS = 10201;

	/**
	 * 数据不存在
	 */
	int E_DATA_NOT_EXISTS = 10202;

	// -----------------------------------------------------------------
	// Biz error : 20000 ~ 29999
	// -----------------------------------------------------------------

}
