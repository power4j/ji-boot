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

package com.power4j.ji.admin.modules.demo.notify.mp;

import com.power4j.ji.common.core.constant.DateTimePattern;
import com.power4j.ji.common.core.translator.ErrorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/16
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class WxMpMessagePusher implements ApplicationListener<PayloadApplicationEvent<ErrorEvent>> {

	private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern(DateTimePattern.DATETIME_ISO8601);

	private final WxMpNotifyProperties wxMpNotifyProperties;

	private final WxMpService wxMpService;

	@Override
	public void onApplicationEvent(PayloadApplicationEvent<ErrorEvent> event) {
		wxMpNotifyProperties.getSubscribers().forEach(u -> {
			sendNotifyMessage(event.getPayload(), u);
		});
	}

	private void sendNotifyMessage(ErrorEvent event, String wxUser) {
		// 模版id
		String msgTemplateId = "dOc4lx-jXC-gcn0PlfzjDDiIWblM7UIlUWmV8r4YtlM";
		// 点击模版消息要访问的网址
		String msgUrl = "http://ji-demo.etcd.ltd/";
		// 推送消息
		WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().toUser(wxUser).templateId(msgTemplateId)
				.url(msgUrl).data(renderTemplateData(event)).build();
		try {
			wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
		}
		catch (WxErrorException e) {
			log.error(e.getMessage(), e);
		}
	}

	private List<WxMpTemplateData> renderTemplateData(ErrorEvent event) {

		// @formatter:off

		List<WxMpTemplateData> list = new ArrayList<>();

		/**
		 * 消息模版定义
		 *
		 * 应用名称：{{appName.DATA}}
		 * 时间(UTC)：{{time.DATA}}
		 * 请求ID: {{reqId.DATA}}
		 * 请求地址: {{reqUrl.DATA}}
		 * 请求方法: {{reqMethod.DATA}}
		 * 异常信息：{{exMsg.DATA}}
		 */

		String dtStr = DATE_TIME_FORMATTER.format(event.getTimeUtc());
		list.add(new WxMpTemplateData("appName", event.getAppName(), "#FF00FF"));
		list.add(new WxMpTemplateData("time", dtStr, "#FF00FF"));
		list.add(new WxMpTemplateData("reqId", event.getRequestId(), "#FF00FF"));
		list.add(new WxMpTemplateData("reqUrl", event.getRequestUri(), "#FF00FF"));
		list.add(new WxMpTemplateData("reqMethod", event.getRequestMethod(), "#FF00FF"));
		list.add(new WxMpTemplateData("exMsg", String.format("%s - %s", event.getEx(), event.getExMsg()), "#FF00FF"));
		return list;

		// @formatter:on

	}

}
