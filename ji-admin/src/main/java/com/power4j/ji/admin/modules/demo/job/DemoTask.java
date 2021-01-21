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

import com.power4j.ji.common.schedule.quartz.job.ITask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
@Slf4j
@Component("demoTask")
public class DemoTask implements ITask {
	private static final AtomicInteger counter = new AtomicInteger();
	private static final int THROW_ERR = 5;

	@Override
	public void run(String param) {
		final int val = counter.incrementAndGet();
		log.info("demo task with param {},counter = #{} ",param, val);
		if(0 == (val % THROW_ERR)){
			throw new RuntimeException("模拟任务异常");
		}
	}

}
