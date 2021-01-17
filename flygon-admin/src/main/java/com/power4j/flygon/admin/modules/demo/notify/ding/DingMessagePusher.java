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

import cn.hutool.core.util.StrUtil;
import com.github.jaemon.dinger.DingerSender;
import com.github.jaemon.dinger.core.entity.DingerRequest;
import com.github.jaemon.dinger.core.entity.DingerResponse;
import com.github.jaemon.dinger.core.entity.enums.DingerResponseCodeEnum;
import com.github.jaemon.dinger.core.entity.enums.MessageSubType;
import com.power4j.flygon.common.core.constant.DateTimePattern;
import com.power4j.flygon.common.core.translator.ErrorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

import java.time.format.DateTimeFormatter;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/16
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class DingMessagePusher implements ApplicationListener<PayloadApplicationEvent<ErrorEvent>> {

	private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern(DateTimePattern.DATETIME_ISO8601);

	private final DingerSender dingerSender;

	@Override
	public void onApplicationEvent(PayloadApplicationEvent<ErrorEvent> event) {
		DingerResponse response = dingerSender.send(MessageSubType.MARKDOWN,
				DingerRequest.request(renderMarkdownMsg(event.getPayload()), "服务异常了！"));
		if (!DingerResponseCodeEnum.SUCCESS.code().equals(response.getCode())) {
			log.error("消息发送失败,code = {},msg = {}", response.getCode(), response.getMessage());
		}
	}

	private String renderMarkdownMsg(ErrorEvent event) {

		// @formatter:off

		final String msgTemplate = "" + StrUtil.CR + StrUtil.CR +
				"## 请求信息" + StrUtil.CR +
				"- 时间(UTC): `%s`" + StrUtil.CR +
				"- 请求ID: `%s`" + StrUtil.CR +
				"- 请求地址: `%s`" + StrUtil.CR +
				"- 请求方法: `%s`" + StrUtil.CR +
				"- 请求参数: `%s`" + StrUtil.CR + StrUtil.CR +
				"## 异常信息" + StrUtil.CR +
				"- 异常类型: `%s`" + StrUtil.CR+
				"- 异常消息: `%s`" + StrUtil.CR + StrUtil.CR +
				"## 异常堆栈" + StrUtil.CR +
				"```shell" + StrUtil.CR +
				"%s" + StrUtil.CR +
				"```" + StrUtil.CR;

		return String.format(msgTemplate,
				DATE_TIME_FORMATTER.format(event.getTimeUtc()),
				event.getRequestId(),
				event.getRequestUri(),
				event.getRequestMethod(),
				event.getRequestQueryString(),
				event.getEx(),
				event.getExMsg(),
				StrUtil.maxLength(event.getExStack(), 500));

		// @formatter:on
	}

}
