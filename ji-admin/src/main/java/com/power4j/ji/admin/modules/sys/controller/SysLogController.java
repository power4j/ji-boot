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
import com.power4j.ji.admin.modules.sys.dto.SysLogDTO;
import com.power4j.ji.admin.modules.sys.service.SysLogService;
import com.power4j.ji.admin.modules.sys.vo.SearchSysLogVO;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.data.crud.api.CrudApi;
import com.power4j.ji.common.openapi.annotations.PageRequestParameters;
import com.power4j.ji.common.security.audit.ApiLog;
import com.power4j.kit.common.data.dict.annotation.DictValue;
import com.power4j.kit.common.data.dict.annotation.MapDict;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/6/25
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/log")
@Tag(name = "访问日志")
public class SysLogController implements CrudApi<Long, SysLogDTO> {

	private final SysLogService sysLogService;

	@ApiLog(module = "系统", tag = "查看日志")
	@PreAuthorize("@pms.any('sys:log:view')")
	@Override
	public ApiResponse<SysLogDTO> read(Long id) {
		return ApiResponseUtil.ok(sysLogService.read(id).orElse(null));
	}

	@ApiLog(module = "系统", tag = "查看日志")
	@PreAuthorize("@pms.any('sys:log:view')")
	@Override
	public ApiResponse<List<SysLogDTO>> readList(List<Long> idList) {
		return ApiResponseUtil.ok(sysLogService.readList(idList));
	}

	@ApiLog(module = "系统", tag = "添加日志")
	@PreAuthorize("@pms.any('sys:log:add')")
	@Override
	public ApiResponse<SysLogDTO> post(SysLogDTO obj) {
		return ApiResponseUtil.forbidden("禁止操作");
	}

	@ApiLog(module = "系统", tag = "修改日志")
	@PreAuthorize("@pms.any('sys:log:edit')")
	@Override
	public ApiResponse<SysLogDTO> put(SysLogDTO obj) {
		return ApiResponseUtil.forbidden("禁止操作");
	}

	@ApiLog(module = "系统", tag = "删除日志")
	@PreAuthorize("@pms.any('sys:log:del')")
	@Override
	public ApiResponse<SysLogDTO> delete(Long id) {
		return ApiResponseUtil.forbidden("禁止操作");
	}

	@ApiLog(module = "系统", tag = "查看日志")
	@PreAuthorize("@pms.any('sys:log:view')")
	@GetMapping("/page")
	@PageRequestParameters
	@Operation(summary = "分页", parameters = { @Parameter(name = "module", in = ParameterIn.QUERY, description = "模块"),
			@Parameter(name = "username", in = ParameterIn.QUERY, description = "用户名") })
	public ApiResponse<PageData<SysLogDTO>> page(@Parameter(hidden = true) PageRequest page,
			@Parameter(hidden = true) SearchSysLogVO param) {
		return ApiResponseUtil.ok(sysLogService.selectPage(page, BeanUtil.toBean(param, SysLogDTO.class)));
	}

	@MapDict(code = "http_method_name", name = "HTTP方法")
	public enum MethodEnum {

		/**
		 * GET
		 */
		GET("GET"),
		/**
		 * POST
		 */
		POST("POST"),
		/**
		 * PUT
		 */
		PUT("PUT"),
		/**
		 * DELETE
		 */
		DELETE("DELETE"),
		/**
		 * PATCH
		 */
		PATCH("PATCH");

		private final String value;

		MethodEnum(String value) {
			this.value = value;
		}

		@DictValue
		public String getValue() {
			return value;
		}

	}

}
