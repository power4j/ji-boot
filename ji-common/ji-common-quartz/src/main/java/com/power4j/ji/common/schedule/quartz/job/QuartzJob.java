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

package com.power4j.ji.common.schedule.quartz.job;

import com.power4j.ji.common.core.util.SpringContextUtil;
import com.power4j.ji.common.schedule.quartz.constant.QuartzConstant;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/18
 * @since 1.0
 */
@Slf4j
@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		final ExecutionPlan executionPlan = (ExecutionPlan) context.getMergedJobDataMap()
				.get(QuartzConstant.KEY_TASK_PLAN);
		ITask task = SpringContextUtil.getBean(executionPlan.getTaskBean(), ITask.class)
				.orElseThrow(() -> new IllegalStateException("No task bean found"));
		try {
			if (log.isDebugEnabled()) {
				log.debug("Executing task plan id = {},bean = {}({})", executionPlan.getPlanId(),
						executionPlan.getTaskBean(), executionPlan.getDescription());
			}
			task.run(executionPlan.getParam());
		}
		catch (Exception e) {
			log.error(e.getMessage());
			if (Boolean.TRUE.equals(executionPlan.getErrorRetry())) {
				throw new JobExecutionException(e, true);
			}
			throw e;
		}
	}

}
