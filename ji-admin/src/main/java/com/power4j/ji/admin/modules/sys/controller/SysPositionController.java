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

import cn.hutool.core.bean.BeanUtil;
import com.power4j.ji.admin.modules.sys.dto.SysPositionDTO;
import com.power4j.ji.admin.modules.sys.service.SysPositionService;
import com.power4j.ji.admin.modules.sys.vo.SearchSysRoleVO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/4/9
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/position")
@Tag(name = "岗位")
public class SysPositionController implements CrudApi<Long, SysPositionDTO> {

	private final SysPositionService sysPositionService;

	@PreAuthorize("@pms.any('sys:position:view')")
	@Override
	public ApiResponse<SysPositionDTO> read(Long id) {
		return ApiResponseUtil.ok(sysPositionService.read(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:position:view')")
	@Override
	public ApiResponse<List<SysPositionDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysPositionService.readList(idList));
	}

	@PreAuthorize("@pms.any('sys:position:add')")
	@Override
	public ApiResponse<SysPositionDTO> post(SysPositionDTO obj) {
		return ApiResponseUtil.ok(sysPositionService.post(obj));
	}

	@PreAuthorize("@pms.any('sys:position:edit')")
	@Override
	public ApiResponse<SysPositionDTO> put(SysPositionDTO obj) {
		return ApiResponseUtil.ok(sysPositionService.put(obj));
	}

	@PreAuthorize("@pms.any('sys:position:del')")
	@Override
	public ApiResponse<SysPositionDTO> delete(Long id) {
		return ApiResponseUtil.ok(sysPositionService.delete(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:position:view')")
	@GetMapping("/page")
	@Operation(summary = "分页",
			parameters = {
					@Parameter(name = CrudConstant.QRY_PAGE_INDEX, in = ParameterIn.QUERY, description = "页码,从1开始"),
					@Parameter(name = CrudConstant.QRY_PAGE_SIZE, in = ParameterIn.QUERY, description = "记录数量"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_PROP, in = ParameterIn.QUERY, description = "排序字段"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_ASC, in = ParameterIn.QUERY, description = "是否升序"),
					@Parameter(name = "code", in = ParameterIn.QUERY, description = "岗位编码"),
					@Parameter(name = "status", in = ParameterIn.QUERY, description = "状态") })
	public ApiResponse<PageData<SysPositionDTO>> page(@Parameter(hidden = true) PageRequest page,
			@Parameter(hidden = true) SearchSysRoleVO param) {
		return ApiResponseUtil.ok(sysPositionService.selectPage(page, BeanUtil.toBean(param, SysPositionDTO.class)));
	}

	@PreAuthorize("@pms.any('sys:position:view')")
	@GetMapping("/all")
	@Operation(summary = "岗位列表")
	public ApiResponse<List<SysPositionDTO>> listAll() {
		return ApiResponseUtil.ok(sysPositionService.searchList(null));
	}

	@GetMapping("/counter/code")
	@Operation(summary = "统计岗位编码", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfCode(@RequestParam String value,
			@Parameter(description = "排除的ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysPositionService.countRoleCode(value, excludeId));
	}

}
