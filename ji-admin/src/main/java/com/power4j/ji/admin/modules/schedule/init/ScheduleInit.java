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

package com.power4j.ji.admin.modules.schedule.init;

import com.power4j.ji.admin.modules.schedule.dto.SysJobDTO;
import com.power4j.ji.admin.modules.schedule.service.SysJobService;
import com.power4j.ji.admin.modules.schedule.util.ScheduleUtil;
import com.power4j.ji.common.schedule.quartz.util.QuartzUtil;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/26
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ScheduleInit {

	private final Scheduler scheduler;

	private final SysJobService sysJobService;

	@PostConstruct
	public void init() {
		List<SysJobDTO> jobList = sysJobService.listAll();
		jobList.stream().map(ScheduleUtil::toExecutionPlan)
				.forEach(plan -> QuartzUtil.createMissingPlan(scheduler, plan));
	}

}
