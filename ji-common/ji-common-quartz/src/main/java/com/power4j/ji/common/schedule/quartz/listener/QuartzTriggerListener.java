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

package com.power4j.ji.common.schedule.quartz.listener;

import cn.hutool.core.util.IdUtil;
import com.power4j.ji.common.core.util.DateTimeUtil;
import com.power4j.ji.common.core.util.SpringContextUtil;
import com.power4j.ji.common.schedule.quartz.constant.QuartzConstant;
import com.power4j.ji.common.schedule.quartz.event.ExecutionStageEnum;
import com.power4j.ji.common.schedule.quartz.event.TriggerEndEvent;
import com.power4j.ji.common.schedule.quartz.event.TriggerStartEvent;
import com.power4j.ji.common.schedule.quartz.job.ExecutionPlan;
import com.power4j.ji.common.schedule.quartz.job.PlanStatusEnum;
import com.power4j.ji.common.schedule.quartz.util.EventHelper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
@Slf4j
public class QuartzTriggerListener implements TriggerListener {

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		Object executionId = context.get(QuartzConstant.KEY_TASK_EXEC_ID);
		if (executionId == null) {
			executionId = IdUtil.objectId();
			context.put(QuartzConstant.KEY_TASK_EXEC_ID, executionId);
		}
		final String fireBy = context.getMergedJobDataMap().getString(QuartzConstant.KEY_EXEC_FIRE_BY);
		// @formatter:off
		if (log.isDebugEnabled()) {
			log.debug("[QTZ_TG] {} [FireInstance {}] [TriggerKey {}] trigger fired {},previous fire time : {},fire by {}",
					executionId,
					context.getFireInstanceId(),
					trigger.getKey().toString(),
					context.isRecovering()?"(For recovering Job)":"",
					DateTimeUtil.forLogging(DateTimeUtil.toLocalDateTime(context.getPreviousFireTime())),
					fireBy);
		}
		// @formatter:on
		final ExecutionPlan executionPlan = (ExecutionPlan) context.getMergedJobDataMap()
				.get(QuartzConstant.KEY_TASK_PLAN);
		TriggerStartEvent triggerStartEvent = new TriggerStartEvent();
		EventHelper.fillValues(triggerStartEvent, trigger, context, executionPlan);
		triggerStartEvent.setExecutionId(executionId.toString());
		triggerStartEvent.setStage(ExecutionStageEnum.TRIGGER_START);
		triggerStartEvent.setFireBy(fireBy);
		SpringContextUtil.publishEvent(triggerStartEvent);
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		final ExecutionPlan executionPlan = (ExecutionPlan) context.getMergedJobDataMap()
				.get(QuartzConstant.KEY_TASK_PLAN);
		final boolean runForce = Optional
				.ofNullable((Boolean) context.getMergedJobDataMap().get(QuartzConstant.KEY_EXEC_FORCE)).orElse(false);
		final Object executionId = context.get(QuartzConstant.KEY_TASK_EXEC_ID);
		final boolean accept = runForce || PlanStatusEnum.NORMAL.equals(executionPlan.getStatus());

		if (!accept) {
			// @formatter:off
			log.info("[QTZ_TG] {} [FireInstance {}] [TriggerKey {}] job vetoed,runForce = {},plan status = {}",
					executionId,
					context.getFireInstanceId(),
					trigger.getKey().toString(),
					runForce,
					executionPlan.getStatus());
			// @formatter:on
		}

		return !accept;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		// @formatter:off
		log.warn("[QTZ_TG] [TriggerKey {}] trigger misfired,next fire time : {}",
				trigger.getKey().toString(),
				DateTimeUtil.forLogging(DateTimeUtil.toLocalDateTime(trigger.getNextFireTime()))
		);
		// @formatter:on
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			Trigger.CompletedExecutionInstruction triggerInstructionCode) {
		final Object executionId = context.get(QuartzConstant.KEY_TASK_EXEC_ID);

		// @formatter:off
		if (log.isDebugEnabled()) {
			log.debug("[QTZ_TG] {} [FireInstance {}] [TriggerKey {}] trigger complete with instruction :{}, next fire time : {}",
					executionId,
					context.getFireInstanceId(),
					trigger.getKey().toString(),
					triggerInstructionCode.name(),
					DateTimeUtil.forLogging(DateTimeUtil.toLocalDateTime(trigger.getNextFireTime())));
		}
		// @formatter:on

		final String fireBy = context.getMergedJobDataMap().getString(QuartzConstant.KEY_EXEC_FIRE_BY);
		final ExecutionPlan executionPlan = (ExecutionPlan) context.getMergedJobDataMap()
				.get(QuartzConstant.KEY_TASK_PLAN);
		TriggerEndEvent triggerEndEvent = new TriggerEndEvent();
		EventHelper.fillValues(triggerEndEvent, trigger, context, executionPlan);
		triggerEndEvent.setExecutionId(executionId.toString());
		triggerEndEvent.setInstructionCode(triggerInstructionCode);
		triggerEndEvent.setStage(ExecutionStageEnum.TRIGGER_END);
		triggerEndEvent.setFireBy(fireBy);
		SpringContextUtil.publishEvent(triggerEndEvent);
	}

}
