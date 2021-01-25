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

package com.power4j.ji.common.schedule.quartz.util;

import com.power4j.ji.common.core.util.DateTimeUtil;
import com.power4j.ji.common.schedule.quartz.event.TaskEvent;
import com.power4j.ji.common.schedule.quartz.event.TriggerEvent;
import com.power4j.ji.common.schedule.quartz.job.ExecutionPlan;
import lombok.experimental.UtilityClass;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/25
 * @since 1.0
 */
@UtilityClass
public class EventHelper {

	/**
	 * 属性填充
	 * @param obj
	 * @param trigger
	 * @param context
	 * @param executionPlan
	 * @param <T>
	 * @return
	 */
	public <T extends TriggerEvent> T fillValues(T obj, Trigger trigger, JobExecutionContext context,
			ExecutionPlan executionPlan) {
		obj.setTriggerKey(trigger.getKey());
		obj.setJobKey(trigger.getJobKey());
		obj.setPlan(ExecutionPlan.copyOf(executionPlan));
		obj.setRefireCount(context.getRefireCount());
		obj.setPreviousFireTime(DateTimeUtil.ofUtc(trigger.getPreviousFireTime()));
		obj.setNextFireTime(DateTimeUtil.ofUtc(trigger.getNextFireTime()));
		obj.setHappenAt(DateTimeUtil.utcNow());
		return obj;
	}

	/**
	 * 属性填充
	 * @param obj
	 * @param context
	 * @param executionPlan
	 * @param <T>
	 * @return
	 */
	public <T extends TaskEvent> T fillValues(T obj, JobExecutionContext context, ExecutionPlan executionPlan) {
		obj.setPlan(ExecutionPlan.copyOf(executionPlan));
		obj.setHappenAt(DateTimeUtil.utcNow());
		return obj;
	}

}
