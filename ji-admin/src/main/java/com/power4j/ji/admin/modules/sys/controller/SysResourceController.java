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

package com.power4j.ji.admin.modules.sys.controller;

import com.power4j.ji.admin.modules.sys.constant.SysConstant;
import com.power4j.ji.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.ji.admin.modules.sys.service.SysResourceService;
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
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/resources")
@Tag(name = "资源")
public class SysResourceController implements CrudApi<Long, SysResourceDTO> {

	private final SysResourceService sysResourceService;

	@PreAuthorize("@pms.any('sys:resource:view')")
	@Override
	public ApiResponse<List<SysResourceDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysResourceService.readList(idList));
	}

	@PreAuthorize("@pms.any('sys:resource:view')")
	@Override
	public ApiResponse<SysResourceDTO> read(Long id) {
		return ApiResponseUtil.ok(sysResourceService.read(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:resource:add')")
	@Override
	public ApiResponse<SysResourceDTO> post(SysResourceDTO obj) {
		return ApiResponseUtil.ok(sysResourceService.post(obj));
	}

	@PreAuthorize("@pms.any('sys:resource:edit')")
	@Override
	public ApiResponse<SysResourceDTO> put(SysResourceDTO obj) {
		return ApiResponseUtil.ok(sysResourceService.put(obj));
	}

	@PreAuthorize("@pms.any('sys:resource:del')")
	@Override
	public ApiResponse<SysResourceDTO> delete(Long id) {
		return ApiResponseUtil.ok(sysResourceService.delete(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:resource:view')")
	@GetMapping("/page")
	@PageRequestParameters
	@Operation(summary = "分页")
	public ApiResponse<PageData<SysResourceDTO>> page(@Parameter(hidden = true) PageRequest page) {
		return ApiResponseUtil.ok(sysResourceService.selectPage(page, null));
	}

	@GetMapping("/tree/all")
	@Operation(summary = "所有资源,树形结构")
	public ApiResponse<List<SysResourceDTO>> getFullTree(@Nullable @RequestParam(required = false) Boolean showRoot) {
		if (showRoot != null && showRoot) {
			return ApiResponseUtil
					.ok(Collections.singletonList(sysResourceService.getTree(SysConstant.ROOT_RESOURCE_ID)));
		}
		else {
			return ApiResponseUtil.ok(sysResourceService.getTreeNodes(SysConstant.ROOT_RESOURCE_ID));
		}
	}

	@GetMapping("/tree/children")
	@Operation(summary = "读取下一级")
	public ApiResponse<List<SysResourceDTO>> getChildren(
			@Nullable @Parameter(description = "父级ID") @RequestParam(required = false) Long pid) {
		return ApiResponseUtil.ok(sysResourceService.getChildren(pid == null ? SysConstant.ROOT_RESOURCE_ID : pid));
	}

	@GetMapping("/counter/name")
	@Operation(summary = "统计name", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfName(@RequestParam String value,
			@Parameter(description = "排除的ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysResourceService.countResourceName(value, excludeId));
	}

	@GetMapping("/counter/path")
	@Operation(summary = "统计path", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfPath(@RequestParam String value,
			@Parameter(description = "排除的ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysResourceService.countResourcePath(value, excludeId));
	}

}
