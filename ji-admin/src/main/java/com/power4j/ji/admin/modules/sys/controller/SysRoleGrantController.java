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
import com.power4j.ji.admin.modules.sys.dto.SysRoleGrantDTO;
import com.power4j.ji.admin.modules.sys.service.SysRoleGrantService;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.core.validate.Groups;
import com.power4j.ji.common.data.crud.api.CrudApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/20
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/role/grantee")
@Tag(name = "角色授权")
public class SysRoleGrantController implements CrudApi<Long, SysRoleGrantDTO> {

	private final SysRoleGrantService sysRoleGrantService;

	@PreAuthorize("@pms.any('sys:role:grant','sys:role:revoke')")
	@Override
	public ApiResponse<List<SysRoleGrantDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysRoleGrantService.readList(idList));
	}

	@PreAuthorize("@pms.any('sys:role:grant','sys:role:revoke')")
	@Override
	public ApiResponse<SysRoleGrantDTO> read(Long id) {
		return ApiResponseUtil.ok(sysRoleGrantService.read(id).orElse(null));
	}

	@PreAuthorize("@pms.any('sys:role:grant')")
	@Override
	public ApiResponse<SysRoleGrantDTO> post(@Validated({ Groups.Create.class }) @RequestBody SysRoleGrantDTO obj) {
		return ApiResponseUtil.ok(sysRoleGrantService.post(obj));
	}

	@PreAuthorize("@pms.any('sys:role:grant','sys:role:revoke')")
	@Override
	public ApiResponse<SysRoleGrantDTO> put(SysRoleGrantDTO obj) {
		return ApiResponseUtil.ok(sysRoleGrantService.put(obj));
	}

	@PreAuthorize("@pms.any('sys:role:revoke')")
	@Override
	public ApiResponse<SysRoleGrantDTO> delete(Long id) {
		return ApiResponseUtil.ok(sysRoleGrantService.delete(id).orElse(null));
	}

	@GetMapping("/users/{uid}")
	@Operation(summary = "用户的授权列表")
	public ApiResponse<List<SysRoleGrantDTO>> getList(@Parameter(description = "用户ID") @PathVariable Long uid,
			@Parameter(description = "授权类型") @RequestParam(required = false) String grantType) {
		List<SysRoleGrantDTO> list = sysRoleGrantService.getByUser(uid, grantType).stream()
				.map(o -> BeanUtil.toBean(o, SysRoleGrantDTO.class)).collect(Collectors.toList());
		return ApiResponseUtil.ok(list);
	}

}
