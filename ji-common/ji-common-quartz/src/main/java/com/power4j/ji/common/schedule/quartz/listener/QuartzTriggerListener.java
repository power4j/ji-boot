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
import com.power4j.ji.common.schedule.quartz.constant.QuartzConstant;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

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
		Object runId = context.get(QuartzConstant.KEY_TASK_EXEC_ID);
		if (runId == null){
			runId = IdUtil.objectId();
			context.put(QuartzConstant.KEY_TASK_EXEC_ID, runId);
		}
		// @formatter:off

		if (log.isDebugEnabled()) {
			log.debug("[QTZ_TG] {} [FireInstance {}] [TriggerKey {}] trigger fired{},previous fire time : {}",
					runId,
					context.getFireInstanceId(),
					trigger.getKey().toString(),
					context.isRecovering()?"(For recovering Job)":"",
					DateTimeUtil.forLogging(DateTimeUtil.toLocalDateTime(context.getPreviousFireTime())));
		}

		// @formatter:on
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
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
		// @formatter:off

		Object runId = context.get(QuartzConstant.KEY_TASK_EXEC_ID);
		if (log.isDebugEnabled()) {
			log.debug("[QTZ_TG] {} [FireInstance {}] [TriggerKey {}] trigger complete with instruction :{}, next fire time : {}",
					runId,
					context.getFireInstanceId(),
					trigger.getKey().toString(),
					triggerInstructionCode.name(),
					DateTimeUtil.forLogging(DateTimeUtil.toLocalDateTime(trigger.getNextFireTime())));
		}

		// @formatter:on
	}

}
