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

package com.power4j.ji.admin.modules.demo.notify.ding;

import com.github.jaemon.dinger.core.entity.DingerRequest;
import com.github.jaemon.dinger.support.CustomMessage;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/16
 * @since 1.0
 */
public class DingMarkDownMessage implements CustomMessage {

	@Override
	public String message(String projectId, DingerRequest request) {
		String content = request.getContent();
		String title = request.getTitle();
		List<String> phones = request.getPhones();
		StringBuilder text = new StringBuilder(title);
		if (phones != null && !phones.isEmpty()) {
			for (String phone : phones) {
				text.append("@").append(phone);
			}
		}
		return MessageFormat.format("## {0} \n\n  > 项目名称: {1}  \n\n{2}", text, projectId, content);
	}

}
