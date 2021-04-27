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

package com.power4j.ji.admin.modules.schedule.controller;

import com.power4j.ji.admin.modules.schedule.dto.ScheduleJobDTO;
import com.power4j.ji.admin.modules.schedule.service.ScheduleJobService;
import com.power4j.ji.admin.modules.schedule.vo.SearchScheduleJobVO;
import com.power4j.ji.common.core.constant.CrudConstant;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.data.crud.api.CrudApi;
import com.power4j.ji.common.openapi.annotations.PageRequestParameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/20
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/jobs")
@Tag(name = "任务调度")
public class ScheduleJobController implements CrudApi<Long, ScheduleJobDTO> {

	private final ScheduleJobService scheduleJobService;

	@PreAuthorize("@pms.any('sys:job:view')")
	@GetMapping("/page")
	@PageRequestParameters
	@Operation(summary = "分页",
			parameters = { @Parameter(name = "groupName", in = ParameterIn.QUERY, description = "作业组,支持模糊查询"),
					@Parameter(name = "status", in = ParameterIn.QUERY, description = "状态") })
	public ApiResponse<PageData<ScheduleJobDTO>> page(@Parameter(hidden = true) PageRequest page,
			@Parameter(hidden = true) SearchScheduleJobVO param) {
		return ApiResponseUtil.ok(scheduleJobService.selectPage(page, param));
	}

	@PreAuthorize("@pms.any('sys:job:view')")
	@Override
	public ApiResponse<ScheduleJobDTO> read(Long id) {
		return ApiResponseUtil.ok(scheduleJobService.read(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:job:view')")
	@Override
	public ApiResponse<List<ScheduleJobDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(scheduleJobService.readList(idList));
	}

	@PreAuthorize("@pms.any('sys:job:add')")
	@Override
	public ApiResponse<ScheduleJobDTO> post(ScheduleJobDTO obj) {
		return ApiResponseUtil.ok(scheduleJobService.post(obj));
	}

	@PreAuthorize("@pms.any('sys:job:edit')")
	@Override
	public ApiResponse<ScheduleJobDTO> put(ScheduleJobDTO obj) {
		return ApiResponseUtil.ok(scheduleJobService.put(obj));
	}

	@PreAuthorize("@pms.any('sys:job:del')")
	@Override
	public ApiResponse<ScheduleJobDTO> delete(Long id) {
		return ApiResponseUtil.ok(scheduleJobService.delete(id).orElse(null));
	}

}
