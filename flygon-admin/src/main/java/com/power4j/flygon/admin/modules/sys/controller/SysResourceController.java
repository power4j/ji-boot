package com.power4j.flygon.admin.modules.sys.controller;

import com.power4j.flygon.admin.modules.sys.constant.SysConstant;
import com.power4j.flygon.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysResource;
import com.power4j.flygon.admin.modules.sys.service.SysResourceService;
import com.power4j.flygon.common.core.constant.CrudConstant;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.data.crud.api.CrudApi;
import com.power4j.flygon.common.data.crud.controller.CrudController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/resources")
@Tag(name = "资源")
public class SysResourceController extends CrudController<SysResourceDTO, SysResource>
		implements CrudApi<SysResourceDTO> {

	private final SysResourceService sysResourceService;

	@GetMapping("/page")
	@Operation(summary = "分页",
			parameters = {
					@Parameter(name = CrudConstant.QRY_PAGE_INDEX, in = ParameterIn.QUERY, description = "页码,从1开始"),
					@Parameter(name = CrudConstant.QRY_PAGE_SIZE, in = ParameterIn.QUERY, description = "记录数量"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_PROP, in = ParameterIn.QUERY, description = "排序字段"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_ASC, in = ParameterIn.QUERY, description = "是否升序") })
	public ApiResponse<PageData<SysResourceDTO>> page(@Parameter(hidden = true) PageRequest page) {
		return ApiResponseUtil.ok(sysResourceService.selectPage(page, null));
	}

	@GetMapping("/tree/all")
	@Operation(summary = "所有资源,树形结构")
	public ApiResponse<List<SysResourceDTO>> allResources(@RequestParam(required = false) Boolean showRoot) {
		if (showRoot != null && showRoot) {
			return ApiResponseUtil.ok(Arrays.asList(sysResourceService.getTree(SysConstant.ROOT_RESOURCE_ID)));
		}
		else {
			return ApiResponseUtil.ok(sysResourceService.getTreeNodes(SysConstant.ROOT_RESOURCE_ID));
		}
	}

	@GetMapping("/tree/children")
	@Operation(summary = "读取子级")
	public ApiResponse<List<SysResourceDTO>> getChildren(@Parameter(description = "父级ID") @RequestParam(required = false) Long pid) {
		return ApiResponseUtil.ok(sysResourceService.getChildren(pid == null ? SysConstant.ROOT_RESOURCE_ID : pid));
	}
}
