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
import cn.hutool.core.text.CharSequenceUtil;
import com.power4j.ji.admin.modules.sys.constant.RoleGrantEnum;
import com.power4j.ji.admin.modules.sys.dto.SysResourceGrantDTO;
import com.power4j.ji.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.ji.admin.modules.sys.entity.SysResource;
import com.power4j.ji.admin.modules.sys.entity.SysRole;
import com.power4j.ji.admin.modules.sys.entity.SysRoleGrant;
import com.power4j.ji.admin.modules.sys.service.SysResourceGrantService;
import com.power4j.ji.admin.modules.sys.service.SysResourceService;
import com.power4j.ji.admin.modules.sys.service.SysRoleGrantService;
import com.power4j.ji.admin.modules.sys.service.SysRoleService;
import com.power4j.ji.admin.modules.sys.vo.SearchSysRoleVO;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.data.crud.api.CrudApi;
import com.power4j.ji.common.data.crud.constant.LowAttrEnum;
import com.power4j.ji.common.openapi.annotations.PageRequestParameters;
import com.power4j.ji.common.security.audit.ApiLog;
import com.power4j.ji.common.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/roles")
@Tag(name = "角色")
public class SysRoleController implements CrudApi<Long, SysRoleDTO> {

	private final SysRoleService sysRoleService;

	private final SysResourceGrantService sysResourceGrantService;

	private final SysRoleGrantService sysRoleGrantService;

	private final SysResourceService sysResourceService;

	@ApiLog
	@PreAuthorize("@pms.any('sys:role:view')")
	@GetMapping("/page")
	@PageRequestParameters
	@Operation(summary = "分页", parameters = { @Parameter(name = "code", in = ParameterIn.QUERY, description = "角色编码"),
			@Parameter(name = "status", in = ParameterIn.QUERY, description = "状态") })
	public ApiResponse<PageData<SysRoleDTO>> page(@Parameter(hidden = true) PageRequest page,
			@Parameter(hidden = true) SearchSysRoleVO param) {
		return ApiResponseUtil.ok(sysRoleService.selectPage(page, BeanUtil.toBean(param, SysRoleDTO.class)));
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:role:view')")
	@GetMapping("/all")
	@Operation(summary = "角色列表")
	public ApiResponse<List<SysRoleDTO>> listAll() {
		return ApiResponseUtil.ok(sysRoleService.searchList(null));
	}

	@GetMapping("/counter/code")
	@Operation(summary = "统计角色编码", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfCode(@RequestParam String value,
			@Parameter(description = "排除的ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysRoleService.countRoleCode(value, excludeId)).mapIfPresent(Math::toIntExact);
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:role:grant','sys:role:revoke')")
	@PostMapping("/{id}/resource")
	@Operation(summary = "角色资源")
	public ApiResponse<Void> setResource(@PathVariable("id") Long roleId, @RequestBody List<Long> resourceIds) {
		SysRoleDTO sysRole = sysRoleService.read(roleId).orElse(null);
		if (sysRole == null) {
			return ApiResponseUtil.notFound(String.format("角色不存在(id: %d)", roleId));
		}
		sysRoleService.checkSysCtlNot(sysRole, LowAttrEnum.SYS_LOCKED.getValue(), "系统数据不允许修改");
		Long myId = SecurityUtil.getLoginUserId().get();
		// 越权检查: 1 检查能否操作该角色 2 检查使用的资源是否超出本人的资源池
		Optional<SysRoleGrant> granted = sysRoleGrantService.findByUserAndRole(myId, roleId)
				.filter(o -> RoleGrantEnum.ADMIN.getValue().equals(o.getGrantType()));
		if (!granted.isPresent() && !myId.equals(sysRole.getOwner())) {
			return ApiResponseUtil.forbidden("你不是此角色的拥有者，也没有此角色的管理授权");
		}

		List<String> withAdmin = sysRoleService
				.listForUser(SecurityUtil.getLoginUsername().get(), RoleGrantEnum.ADMIN.getValue()).stream()
				.map(SysRole::getCode).collect(Collectors.toList());
		Set<Long> myResources = sysResourceService.listForRoles(withAdmin).stream().map(SysResource::getId)
				.collect(Collectors.toSet());
		Set<Long> exceed = resourceIds.stream().filter(myResources::contains).collect(Collectors.toSet());
		if (!exceed.isEmpty()) {
			log.warn("角色资源越权,资源ID:{}", CharSequenceUtil.join(",", exceed));
			return ApiResponseUtil.forbidden("只能将使用已经获得授权的资源");
		}

		sysResourceGrantService.setResources(roleId, resourceIds);
		return ApiResponseUtil.ok();
	}

	@GetMapping("/{id}/resource")
	@Operation(summary = "角色资源")
	public ApiResponse<List<SysResourceGrantDTO>> getResource(@PathVariable("id") Long id) {
		List<SysResourceGrantDTO> list = sysResourceGrantService.getByRole(id).stream()
				.map(o -> BeanUtil.toBean(o, SysResourceGrantDTO.class)).collect(Collectors.toList());
		return ApiResponseUtil.ok(list);
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:role:view')")
	@Override
	public ApiResponse<SysRoleDTO> read(Long id) {
		return ApiResponseUtil.ok(sysRoleService.read(id).orElse(null));
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:role:view')")
	@Override
	public ApiResponse<List<SysRoleDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysRoleService.readList(idList));
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:role:add')")
	@Override
	public ApiResponse<SysRoleDTO> post(SysRoleDTO obj) {
		return ApiResponseUtil.ok(sysRoleService.post(obj));
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:role:edit')")
	@Override
	public ApiResponse<SysRoleDTO> put(SysRoleDTO obj) {
		return ApiResponseUtil.ok(sysRoleService.put(obj));
	}

	@ApiLog
	@PreAuthorize("@pms.any('sys:role:del')")
	@Override
	public ApiResponse<SysRoleDTO> delete(Long id) {
		return ApiResponseUtil.ok(sysRoleService.delete(id).orElse(null));
	}

}
