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

package com.power4j.ji.admin.modules.demo.job;

import com.power4j.ji.common.schedule.quartz.job.MisFirePolicyEnum;
import com.power4j.ji.common.schedule.quartz.job.ExecutionPlan;
import com.power4j.ji.common.schedule.quartz.job.PlanStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
@Slf4j
@Component
public class InitDemoTask {

	/*
	 * @Autowired private Scheduler scheduler;
	 *
	 * @SneakyThrows
	 *
	 * @PostConstruct public void init() { ExecutionPlan plan = new ExecutionPlan();
	 * plan.setPlanId(1L); plan.setCron("3/5 * * * * ? *"); plan.setTaskBean("demoTask");
	 * plan.setParam("123"); plan.setDescription("demo");
	 * plan.setStatus(PlanStatusEnum.NORMAL);
	 * plan.setMisFirePolicy(MisFirePolicyEnum.RESCUE_ALL); plan.setFailRecover(true);
	 *
	 * if (!scheduler.checkExists(QuartzUtil.makeTriggerKey(plan.getPlanId(),
	 * plan.getGroupName()))) { log.info("创建演示任务"); QuartzUtil.createPlan(scheduler,
	 * plan); } }
	 */

}
