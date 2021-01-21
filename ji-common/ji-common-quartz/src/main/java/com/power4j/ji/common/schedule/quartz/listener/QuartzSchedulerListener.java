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

import com.power4j.ji.common.schedule.quartz.constant.QuartzConstant;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
@Slf4j
public class QuartzSchedulerListener implements SchedulerListener {

	@Override
	public void jobScheduled(Trigger trigger) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] jobScheduled:{},{}",trigger.getJobKey(),trigger.getDescription());
		}
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] jobUnscheduled:{},{}",triggerKey.getName(),triggerKey.getGroup());
		}
	}

	@Override
	public void triggerFinalized(Trigger trigger) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] triggerFinalized:{},{}",trigger.getJobKey(),trigger.getDescription());
		}
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] triggerPaused:{},{}",triggerKey.getName(),triggerKey.getGroup());
		}
	}

	@Override
	public void triggersPaused(String triggerGroup) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] triggersPaused:{},{}",triggerGroup);
		}
	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] triggerResumed:{},{}",triggerKey.getName(),triggerKey.getGroup());
		}
	}

	@Override
	public void triggersResumed(String triggerGroup) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] triggersResumed:{}",triggerGroup);
		}
	}

	@Override
	public void jobAdded(JobDetail jobDetail) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] jobAdded:{},{}",jobDetail.getJobClass().getName(), jobDetail.getKey().toString());
		}
	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] jobDeleted:{},{}",jobKey.getName(),jobKey.toString());
		}
	}

	@Override
	public void jobPaused(JobKey jobKey) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] jobPaused:{},{}",jobKey.getName(),jobKey.toString());
		}
	}

	@Override
	public void jobsPaused(String jobGroup) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] jobsPaused:{}",jobGroup);
		}
	}

	@Override
	public void jobResumed(JobKey jobKey) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] jobResumed:{},{}",jobKey.getName(),jobKey.toString());
		}
	}

	@Override
	public void jobsResumed(String jobGroup) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] jobsResumed,jobGroup:{}",jobGroup);
		}
	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] schedulerError: msg = {},cause ={},UnderlyingException = {}",msg,cause.getClass().getSimpleName(),
					Optional.ofNullable(cause.getUnderlyingException()).map(e -> e.getClass().getName()).orElse(null));
		}
	}

	@Override
	public void schedulerInStandbyMode() {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] schedulerInStandbyMode");
		}
	}

	@Override
	public void schedulerStarted() {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] schedulerStarted");
		}
	}

	@Override
	public void schedulerStarting() {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] schedulerStarting");
		}
	}

	@Override
	public void schedulerShutdown() {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] schedulerShutdown");
		}
	}

	@Override
	public void schedulerShuttingdown() {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] scheduler Shuttingdown");
		}
	}

	@Override
	public void schedulingDataCleared() {
		if(log.isDebugEnabled()){
			log.debug("[QTZ_SC] schedulingData Cleared");
		}
	}

}
