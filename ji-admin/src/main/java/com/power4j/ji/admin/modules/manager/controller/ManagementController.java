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

package com.power4j.ji.admin.modules.manager.controller;

import com.power4j.ji.admin.modules.manager.service.ManagementService;
import com.power4j.ji.admin.modules.schedule.dto.SysJobDTO;
import com.power4j.ji.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.ji.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.ji.admin.modules.sys.entity.SysRole;
import com.power4j.ji.admin.modules.sys.service.SysRoleService;
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

import java.util.Collections;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/1/20
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/am")
@Tag(name = "应用管理接口")
public class ManagementController {

	private final SysRoleService sysRoleService;

	private final ManagementService managementService;

	@PostMapping("/role/{roleCode}/resources/permit-all")
	@Operation(summary = "授予角色全部资源")
	public ApiResponse<List<SysResourceDTO>> permitAllResource(@PathVariable("roleCode") String roleCode) {
		SysRoleDTO role = sysRoleService.readByCode(roleCode).orElse(null);
		if (role == null) {
			return ApiResponseUtil.ok("角色不存在", Collections.emptyList());
		}
		return ApiResponseUtil.ok(managementService.permitAnyLeakedResource(role));
	}

}
