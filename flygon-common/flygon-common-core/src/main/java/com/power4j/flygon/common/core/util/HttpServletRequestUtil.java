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

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * HttpServletRequest Util
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class HttpServletRequestUtil {

	public final static String UNKNOWN = "unknown";

	public final static String X_FORWARD_FOR = "x-forwarded-for";

	public final static String PROXY_CLIENT_IP = "Proxy-Client-IP";

	public final static String WL_Proxy_Client_IP = "WL-Proxy-Client-IP";

	public final static String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";

	public final static String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

	/**
	 * 获取IP地址
	 * <ul>
	 * <li>用Nginx等反向代理软件时，不能通过request.getRemoteAddr()获取IP地址</li>
	 * <li>如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址</li>
	 * </ul>
	 * @see <a href=
	 * "https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/X-Forwarded-For">X-Forwarded-For</a>
	 * @param request
	 * @return
	 */
	public static Optional<String> getRemoteAddr(HttpServletRequest request) {
		String address = request.getHeader(X_FORWARD_FOR);
		if (StrUtil.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
			address = request.getHeader(PROXY_CLIENT_IP);
		}
		if (StrUtil.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
			address = request.getHeader(WL_Proxy_Client_IP);
		}
		if (StrUtil.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
			address = request.getHeader(HTTP_CLIENT_IP);
		}
		if (StrUtil.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
			address = request.getHeader(HTTP_X_FORWARDED_FOR);
		}
		if (StrUtil.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
			address = request.getRemoteAddr();
		}
		return Optional.ofNullable(address).map(s -> StrUtil.split(s, ",")[0]);
	}

	/**
	 * 获取IP地址
	 * @see #getRemoteAddr(HttpServletRequest)
	 * @param request
	 * @param defaultValue
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request, String defaultValue) {
		return getRemoteAddr(request).orElse(defaultValue);
	}

}
