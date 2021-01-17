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
import com.power4j.ji.admin.modules.sys.dto.SysResourceGrantDTO;
import com.power4j.ji.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.ji.admin.modules.sys.service.SysResourceGrantService;
import com.power4j.ji.admin.modules.sys.service.SysRoleService;
import com.power4j.ji.admin.modules.sys.vo.SearchSysRoleVO;
import com.power4j.ji.common.core.constant.CrudConstant;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.data.crud.api.CrudApi;
import com.power4j.ji.common.data.crud.constant.SysCtlFlagEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/roles")
@Tag(name = "角色")
public class SysRoleController implements CrudApi<Long, SysRoleDTO> {

	private final SysRoleService sysRoleService;

	private final SysResourceGrantService sysResourceGrantService;

	@PreAuthorize("@pms.any('sys:role:view')")
	@GetMapping("/page")
	@Operation(summary = "分页",
			parameters = {
					@Parameter(name = CrudConstant.QRY_PAGE_INDEX, in = ParameterIn.QUERY, description = "页码,从1开始"),
					@Parameter(name = CrudConstant.QRY_PAGE_SIZE, in = ParameterIn.QUERY, description = "记录数量"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_PROP, in = ParameterIn.QUERY, description = "排序字段"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_ASC, in = ParameterIn.QUERY, description = "是否升序"),
					@Parameter(name = "code", in = ParameterIn.QUERY, description = "角色编码"),
					@Parameter(name = "status", in = ParameterIn.QUERY, description = "状态") })
	public ApiResponse<PageData<SysRoleDTO>> page(@Parameter(hidden = true) PageRequest page,
			@Parameter(hidden = true) SearchSysRoleVO param) {
		return ApiResponseUtil
				.ok(sysRoleService.selectPage(page, param == null ? null : BeanUtil.toBean(param, SysRoleDTO.class)));
	}

	@PreAuthorize("@pms.any('sys:role:view')")
	@GetMapping("/all")
	@Operation(summary = "角色列表")
	public ApiResponse<List<SysRoleDTO>> listAll() {
		return ApiResponseUtil.ok(sysRoleService.searchList(null));
	}

	@GetMapping("/counter/code/{code}")
	@Operation(summary = "统计角色编码", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfCode(@PathVariable("code") @NotEmpty String code,
			@Parameter(description = "排除的ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysRoleService.countRoleCode(code, excludeId));
	}

	@PreAuthorize("@pms.any('sys:role:grant','sys:role:revoke')")
	@PostMapping("/{id}/resource")
	@Operation(summary = "角色资源")
	public ApiResponse<Void> setResource(@PathVariable("id") Long id, @RequestBody List<Long> resourceIds) {
		SysRoleDTO sysRole = sysRoleService.read(id).orElse(null);
		if (sysRole == null) {
			return ApiResponseUtil.notFound(String.format("角色不存在(id %d)", id));
		}
		sysRoleService.checkSysCtlNot(sysRole, SysCtlFlagEnum.SYS_LOCKED.getValue(), "系统数据不允许修改");
		// TODO 越权检查
		sysResourceGrantService.setResources(id, resourceIds);
		return ApiResponseUtil.ok();
	}

	@GetMapping("/{id}/resource")
	@Operation(summary = "角色资源")
	public ApiResponse<List<SysResourceGrantDTO>> getResource(@PathVariable("id") Long id) {
		List<SysResourceGrantDTO> list = sysResourceGrantService.getByRole(id).stream()
				.map(o -> BeanUtil.toBean(o, SysResourceGrantDTO.class)).collect(Collectors.toList());
		return ApiResponseUtil.ok(list);
	}

	@PreAuthorize("@pms.any('sys:role:view')")
	@Override
	public ApiResponse<SysRoleDTO> read(Long id) {
		return ApiResponseUtil.ok(sysRoleService.read(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:role:view')")
	@Override
	public ApiResponse<List<SysRoleDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysRoleService.readList(idList));
	}

	@PreAuthorize("@pms.any('sys:role:add')")
	@Override
	public ApiResponse<SysRoleDTO> post(SysRoleDTO obj) {
		return ApiResponseUtil.ok(sysRoleService.post(obj));
	}

	@PreAuthorize("@pms.any('sys:role:edit')")
	@Override
	public ApiResponse<SysRoleDTO> put(SysRoleDTO obj) {
		return ApiResponseUtil.ok(sysRoleService.put(obj));
	}

	@PreAuthorize("@pms.any('sys:role:del')")
	@Override
	public ApiResponse<SysRoleDTO> delete(@PathVariable("id") Long id) {
		return ApiResponseUtil.ok(sysRoleService.delete(id).orElse(null));
	}

}
