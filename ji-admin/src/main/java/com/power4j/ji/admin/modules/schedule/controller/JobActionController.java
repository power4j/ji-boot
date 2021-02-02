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

import com.power4j.ji.admin.modules.schedule.service.SysJobService;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/20
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/job")
@Tag(name = "任务调度")
public class JobActionController {

	private final SysJobService sysJobService;

	@Operation(summary = "立即调度")
	@PreAuthorize("@pms.any('sys:job:action')")
	@PostMapping("/{jobId}/action/trigger")
	public ApiResponse<String> runNow(@PathVariable("jobId") Long jobId) {
		return ApiResponseUtil.ok(sysJobService.scheduleNow(jobId, true));
	}

	@Operation(summary = "停止调度")
	@PreAuthorize("@pms.any('sys:job:action')")
	@PostMapping("/{jobId}/action/pause")
	public ApiResponse<Void> pause(@PathVariable("jobId") Long jobId) {
		sysJobService.pauseJob(jobId);
		return ApiResponseUtil.ok();
	}

	@Operation(summary = "恢复调度")
	@PreAuthorize("@pms.any('sys:job:action')")
	@PostMapping("/{jobId}/action/resume")
	public ApiResponse<LocalDateTime> resume(@PathVariable("jobId") Long jobId) {
		return ApiResponseUtil.ok(sysJobService.resumeJob(jobId).orElse(null));
	}

}
