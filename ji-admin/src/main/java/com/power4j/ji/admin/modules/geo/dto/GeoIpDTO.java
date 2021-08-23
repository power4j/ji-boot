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

package com.power4j.ji.admin.modules.geo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/23
 * @since 1.0
 */
@Data
public class GeoIpDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "ID")
	private String id;

	@Schema(description = "国家", example = "星星国")
	private String country;

	@Schema(description = "区域")
	private String zone;

	@Schema(description = "省", example = "四川")
	private String province;

	@Schema(description = "市", example = "成都")
	private String city;

	@Schema(description = "ISP")
	private String isp;

}
