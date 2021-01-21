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

package com.power4j.ji.common.schedule.quartz.job;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
@Data
public class ExecutionPlan implements Plan, Serializable {

	private final static long serialVersionUID = 1L;

	private Long planId;

	/**
	 * 分组
	 */
	@Nullable
	private String groupName;

	/**
	 * Cron 表达式
	 */
	private String cron;

	/**
	 * bean名称
	 */
	private String taskBean;

	/**
	 * 任务参数
	 */
	private String param;

	/**
	 * 描述
	 */
	@Nullable
	private String description;

	private PlanStatusEnum status;

	private MisFirePolicyEnum misFirePolicy;

	/**
	 * 允许故障转移
	 */
	private Boolean failRecover;

	/**
	 * 允许失败重试
	 */
	private Boolean errorRetry;

}
