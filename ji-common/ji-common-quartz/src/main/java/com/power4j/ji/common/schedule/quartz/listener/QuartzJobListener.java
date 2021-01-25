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

import com.power4j.ji.common.core.util.DateTimeUtil;
import com.power4j.ji.common.core.util.SpringContextUtil;
import com.power4j.ji.common.schedule.quartz.event.ExecutionStageEnum;

import com.power4j.ji.common.schedule.quartz.event.TaskEndEvent;
import com.power4j.ji.common.schedule.quartz.event.TaskStartEvent;
import com.power4j.ji.common.schedule.quartz.constant.QuartzConstant;
import com.power4j.ji.common.schedule.quartz.job.ExecutionPlan;
import com.power4j.ji.common.schedule.quartz.util.EventHelper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
@Slf4j
public class QuartzJobListener implements JobListener {

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		final Object executionId = context.get(QuartzConstant.KEY_TASK_EXEC_ID);

		// @formatter:off
		if (log.isDebugEnabled()) {
			log.debug("[QTZ_JB] {} [FireInstance {}] [JobKey {}] to be executed",
					executionId,
					context.getFireInstanceId(),
					context.getJobDetail().getKey().toString());
		}
		// @formatter:on

		final ExecutionPlan executionPlan = (ExecutionPlan) context.getMergedJobDataMap()
				.get(QuartzConstant.KEY_TASK_PLAN);
		TaskStartEvent event = new TaskStartEvent();
		EventHelper.fillValues(event, context, executionPlan);
		event.setExecutionId(executionId.toString());
		event.setStage(ExecutionStageEnum.TASK_START);
		event.setStartTime(DateTimeUtil.utcNow());
		SpringContextUtil.publishEvent(event);

	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		final Object executionId = context.get(QuartzConstant.KEY_TASK_EXEC_ID);

		// @formatter:off
		if (log.isDebugEnabled()) {
			log.debug("[QTZ_JB] {} [FireInstance {}] [JobKey {}] execution vetoed",
					executionId,
					context.getFireInstanceId(),
					context.getJobDetail().getKey().toString());
		}
		// @formatter:on
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		final Object executionId = context.get(QuartzConstant.KEY_TASK_EXEC_ID);

		// @formatter:off
		if (log.isDebugEnabled()) {
			log.debug("[QTZ_JB] {} [FireInstance {}] [JobKey {}] was executed,ex = {}",
					executionId,
					context.getFireInstanceId(),
					context.getJobDetail().getKey().toString(),
					jobException == null ? "null" : jobException.getMessage());
		}
		// @formatter:on

		final ExecutionPlan executionPlan = (ExecutionPlan) context.getMergedJobDataMap()
				.get(QuartzConstant.KEY_TASK_PLAN);
		final Optional<JobExecutionException> ex = Optional.ofNullable(jobException);
		final Optional<Throwable> causeBy = ex.map(r -> r.getCause());
		final LocalDateTime utcNow = DateTimeUtil.utcNow();
		TaskEndEvent event = new TaskEndEvent();
		EventHelper.fillValues(event, context, executionPlan);
		event.setExecutionId(executionId.toString());
		event.setStage(ExecutionStageEnum.TASK_END);
		event.setStartTime(utcNow.minusNanos(Duration.ofMillis(context.getJobRunTime()).toNanos()));
		event.setEndTime(utcNow);
		event.setElapsed(Duration.ofMillis(context.getJobRunTime()));
		event.setSuccess(jobException == null);
		event.setErrMsg(ex.map(r -> r.getMessage()).orElse(null));
		event.setCauseBy(causeBy.map(r -> r.getClass().getName()).orElse(null));
		event.setCauseByMsg(causeBy.map(r -> r.getMessage()).orElse(null));
		SpringContextUtil.publishEvent(event);
	}

}
