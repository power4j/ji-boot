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

package com.power4j.ji.admin.modules.ureport.controller;

import com.power4j.ji.admin.modules.ureport.dto.UrFileDTO;
import com.power4j.ji.admin.modules.ureport.entity.UrData;
import com.power4j.ji.admin.modules.ureport.service.UrFileService;
import com.power4j.ji.admin.modules.ureport.vo.SearchUrFileVO;
import com.power4j.ji.common.core.constant.CrudConstant;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.model.PageData;
import com.power4j.ji.common.core.model.PageRequest;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import com.power4j.ji.common.core.util.HttpServletResponseUtil;
import com.power4j.ji.common.security.audit.ApiLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/2/12
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/ur-files")
@Tag(name = "报表管理")
public class UrFileController {

	private final UrFileService urFileService;

	@ApiLog(module = "报表", tag = "查看报表定义")
	@PreAuthorize("@pms.any('sys:ureports:view')")
	@GetMapping("/page")
	@Operation(summary = "分页",
			parameters = {
					@Parameter(name = CrudConstant.QRY_PAGE_INDEX, in = ParameterIn.QUERY, description = "页码,从1开始"),
					@Parameter(name = CrudConstant.QRY_PAGE_SIZE, in = ParameterIn.QUERY, description = "记录数量"),
					@Parameter(name = "file", in = ParameterIn.QUERY, description = "报表名,支持模糊查询") })
	public ApiResponse<PageData<UrFileDTO>> page(@Parameter(hidden = true) PageRequest page,
			SearchUrFileVO searchUrFileVO) {
		return ApiResponseUtil.ok(urFileService.selectPage(page, searchUrFileVO));
	}

	@ApiLog(module = "报表", tag = "删除报表定义")
	@PreAuthorize("@pms.any('sys:ureports:del')")
	@DeleteMapping("/file/{fileName}")
	@Operation(summary = "删除报表")
	public ApiResponse<UrFileDTO> deleteByFileName(@PathVariable String fileName) {
		return ApiResponseUtil.ok(urFileService.deleteByFileName(fileName).orElse(null));
	}

	@ApiLog(module = "报表", tag = "导出报表定义")
	@PreAuthorize("@pms.any('sys:ureports:export')")
	@GetMapping("/export/file/{fileName}")
	@Operation(summary = "导出报表")
	public void exportByFileName(@PathVariable String fileName, HttpServletResponse response) throws IOException {
		UrData urData = urFileService.findByName(fileName).orElse(null);
		if (urData == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		HttpServletResponseUtil.writeAttachment(response, urData.getData(), urData.getFile(), false);
	}

}
