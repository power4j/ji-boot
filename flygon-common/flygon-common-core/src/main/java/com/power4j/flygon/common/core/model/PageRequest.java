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

package com.power4j.flygon.common.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

/**
 * 分页请求
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(title = "分页请求")
public class PageRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(title = "页码，从1开始", defaultValue = "1",example = "1")
	@Min(value = 1)
	private Long page = 1L;

	@Schema(title = "每页条数", defaultValue = "10",example = "10")
	@Min(value = 1)
	@Max(value = 100)
	private Long size = 10L;

	@Schema(title = "升序字段",example = "[]")
	private List<String> asc;

	@Schema(title = "降序字段",example = "[]")
	private List<String> desc;

}
