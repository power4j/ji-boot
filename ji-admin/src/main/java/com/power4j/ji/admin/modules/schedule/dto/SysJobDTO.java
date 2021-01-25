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

package com.power4j.ji.admin.modules.schedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power4j.ji.common.core.validate.Groups;
import com.power4j.ji.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/20
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysJobDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 作业组
	 */
	@Schema(description = "作业组", example = "Test")
	@NotBlank(groups = { Groups.Default.class })
	@Size(max = 40, groups = { Groups.Default.class })
	private String groupName;

	/**
	 * Cron 表达式
	 */
	@Schema(description = "Cron", example = "0 5/30 * * * ? *")
	@NotBlank(groups = { Groups.Default.class })
	@Size(max = 40, groups = { Groups.Default.class })
	private String cron;

	/**
	 * bean名称
	 */
	@Schema(description = "bean名称", example = "testTask")
	@NotBlank(groups = { Groups.Default.class })
	@Size(max = 250, groups = { Groups.Default.class })
	private String taskBean;

	/**
	 * 任务参数
	 */
	@Schema(description = "任务参数", example = "key=val1,key2=val2")
	@Size(max = 250, groups = { Groups.Default.class })
	private String param;

	/**
	 * 任务说明
	 */
	@Schema(description = "任务说明")
	@NotBlank(groups = { Groups.Default.class })
	@Size(max = 20, groups = { Groups.Default.class })
	private String shortDescription;

	/**
	 * 状态 0 正常 1 停止调度
	 */
	@Schema(description = "状态 0 正常 1 停止调度", example = "0")
	@NotNull(groups = { Groups.Default.class })
	@Pattern(regexp = "0|1", message = "状态只能是 0 或者 1", groups = { Groups.Default.class })
	private String status;

	/**
	 * 调度丢失处理策略
	 */
	@Schema(description = "调度丢失处理策略", example = "0")
	@NotNull(groups = { Groups.Default.class })
	@Pattern(regexp = "0|1|2", message = "只能是 0 , 1 , 2", groups = { Groups.Default.class })
	private String misFirePolicy;

	/**
	 * 允许故障转移
	 */
	@Schema(description = "允许故障转移", example = "false")
	@NotNull(groups = { Groups.Default.class })
	private Boolean failRecover;

	/**
	 * 允许失败重试
	 */
	@Schema(description = "允许失败重试", example = "false")
	@NotNull(groups = { Groups.Default.class })
	private Boolean errorRetry;

	/**
	 * 创建人
	 */
	@Schema(description = "创建者", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String createBy;

	/**
	 * 更新人
	 */
	@Schema(description = "修改者", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String updateBy;

}
