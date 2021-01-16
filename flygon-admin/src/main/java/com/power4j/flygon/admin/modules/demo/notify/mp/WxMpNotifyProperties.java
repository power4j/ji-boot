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

package com.power4j.flygon.admin.modules.demo.notify.mp;

import com.power4j.flygon.common.core.constant.CommonConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/16
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = WxMpNotifyProperties.PREFIX)
public class WxMpNotifyProperties {

	public final static String PREFIX = CommonConstant.PROPERTY_PREFIX + ".notify.wx-mp";

	private Boolean enabled = Boolean.FALSE;

	private String appId;

	private String secret;

	private List<String> subscribers = new ArrayList<>();

}
