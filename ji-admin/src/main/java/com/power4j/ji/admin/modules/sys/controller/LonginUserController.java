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
import com.power4j.ji.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.ji.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.ji.admin.modules.sys.service.SysResourceService;
import com.power4j.ji.admin.modules.sys.service.SysRoleService;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/23
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/user")
@Tag(name = "用户")
public class LonginUserController {

	private final SysRoleService sysRoleService;

	private final SysResourceService sysResourceService;

	@GetMapping("/menu/tree")
	@Operation(summary = "当前用户的菜单树")
	public ApiResponse<List<SysResourceDTO>> getCurrentUserResource() {
		Set<String> roleList = SecurityUtil.getLoginUserRoles();
		if (roleList.isEmpty()) {
			return ApiResponseUtil.ok(Collections.emptyList());
		}
		return ApiResponseUtil.ok(sysResourceService.getTreeForRoles(roleList));
	}

	@GetMapping("/roles")
	@Operation(summary = "当前用户的角色列表")
	public ApiResponse<List<SysRoleDTO>> getUserRoles(
			@Parameter(description = "授权类型") @RequestParam(required = false) String grantType) {
		List<SysRoleDTO> ret = sysRoleService.listForUser(SecurityUtil.getLoginUsername().get(), grantType).stream()
				.map(o -> BeanUtil.toBean(o, SysRoleDTO.class)).collect(Collectors.toList());
		return ApiResponseUtil.ok(ret);
	}

}
