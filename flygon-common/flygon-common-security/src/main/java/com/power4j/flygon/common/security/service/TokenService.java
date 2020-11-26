package com.power4j.flygon.common.security.service;

import com.power4j.flygon.common.security.model.ApiToken;

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
	 * @param username
	 * @return
	 */
	ApiToken createToken(String username);

}
