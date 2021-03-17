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

package com.power4j.ji.common.data.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power4j.ji.common.core.validate.Groups;
import com.power4j.ji.common.data.crud.util.SysCtl;
import com.power4j.ji.common.data.crud.util.Unique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Getter
@Setter
public abstract class BaseDTO implements Unique, SysCtl {

	/**
	 * 主健
	 */
	@Schema(description = "主健")
	@NotNull(groups = { Groups.Update.class })
	@Null(groups = { Groups.Create.class }, message = "主健ID由系统分配")
	private Long id;

	/**
	 * 数据标记 0 普通数据, 1 系统保护数据
	 */
	@Schema(description = "数据标记 0 普通数据, 1 系统保护数据", defaultValue = "0")
	private Integer sysFlag;

	/**
	 * 更新时间
	 */
	@Nullable
	@Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime updateAt;

	/**
	 * 创建时间
	 */
	@Nullable
	@Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime createAt;

	@Override
	public Serializable getOnlyId() {
		return id;
	}

	@Override
	public Integer getCtlFlag() {
		return sysFlag;
	}

}
