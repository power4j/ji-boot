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

package com.power4j.ji.admin.modules.schedule.util;

import cn.hutool.core.text.CharSequenceUtil;
import com.power4j.ji.admin.modules.schedule.dto.ScheduleJobDTO;
import com.power4j.ji.admin.modules.schedule.entity.ScheduleLog;
import com.power4j.ji.common.schedule.quartz.event.TaskEndEvent;
import com.power4j.ji.common.schedule.quartz.job.ExecutionPlan;
import com.power4j.ji.common.schedule.quartz.job.MisFirePolicyEnum;
import com.power4j.ji.common.schedule.quartz.job.PlanStatusEnum;
import lombok.experimental.UtilityClass;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/20
 * @since 1.0
 */
@UtilityClass
public class ScheduleUtil {

	/**
	 * SysJobDTO 转换
	 * @param sysJob
	 * @return
	 */
	public ExecutionPlan toExecutionPlan(ScheduleJobDTO sysJob) {
		ExecutionPlan executionPlan = new ExecutionPlan();
		executionPlan.setPlanId(sysJob.getId());
		executionPlan.setGroupName(sysJob.getGroupName());
		executionPlan.setCron(sysJob.getCron());
		executionPlan.setTaskBean(sysJob.getTaskBean());
		executionPlan.setParam(sysJob.getParam());
		executionPlan.setDescription(sysJob.getShortDescription());
		executionPlan.setStatus(PlanStatusEnum.parse(sysJob.getStatus()));
		executionPlan.setMisFirePolicy(MisFirePolicyEnum.parse(sysJob.getMisFirePolicy()));
		executionPlan.setFailRecover(sysJob.getFailRecover());
		executionPlan.setErrorRetry(sysJob.getErrorRetry());

		return executionPlan;

	}

	/**
	 * TaskEndEvent 转换
	 * @param event
	 * @return
	 */
	public ScheduleLog toSysJobLog(TaskEndEvent event) {
		ScheduleLog jobLog = new ScheduleLog();
		jobLog.setExecutionId(event.getExecutionId());
		jobLog.setJobId(event.getPlan().getPlanId());
		jobLog.setFireBy(event.getFireBy());
		jobLog.setGroupName(event.getPlan().getGroupName());
		jobLog.setTaskBean(event.getPlan().getTaskBean());
		jobLog.setShortDescription(event.getPlan().getDescription());
		jobLog.setStartTime(event.getStartTime());
		jobLog.setEndTime(event.getEndTime());
		jobLog.setExecuteMs(event.getElapsed().toMillis());
		jobLog.setSuccess(event.getSuccess());
		jobLog.setEx(CharSequenceUtil.maxLength(event.getCauseBy(), 255));
		jobLog.setExMsg(CharSequenceUtil.maxLength(event.getCauseByMsg(), 255));
		return jobLog;
	}

}
