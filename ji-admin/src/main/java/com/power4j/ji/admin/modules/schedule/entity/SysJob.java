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

package com.power4j.ji.admin.modules.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.ji.common.data.crud.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/20
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_job")
public class SysJob extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 作业组
	 */
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
	 * 备注
	 */
	private String remarks;

	/**
	 * 状态 0 正常 1 停止调度
	 */
	private String status;

	/**
	 * 调度丢失补救策略
	 */
	private String misFirePolicy;

	/**
	 * 允许故障转移
	 */
	private Boolean failRecover;

	/**
	 * 允许失败重试
	 */
	private Boolean errorRetry;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 更新人
	 */
	private String updateBy;

}
