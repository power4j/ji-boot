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

import cn.hutool.core.bean.BeanUtil;
import com.power4j.flygon.admin.modules.sys.dto.SysUserDTO;
import com.power4j.flygon.admin.modules.sys.service.SysUserService;
import com.power4j.flygon.admin.modules.sys.vo.SearchSysUserVO;
import com.power4j.flygon.admin.modules.sys.vo.SysUserVO;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.core.validate.Groups;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 用户管理
 * <p>
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020-11-17
 * @since 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/sys/users")
@Tag(name = "sys-user", description = "用户管理")
public class SysUserController {

	private final SysUserService sysUserService;

	@GetMapping("/page")
	@Operation(summary = "分页")
	public ApiResponse<PageData<SysUserVO>> page(PageRequest page, SearchSysUserVO param) {
		return ApiResponseUtil.ok(sysUserService.selectPage(page, param).map(o -> BeanUtil.toBean(o, SysUserVO.class)));
	}

	@GetMapping("/{id}")
	@Operation(summary = "查看")
	public ApiResponse<SysUserVO> getById(@PathVariable("id") Long id) {
		SysUserVO vo = sysUserService.read(id).map(o -> BeanUtil.toBean(o, SysUserVO.class)).orElse(null);
		return ApiResponseUtil.nullAsNotFound(vo, "用户不存在");
	}

	@PostMapping
	@Operation(summary = "添加用户")
	public ApiResponse<SysUserVO> add(
			@Validated(value = { Groups.Create.class, Groups.Default.class }) @RequestBody SysUserVO vo) {
		if (sysUserService.countUsername(vo.getUsername(), null) > 0) {
			return ApiResponseUtil.conflict("用户名已经存在");
		}
		SysUserDTO dto = BeanUtil.toBean(vo, SysUserDTO.class);
		return ApiResponseUtil.ok(BeanUtil.toBean(sysUserService.create(dto), SysUserVO.class));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "删除")
	public ApiResponse<Boolean> delete(@PathVariable("id") Long id) {
		return ApiResponseUtil.ok(sysUserService.removeById(id));
	}

	@DeleteMapping
	@Operation(summary = "批量删除")
	public ApiResponse<Boolean> deleteBatch(@RequestBody List<Long> ids) {
		return ApiResponseUtil.ok(sysUserService.removeByIds(ids));
	}

	@PutMapping
	@Operation(summary = "修改")
	public ApiResponse<Boolean> update(@RequestBody SysUserVO vo) {
		if (sysUserService.read(vo.getId()) == null) {
			return ApiResponseUtil.notFound("用户不存在");
		}
		if (sysUserService.countUsername(vo.getUsername(), vo.getId()) > 0) {
			return ApiResponseUtil.conflict("用户名已经使用");
		}
		return ApiResponseUtil.ok(sysUserService.update(BeanUtil.toBean(vo, SysUserDTO.class)));
	}

	@GetMapping("/validate/username/{username}")
	@Operation(summary = "用户名使用次数", description = "可用于用户名唯一性检查")
	public ApiResponse<Integer> getById(@PathVariable("username") @NotEmpty String username,
			@Schema(description = "排除的用户ID") @RequestParam(required = false) Long expectedId) {
		return ApiResponseUtil.ok(sysUserService.countUsername(username, expectedId));
	}

}
