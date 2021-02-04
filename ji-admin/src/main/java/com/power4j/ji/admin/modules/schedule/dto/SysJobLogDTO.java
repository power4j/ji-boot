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

import com.power4j.ji.common.data.crud.util.Unique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/25
 * @since 1.0
 */
@Data
public class SysJobLogDTO implements Unique, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主健
	 */
	@Schema(description = "ID")
	private Long id;

	/**
	 * 执行ID
	 */
	@Schema(description = "执行ID")
	private String executionId;

	/**
	 * 触发者,null表示由系统触发
	 */
	@Schema(description = "触发者")
	private String fireBy;

	/**
	 * 作业ID
	 */
	@Schema(description = "作业ID")
	private Long jobId;

	/**
	 * 作业组
	 */
	@Schema(description = "作业组")
	private String groupName;

	/**
	 * bean名称
	 */
	@Schema(description = "bean名称")
	private String taskBean;

	/**
	 * 任务描述
	 */
	@Schema(description = "任务描述")
	private String shortDescription;

	/**
	 * 执行开始时间
	 */
	@Schema(description = "执行开始时间")
	private LocalDateTime startTime;

	/**
	 * 执行结束时间
	 */
	@Schema(description = "执行结束时间")
	private LocalDateTime endTime;

	/**
	 * 执行耗时(毫秒)
	 */
	@Schema(description = "执行耗时(毫秒)")
	private Long executeMs;

	/**
	 * 是否成功
	 */
	@Schema(description = "是否成功")
	private Boolean success;

	/**
	 * 异常
	 */
	@Schema(description = "异常")
	private String ex;

	/**
	 * 异常信息
	 */
	@Schema(description = "异常信息")
	private String exMsg;

	@Override
	public Serializable getOnlyId() {
		return id;
	}

}
