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

package com.power4j.ji.admin.modules.schedule.listener;

import com.power4j.ji.admin.modules.schedule.entity.SysJobLog;
import com.power4j.ji.admin.modules.schedule.service.SysJobLogService;
import com.power4j.ji.admin.modules.schedule.util.ScheduleUtil;
import com.power4j.ji.common.schedule.quartz.event.TaskEndEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/25
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskEndEventListener implements ApplicationListener<PayloadApplicationEvent<TaskEndEvent>> {

	private final SysJobLogService sysJobLogService;

	@Override
	public void onApplicationEvent(PayloadApplicationEvent<TaskEndEvent> event) {
		try {
			SysJobLog jobLog = ScheduleUtil.toSysJobLog(event.getPayload());
			sysJobLogService.insertJobLog(jobLog);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
