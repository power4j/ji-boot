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

import com.power4j.ji.admin.modules.schedule.dto.ScheduleLogDTO;
import com.power4j.ji.admin.modules.schedule.service.ScheduleLogService;
import com.power4j.ji.admin.modules.schedule.vo.SearchScheduleLogVO;
import com.power4j.ji.common.core.constant.CrudConstant;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/25
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/job-logs")
@Tag(name = "任务执行日志")
public class ScheduleLogController {

	private final ScheduleLogService scheduleLogService;

	@PreAuthorize("@pms.any('sys:job-log:view')")
	@GetMapping("/page")
	@Operation(summary = "分页", parameters = {
			@Parameter(name = CrudConstant.QRY_PAGE_INDEX, in = ParameterIn.QUERY, description = "页码,从1开始"),
			@Parameter(name = CrudConstant.QRY_PAGE_SIZE, in = ParameterIn.QUERY, description = "记录数量"),
			@Parameter(name = CrudConstant.QRY_PAGE_ORDER_PROP, in = ParameterIn.QUERY, description = "排序字段"),
			@Parameter(name = CrudConstant.QRY_PAGE_ORDER_ASC, in = ParameterIn.QUERY, description = "是否升序"),
			@Parameter(name = "taskBean", in = ParameterIn.QUERY, description = "bean名称"),
			@Parameter(name = "success", in = ParameterIn.QUERY, description = "是否成功"),
			@Parameter(name = "ex", in = ParameterIn.QUERY, description = "异常,支持模糊查询"), @Parameter(name = "startTimeIn",
					in = ParameterIn.QUERY, description = "执行日期范围", example = "2020-01-01,2020-12-31") })
	public ApiResponse<PageData<ScheduleLogDTO>> page(@Parameter(hidden = true) PageRequest page,
			@Parameter(hidden = true) SearchScheduleLogVO param) {
		return ApiResponseUtil.ok(scheduleLogService.selectPage(page, param));
	}

	@PreAuthorize("@pms.any('sys:job-log:del')")
	@DeleteMapping
	@Operation(summary = "批量删除")
	public ApiResponse<?> deleteBatch(@RequestBody Long[] ids) {
		scheduleLogService.deleteBatch(Arrays.asList(ids));
		return ApiResponseUtil.ok();
	}

}
