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

package com.power4j.flygon.admin.modules.demo.notify.ding;

import com.github.jaemon.dinger.DingerSender;
import com.github.jaemon.dinger.support.CustomMessage;
import com.power4j.flygon.common.core.constant.CommonConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.jaemon.dinger.constant.DingerConstant.MARKDOWN_MESSAGE;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/16
 * @since 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = CommonConstant.PROPERTY_PREFIX + ".notify.ding-talk", name = "enabled",
		havingValue = "true")
public class DingNotifyConfig {

	@Bean
	public DingMessagePusher dingMessagePusher(DingerSender dingerSender) {
		return new DingMessagePusher(dingerSender);
	}

	@Bean(MARKDOWN_MESSAGE)
	public CustomMessage markDownMessage() {
		return new DingMarkDownMessage();
	}

}
