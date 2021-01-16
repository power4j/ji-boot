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

package com.power4j.flygon.admin.modules.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用户查询
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-19
 * @since 1.0
 */
@Data
@Schema(title = "用户查询")
public class SearchSysUserVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "创建日期范围", example = "[\"2020-01-01\",\"2020-12-31\"]")
	private LocalDate[] createIn;

	@Schema(description = "用户名,支持模糊查询", example = "admin")
	private String username;

	@Schema(description = "状态", example = "0")
	private String status;

}
