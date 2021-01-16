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

package com.power4j.flygon.common.security.service;

import com.power4j.flygon.common.security.model.ApiToken;
import org.springframework.security.core.Authentication;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
public interface TokenService {

	/**
	 * 获取ApiToken
	 * @param tokenValue
	 * @return 获取失败返回 null
	 */
	ApiToken loadApiToken(String tokenValue);

	/**
	 * 删除 token
	 * @param tokenValue
	 * @return 删除成功返回true
	 */
	boolean deleteToken(String tokenValue);

	/**
	 * 创建 访问令牌
	 * @param authentication
	 * @return
	 */
	ApiToken createToken(Authentication authentication);

}
