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
import com.power4j.ji.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.ji.admin.modules.sys.dto.SysUserDTO;
import com.power4j.ji.admin.modules.sys.service.SysRoleService;
import com.power4j.ji.admin.modules.sys.service.SysUserService;
import com.power4j.ji.admin.modules.sys.vo.SearchSysUserVO;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.data.crud.api.CrudApi;
import com.power4j.ji.common.openapi.annotations.PageRequestParameters;
import com.power4j.ji.common.security.audit.ApiLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/users")
@Tag(name = "用户")
public class SysUserController implements CrudApi<Long, SysUserDTO> {

	private final SysUserService sysUserService;

	private final SysRoleService sysRoleService;

	@ApiLog
	@PreAuthorize("@pms.any('sys:user:view')")
	@Override
	public ApiResponse<List<SysUserDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysUserService.readList(idList));
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:user:view')")
	@Override
	public ApiResponse<SysUserDTO> read(Long id) {
		return ApiResponseUtil.ok(sysUserService.read(id).orElse(null));
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:user:add')")
	@Override
	public ApiResponse<SysUserDTO> post(SysUserDTO obj) {
		return ApiResponseUtil.ok(sysUserService.post(obj));
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:user:edit')")
	@Override
	public ApiResponse<SysUserDTO> put(SysUserDTO obj) {
		return ApiResponseUtil.ok(sysUserService.put(obj));
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:user:del')")
	@Override
	public ApiResponse<SysUserDTO> delete(Long id) {
		return ApiResponseUtil.ok(sysUserService.delete(id).orElse(null));
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:user:view')")
	@GetMapping("/page")
	@PageRequestParameters
	@Operation(summary = "分页",
			parameters = { @Parameter(name = "username", in = ParameterIn.QUERY, description = "用户名,支持模糊查询"),
					@Parameter(name = "status", in = ParameterIn.QUERY, description = "状态"),
					@Parameter(name = "createIn", in = ParameterIn.QUERY, description = "创建日期范围",
							example = "2020-01-01,2020-12-31") })
	public ApiResponse<PageData<SysUserDTO>> page(@Parameter(hidden = true) PageRequest page,
			@Parameter(hidden = true) SearchSysUserVO param) {
		return ApiResponseUtil.ok(sysUserService.selectPage(page, param));
	}

	@GetMapping("/counter/username")
	@Operation(summary = "统计用户名", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfUsername(@RequestParam String value,
			@Parameter(description = "排除的用户ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysUserService.countUsername(value, excludeId));
	}

	@GetMapping("/{username}/roles")
	@Operation(summary = "用户的角色列表")
	public ApiResponse<List<SysRoleDTO>> getRoleList(@Parameter(description = "用户名") @PathVariable String username,
			@Parameter(description = "授权类型") @RequestParam(required = false) String grantType) {
		List<SysRoleDTO> data = sysRoleService.listForUser(username, grantType).stream()
				.map(o -> BeanUtil.toBean(o, SysRoleDTO.class)).collect(Collectors.toList());
		return ApiResponseUtil.ok(data);
	}

}
