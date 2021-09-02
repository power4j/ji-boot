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

package com.power4j.ji.admin.modules.social.wxma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/25
 * @since 1.0
 */
@Data
public class UserInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 登录用户名
	 */
	@Schema(description = "用户名", example = "power4j")
	private String username;


	/**
	 * 姓名
	 */
	@Schema(description = "姓名", example = "momo")
	private String name;

	/**
	 * 创建时间
	 */
	@Nullable
	@Schema(description = "创建时间")
	private LocalDateTime createAt;
}
