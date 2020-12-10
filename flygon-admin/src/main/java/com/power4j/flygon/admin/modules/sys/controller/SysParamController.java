package com.power4j.flygon.admin.modules.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import com.power4j.flygon.admin.modules.sys.dto.SysParamDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysParam;
import com.power4j.flygon.admin.modules.sys.service.SysParamService;
import com.power4j.flygon.admin.modules.sys.vo.SearchSysParamVO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/9
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/param")
@Tag(name = "参数")
public class SysParamController extends CrudController<SysParamDTO, SysParam>
		implements CrudApi<SysParamDTO> {

	private final SysParamService sysParamService;

	@GetMapping("/page")
	@Operation(summary = "分页",
			parameters = {
					@Parameter(name = CrudConstant.QRY_PAGE_INDEX, in = ParameterIn.QUERY, description = "页码,从1开始"),
					@Parameter(name = CrudConstant.QRY_PAGE_SIZE, in = ParameterIn.QUERY, description = "记录数量"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_PROP, in = ParameterIn.QUERY, description = "排序字段"),
					@Parameter(name = CrudConstant.QRY_PAGE_ORDER_ASC, in = ParameterIn.QUERY, description = "是否升序"),
					@Parameter(name = "paramKey", in = ParameterIn.QUERY, description = "参数名"),
					@Parameter(name = "status", in = ParameterIn.QUERY, description = "状态") })
	public ApiResponse<PageData<SysParamDTO>> page(@Parameter(hidden = true) PageRequest page,
			@Parameter(hidden = true) SearchSysParamVO param) {
		return ApiResponseUtil
				.ok(sysParamService.selectPage(page, param == null ? null : BeanUtil.toBean(param, SysParamDTO.class)));
	}

	@GetMapping("/key/{key}")
	@Operation(summary = "根据参数名查找")
	public ApiResponse<SysParamDTO> getByKey(@PathVariable("key") String key) {
		return ApiResponseUtil.ok(sysParamService.findByKey(key).orElse(null));
	}

	@GetMapping("/counter/key/{key}")
	@Operation(summary = "统计参数名", description = "返回统计值,可用于唯一性检查")
	public ApiResponse<Integer> countOfKey(@PathVariable("key") @NotEmpty String key,
			@Parameter(description = "排除的ID") @RequestParam(required = false) Long excludeId) {
		return ApiResponseUtil.ok(sysParamService.countParamKey(key, excludeId));
	}

}
