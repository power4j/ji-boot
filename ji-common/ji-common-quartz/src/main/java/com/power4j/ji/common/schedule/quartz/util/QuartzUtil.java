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

import cn.hutool.core.lang.Pair;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.core.util.DateTimeUtil;
import com.power4j.ji.common.schedule.quartz.job.ExecutionPlan;
import com.power4j.ji.common.schedule.quartz.job.MisFirePolicyEnum;
import com.power4j.ji.common.schedule.quartz.job.PlanStatusEnum;
import com.power4j.ji.common.schedule.quartz.job.QuartzJob;
import com.power4j.ji.common.schedule.quartz.constant.QuartzConstant;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class QuartzUtil {

	/**
	 * 组装Key
	 * @param planId
	 * @param planGroup
	 * @return
	 */
	public final JobKey makeJobKey(Object planId, @Nullable String planGroup) {
		return JobKey.jobKey(QuartzConstant.JOB_NAME_PREFIX + planId,
				Optional.ofNullable(planGroup).orElse(QuartzConstant.DEFAULT_JOB_GROUP));
	}

	/**
	 * 组装Key
	 * @param planId
	 * @param planGroup
	 * @return
	 */
	public final TriggerKey makeTriggerKey(Object planId, @Nullable String planGroup) {
		return TriggerKey.triggerKey(QuartzConstant.TRIGGER_NAME_PREFIX + planId,
				Optional.ofNullable(planGroup).orElse(QuartzConstant.DEFAULT_JOB_GROUP));
	}

	/**
	 * 构建 JobDetail CronTrigger
	 * @param plan
	 * @return
	 */
	protected Pair<JobDetail,CronTrigger> buildCronPlan(ExecutionPlan plan){
		JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
				.withIdentity(makeJobKey(plan.getPlanId(), plan.getGroupName()))
				.requestRecovery(plan.getFailRecover()).build();

		jobDetail.getJobDataMap().put(QuartzConstant.KEY_TASK_PLAN, plan);
		return Pair.of(jobDetail,buildCronTrigger(plan));
	}

	protected CronTrigger buildCronTrigger(ExecutionPlan plan){
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(plan.getCron());
		applyMisFirePolicy(scheduleBuilder, plan.getMisFirePolicy());

		CronTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(makeTriggerKey(plan.getPlanId(), plan.getGroupName()))
				.withSchedule(scheduleBuilder)
				.build();
		return trigger;
	}

	public CronScheduleBuilder applyMisFirePolicy(CronScheduleBuilder cronScheduleBuilder, MisFirePolicyEnum policy) {
		switch (policy) {

		case RESCUE_ONE:
			cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
			break;
		case RESCUE_ALL:
			cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
			break;
		case RESCUE_NONE:
			cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
			break;
		default:
			throw new IllegalStateException("Unknown case:" + policy.name());
		}
		return cronScheduleBuilder;
	}

	/**
	 * 获取触发器
	 * @param planId
	 * @param planGroup
	 * @return
	 */
	@Nullable
	public CronTrigger getCronTrigger(Scheduler scheduler, Object planId, @Nullable String planGroup) {
		try {
			return (CronTrigger) scheduler.getTrigger(makeTriggerKey(planId, planGroup));
		}
		catch (SchedulerException e) {
			throw new BizException(SysErrorCodes.E_JOB_FAIL, e);
		}
	}

	/**
	 * 返回下一次(应该)执行的时间,null表示不会执行
	 * @param scheduler
	 * @param planId
	 * @param planGroup
	 * @return
	 */
	public Optional<LocalDateTime> getNextScheduleTime(Scheduler scheduler, Object planId, @Nullable String planGroup) {
		CronTrigger trigger = getCronTrigger(scheduler, planId, planGroup);
		if (trigger == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(trigger.getNextFireTime()).map(DateTimeUtil::toLocalDateTime);
	}

	/**
	 * 创建调度计划
	 * @param scheduler
	 * @param plan
	 * @return 返回下一次(应该)执行的时间,null表示不会执行
	 */
	public Optional<LocalDateTime> createPlan(Scheduler scheduler, ExecutionPlan plan) {
		try {

			Pair<JobDetail,CronTrigger> entry = buildCronPlan(plan);
			scheduler.scheduleJob(entry.getKey(), entry.getValue());
			if (PlanStatusEnum.PAUSE.equals(plan.getStatus())) {
				pausePlan(scheduler, plan.getPlanId(), plan.getGroupName());
			}
			Optional<LocalDateTime> nextRun = Optional.ofNullable(entry.getValue().getNextFireTime())
					.map(DateTimeUtil::toLocalDateTime);
			if (log.isDebugEnabled()) {
				log.debug("Job #{}({}) 的下一次计划执行时间为:{}", plan.getPlanId(), plan.getDescription(),
						DateTimeUtil.forLogging(nextRun.orElse(null)));
			}
			return nextRun;
		}
		catch (SchedulerException e) {
			throw new BizException(SysErrorCodes.E_JOB_FAIL, e);
		}
	}

	/**
	 * @param scheduler
	 * @param plan
	 * @return 返回下一次计划执行时间
	 * @deprecated
	 */
	public Optional<LocalDateTime> updatePlan(Scheduler scheduler, ExecutionPlan plan) {
		try {
			final JobKey jobKey = makeJobKey(plan.getPlanId(), plan.getGroupName());
			scheduler.deleteJob(jobKey);
			return createPlan(scheduler, plan);
		}
		catch (SchedulerException e) {
			throw new BizException(SysErrorCodes.E_JOB_FAIL, e);
		}
	}

	/**
	 * 重新调度已经存在的作业
	 * @param scheduler
	 * @param plan
	 * @return
	 */
	public Optional<LocalDateTime> reschedulePlan(Scheduler scheduler, ExecutionPlan plan){
		TriggerKey triggerKey = makeTriggerKey(plan.getPlanId(),plan.getGroupName());
		CronTrigger newTrigger = buildCronTrigger(plan);
		try {
			scheduler.rescheduleJob(triggerKey,newTrigger);
		} catch (SchedulerException e) {
			throw new BizException(SysErrorCodes.E_JOB_FAIL, e);
		}
		Optional<LocalDateTime> nextRun = Optional.ofNullable(newTrigger.getNextFireTime())
				.map(DateTimeUtil::toLocalDateTime);
		if (log.isDebugEnabled()) {
			log.debug("Job #{}({}) 的下一次计划执行时间为:{}", plan.getPlanId(), plan.getDescription(),
					DateTimeUtil.forLogging(nextRun.orElse(null)));
		}
		return nextRun;
	}

	/**
	 * 立即触发任务
	 * @param scheduler
	 * @param plan
	 */
	public void triggerNow(Scheduler scheduler, ExecutionPlan plan) {
		try {
			// 参数
			JobDataMap dataMap = new JobDataMap();
			dataMap.put(QuartzConstant.KEY_TASK_PLAN, plan);

			scheduler.triggerJob(makeJobKey(plan.getPlanId(), plan.getGroupName()), dataMap);
		}
		catch (SchedulerException e) {
			throw new BizException(SysErrorCodes.E_JOB_FAIL, e);
		}
	}

	/**
	 * 停止调度
	 * @param scheduler
	 * @param planId
	 * @param planGroup
	 */
	public void pausePlan(Scheduler scheduler, Object planId, @Nullable String planGroup) {
		try {
			scheduler.pauseJob(makeJobKey(planId, planGroup));
		}
		catch (SchedulerException e) {
			throw new BizException(SysErrorCodes.E_JOB_FAIL, e);
		}
	}

	/**
	 * 恢复调度
	 * @param scheduler
	 * @param planId
	 * @param planGroup
	 * @return 返回下一次计划执行时间
	 */
	public Optional<LocalDateTime> resumePlan(Scheduler scheduler, Object planId, @Nullable String planGroup) {
		try {
			scheduler.resumeJob(makeJobKey(planId, planGroup));
			Optional<LocalDateTime> nextRun = getNextScheduleTime(scheduler, planId, planGroup);
			if (log.isDebugEnabled()) {
				log.debug("Job #{}({}) 的下一次计划执行时间为:{}", planId, planGroup,
						DateTimeUtil.forLogging(nextRun.orElse(null)));
			}
			return nextRun;
		}
		catch (SchedulerException e) {
			throw new BizException(SysErrorCodes.E_JOB_FAIL, e);
		}
	}

	/**
	 * 删除调度
	 * @param scheduler
	 * @param planId
	 * @param planGroup
	 */
	public void deletePlan(Scheduler scheduler, Object planId, @Nullable String planGroup) {
		try {
			scheduler.deleteJob(makeJobKey(planId, planGroup));
		}
		catch (SchedulerException e) {
			throw new BizException(SysErrorCodes.E_JOB_FAIL, e);
		}
	}

}
