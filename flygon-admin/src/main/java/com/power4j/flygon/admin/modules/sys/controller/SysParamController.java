package com.power4j.flygon.admin.modules.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import com.power4j.flygon.admin.modules.sys.dto.SysParamDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysParam;
import com.power4j.flygon.admin.modules.sys.service.SysParamService;
import com.power4j.flygon.admin.modules.sys.vo.SearchSysParamVO;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/9
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/param")
@Tag(name = "参数管理", description = "参数管理")
public class SysParamController extends CrudController<SysParamDTO, SysParam> implements CrudApi<SysParamDTO, ApiResponse<SysParamDTO>> {
	private final SysParamService sysParamService;

	@GetMapping("/page")
	@Operation(summary = "分页")
	public ApiResponse<PageData<SysParamDTO>> page(PageRequest page, SearchSysParamVO param) {
		return ApiResponseUtil.ok(sysParamService.selectPage(page, BeanUtil.toBean(param,SysParamDTO.class)));
	}

	@GetMapping("/key/{key}")
	@Operation(summary = "根据参数名查找")
	public ApiResponse<SysParamDTO> getByKey(@PathVariable("key") String key ){
		return ApiResponseUtil.ok(sysParamService.findByKey(key).orElse(null));
	}

	@Override
	protected void prePostCheck(SysParamDTO obj) throws BizException {
		if (sysParamService.countParamKey(obj.getParamKey(), null) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("参数名不能使用: %s", obj.getParamKey()));
		}
	}

	@Override
	protected void prePutCheck(SysParamDTO obj, SysParam entity) throws BizException {
		if (entity == null) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("参数不存在"));
		}
		checkOpFlagNq(entity, OpFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许修改");
		if (sysParamService.countParamKey(obj.getParamKey(), obj.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("参数名不能使用: %s", obj.getParamKey()));
		}
	}

	@Override
	protected void preReadCheck(SysParamDTO obj) throws BizException {

	}

	@Override
	protected void preDeleteCheck(SysParamDTO obj, SysParam entity) throws BizException {
		checkOpFlagNq(entity,OpFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许删除");
	}
}
