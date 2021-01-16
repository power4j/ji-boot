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

package com.power4j.flygon.common.captcha.config;

import com.power4j.flygon.common.core.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/11
 * @since 1.0
 */
@Getter
@Setter
@ConfigurationProperties(CaptchaProperties.PREFIX)
public class CaptchaProperties {

	public static final String PREFIX = CommonConstant.PROPERTY_PREFIX + ".captcha";

	/**
	 * 受验证码保护的地址,支持Ant风格的表达式
	 */
	private List<String> consumer = new ArrayList<>();

	/**
	 * 验证码生成地址
	 */
	private String codeUrl = "/code";

	/**
	 * 有效期,单位秒
	 */
	private long expire = 60L;

}
