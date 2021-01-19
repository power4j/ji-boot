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

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
public class QuartzSchedulerListener implements SchedulerListener {

	@Override
	public void jobScheduled(Trigger trigger) {
		// Ignore
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		// Ignore
	}

	@Override
	public void triggerFinalized(Trigger trigger) {
		// Ignore
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		// Ignore
	}

	@Override
	public void triggersPaused(String triggerGroup) {
		// Ignore
	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		// Ignore
	}

	@Override
	public void triggersResumed(String triggerGroup) {
		// Ignore
	}

	@Override
	public void jobAdded(JobDetail jobDetail) {
		// Ignore
	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		// Ignore
	}

	@Override
	public void jobPaused(JobKey jobKey) {
		// Ignore
	}

	@Override
	public void jobsPaused(String jobGroup) {
		// Ignore
	}

	@Override
	public void jobResumed(JobKey jobKey) {
		// Ignore
	}

	@Override
	public void jobsResumed(String jobGroup) {
		// Ignore
	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		// Ignore
	}

	@Override
	public void schedulerInStandbyMode() {
		// Ignore
	}

	@Override
	public void schedulerStarted() {
		// Ignore
	}

	@Override
	public void schedulerStarting() {
		// Ignore
	}

	@Override
	public void schedulerShutdown() {
		// Ignore
	}

	@Override
	public void schedulerShuttingdown() {
		// Ignore
	}

	@Override
	public void schedulingDataCleared() {
		// Ignore
	}

}
