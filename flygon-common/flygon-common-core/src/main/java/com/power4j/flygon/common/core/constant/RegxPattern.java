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
 * 正则常量
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/20
 * @since 1.0
 */
public interface RegxPattern {

	/**
	 * 手机号
	 */
	String MOBILE_PHONE_NUMBER = "(?:0|86|\\+86)?1[3-9]\\d{9}";

	/**
	 * 中国身份证
	 */
	String CN_ID_CARD_NUMBER = "[1-9]\\d{5}[1-2]\\d{3}((0\\d)|(1[0-2]))(([012]\\d)|3[0-1])\\d{3}(\\d|X|x)";

}
