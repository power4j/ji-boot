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

package com.power4j.ji.common.ureport.config;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/2/12
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
@ImportResource("classpath:ureport-console-context.xml")
public class UReportAutoConfig {

	@Bean
	public ServletRegistrationBean<UReportServlet> uReportServlet() {
		return new ServletRegistrationBean<>(new UReportServlet(), "/ureport/*");
	}

}
