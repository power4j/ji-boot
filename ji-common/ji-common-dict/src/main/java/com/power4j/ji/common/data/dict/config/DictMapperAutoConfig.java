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

package com.power4j.ji.common.data.dict.config;

import cn.hutool.core.bean.BeanUtil;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.kit.common.data.dict.support.DictItemConverter;
import com.power4j.kit.common.data.dict.support.RestResponseProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/26
 * @since 1.0
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class DictMapperAutoConfig {

	@Bean
	public RestResponseProcessor<?> restResponseProcessor() {
		return ApiResponseUtil::ok;
	}

	@Bean
	public DictItemConverter<?> dictItemConverter() {
		return (item) -> {
			Map<String, Object> data = BeanUtil.beanToMap(item);
			data.put("color", item.getStyle());
			return data;
		};
	}

}
