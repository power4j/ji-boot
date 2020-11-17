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

package com.power4j.flygon.common.core.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页请求
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 11/17/20
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页请求")
public class PageRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "页码，从1开始")
	private Integer page;

	@ApiModelProperty(value = "每页条数")
	private Integer size;

	@ApiModelProperty(value = "升序字段")
	private List<String> asc;

	@ApiModelProperty(value = "将序字段")
	private List<String> desc;

}
