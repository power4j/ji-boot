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

package com.power4j.ji.admin.modules.schedule.service;

import com.power4j.ji.admin.modules.schedule.dto.ScheduleJobDTO;
import com.power4j.ji.admin.modules.schedule.entity.ScheduleJob;
import com.power4j.ji.admin.modules.schedule.vo.SearchScheduleJobVO;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.data.crud.service.CrudService;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/20
 * @since 1.0
 */
public interface ScheduleJobService extends CrudService<ScheduleJobDTO, ScheduleJob> {

	/**
	 * 分页查询
	 * @param pageRequest
	 * @param param
	 * @return
	 */
	PageData<ScheduleJobDTO> selectPage(PageRequest pageRequest, @Nullable SearchScheduleJobVO param);

	/**
	 * 立即调度
	 * @param jobId
	 * @param force
	 * @param fireBy
	 * @return 返回调度ID
	 */
	String scheduleNow(Long jobId, boolean force, @Nullable String fireBy);

	/**
	 * 停止调度
	 * @param jobId
	 */
	void pauseJob(Long jobId);

	/**
	 * 恢复调度
	 * @param jobId
	 * @return 返回下一次计划执行时间
	 */
	Optional<LocalDateTime> resumeJob(Long jobId);

	/**
	 * 查询全部数据
	 * @return
	 */
	List<ScheduleJobDTO> listAll();

}
