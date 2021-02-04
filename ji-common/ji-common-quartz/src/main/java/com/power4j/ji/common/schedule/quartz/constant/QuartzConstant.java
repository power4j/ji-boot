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

package com.power4j.ji.common.schedule.quartz.constant;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/19
 * @since 1.0
 */
public interface QuartzConstant {

	String KEY_TASK_PLAN = "QUARTZ_TASK";

	String KEY_TASK_EXEC_ID = "EXECUTION_ID";

	String KEY_EXEC_FORCE = "EXEC_FORCE";

	String KEY_EXEC_FIRE_BY = "EXEC_FIRE_BY";

	String DEFAULT_JOB_GROUP = "DEFAULT";

	String JOB_NAME_PREFIX = "job_";

	String TRIGGER_NAME_PREFIX = "trigger_";

}
