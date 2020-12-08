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

package com.power4j.flygon.admin.modules.sys.controller;

import com.power4j.flygon.admin.modules.sys.dto.SysUserDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysUser;
import com.power4j.flygon.admin.modules.sys.service.SysUserService;
import com.power4j.flygon.admin.modules.sys.vo.SearchSysUserVO;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.data.crud.api.CrudApi;
import com.power4j.flygon.common.data.crud.constant.OpFlagEnum;
import com.power4j.flygon.common.data.crud.controller.CrudController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

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
@Tag(name = "用户管理", description = "用户管理")
public class SysUserController  extends CrudController<SysUserDTO, SysUser> implements CrudApi<SysUserDTO,ApiResponse<SysUserDTO>> {

	private final SysUserService sysUserService;

	@GetMapping("/page")
	@Operation(summary = "分页")
	public ApiResponse<PageData<SysUserDTO>> page(PageRequest page, SearchSysUserVO param) {
		return ApiResponseUtil.ok(sysUserService.selectPage(page, param));
	}

	@GetMapping("/count/username/{username}")
	@Operation(summary = "查找用户名", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> findUsername(@PathVariable("username") @NotEmpty String username,
			@Parameter(description = "排除的用户ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysUserService.countUsername(username, excludeId));
	}

	@Override
	protected void prePostCheck(SysUserDTO obj) throws BizException {
		if (sysUserService.countUsername(obj.getUsername(), null) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("用户名不能使用: %s", obj.getUsername()));
		}
	}

	@Override
	protected void prePutCheck(SysUserDTO obj,SysUser entity) throws BizException {
		if (entity == null) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("用户不存在"));
		}
		checkOpFlagNq(entity,OpFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许修改");
		if (sysUserService.countUsername(obj.getUsername(), obj.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("用户名不能使用: %s", obj.getUsername()));
		}
	}

	@Override
	protected void preReadCheck(SysUserDTO obj) throws BizException {

	}

	@Override
	protected void preDeleteCheck(SysUserDTO obj,SysUser entity) throws BizException {
		checkOpFlagNq(entity,OpFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许删除");
	}
}
