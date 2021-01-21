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

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

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
		if (log.isDebugEnabled()) {
			log.debug("[QTZ_JB] [FireInstance {}] [JobKey {}] to be executed", context.getFireInstanceId(), context.getJobDetail().getKey().toString());
		}
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		if (log.isDebugEnabled()) {
			log.debug("[QTZ_JB] [FireInstance {}] [JobKey {}] execution vetoed", context.getFireInstanceId(), context.getJobDetail().getKey().toString());
		}

	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		if (log.isDebugEnabled()) {
			log.debug("[QTZ_JB] [FireInstance {}] [JobKey {}] was executed,ex = {}", context.getFireInstanceId(), context.getJobDetail().getKey().toString(),
					jobException == null ? "null" : jobException.getMessage());
		}
	}

}
