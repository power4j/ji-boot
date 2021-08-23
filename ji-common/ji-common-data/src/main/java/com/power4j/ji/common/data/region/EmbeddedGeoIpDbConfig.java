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

package com.power4j.ji.common.data.region;

import cn.hutool.core.io.resource.ResourceUtil;
import com.power4j.ji.common.data.region.embedded.InMemoryGeoIpRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/23
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnResource(resources = EmbeddedGeoIpDbConfig.IP2REGION_DB_FILE)
public class EmbeddedGeoIpDbConfig {

	static final String IP2REGION_DB_FILE = "classpath:ip2region/ip2region.db";

	@Bean
	@ConditionalOnMissingBean
	public GeoIpRegistry inMemoryRegistry() {
		InputStream inputStream = ResourceUtil.getStream(IP2REGION_DB_FILE);
		return InMemoryGeoIpRegistry.load(inputStream);
	}

}
