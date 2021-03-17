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
import com.power4j.ji.admin.modules.sys.dto.SysOrgNodeDTO;
import com.power4j.ji.admin.modules.sys.service.SysOrgService;
import com.power4j.ji.common.core.constant.CrudConstant;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.data.crud.api.CrudApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/3/17
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/org")
@Tag(name = "组织机构")
public class SysOrgController implements CrudApi<Long, SysOrgNodeDTO> {

	private final SysOrgService sysOrgService;
	
	@PreAuthorize("@pms.any('sys:org:view')")
	@Override
	public ApiResponse<List<SysOrgNodeDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysOrgService.readList(idList));
	}

	@PreAuthorize("@pms.any('sys:org:view')")
	@Override
	public ApiResponse<SysOrgNodeDTO> read(Long id) {
		return ApiResponseUtil.ok(sysOrgService.read(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:org:add')")
	@Override
	public ApiResponse<SysOrgNodeDTO> post(SysOrgNodeDTO obj) {
		return ApiResponseUtil.ok(sysOrgService.post(obj));
	}

	@PreAuthorize("@pms.any('sys:org:edit')")
	@Override
	public ApiResponse<SysOrgNodeDTO> put(SysOrgNodeDTO obj) {
		return ApiResponseUtil.ok(sysOrgService.put(obj));
	}

	@PreAuthorize("@pms.any('sys:org:del')")
	@Override
	public ApiResponse<SysOrgNodeDTO> delete(Long id) {
		return ApiResponseUtil.ok(sysOrgService.delete(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:org:view')")
	@GetMapping("/page")
	@Operation(summary = "分页",
			parameters = {
					@Parameter(name = CrudConstant.QRY_PAGE_INDEX, in = ParameterIn.QUERY, description = "页码,从1开始"),
					@Parameter(name = CrudConstant.QRY_PAGE_SIZE, in = ParameterIn.QUERY, description = "记录数量"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_PROP, in = ParameterIn.QUERY, description = "排序字段"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_ASC, in = ParameterIn.QUERY, description = "是否升序") })
	public ApiResponse<PageData<SysOrgNodeDTO>> page(@Parameter(hidden = true) PageRequest page) {
		return ApiResponseUtil.ok(sysOrgService.selectPage(page, null));
	}

	@GetMapping("/tree/all")
	@Operation(summary = "所有资源,树形结构")
	public ApiResponse<List<SysOrgNodeDTO>> allResources(@RequestParam(required = false) Boolean showRoot) {
		if (showRoot != null && showRoot) {
			return ApiResponseUtil.ok(Arrays.asList(sysOrgService.getTree(SysConstant.ROOT_RESOURCE_ID)));
		}
		else {
			return ApiResponseUtil.ok(sysOrgService.getTreeNodes(SysConstant.ROOT_RESOURCE_ID));
		}
	}

	@GetMapping("/tree/children")
	@Operation(summary = "读取子级")
	public ApiResponse<List<SysOrgNodeDTO>> getChildren(
			@Parameter(description = "父级ID") @RequestParam(required = false) Long pid) {
		return ApiResponseUtil.ok(sysOrgService.getChildren(pid == null ? SysConstant.ROOT_RESOURCE_ID : pid));
	}

	@GetMapping("/counter/code")
	@Operation(summary = "统计code", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfCode(@RequestParam String value,
											@Parameter(description = "排除的ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysOrgService.countOrgCode(value, excludeId));
	}

}
