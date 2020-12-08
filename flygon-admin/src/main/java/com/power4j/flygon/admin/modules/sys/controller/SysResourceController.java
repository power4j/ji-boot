package com.power4j.flygon.admin.modules.sys.controller;

import com.power4j.flygon.admin.modules.sys.constant.SysConstant;
import com.power4j.flygon.admin.modules.sys.dto.SysResourceDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysResource;
import com.power4j.flygon.admin.modules.sys.service.SysResourceService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.data.crud.api.CrudApi;
import com.power4j.flygon.common.data.crud.constant.OpFlagEnum;
import com.power4j.flygon.common.data.crud.controller.CrudController;
import com.power4j.flygon.common.security.LoginUser;
import com.power4j.flygon.common.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "资源管理", description = "资源管理")
public class SysResourceController extends CrudController<SysResourceDTO, SysResource> implements CrudApi<SysResourceDTO,ApiResponse<SysResourceDTO>> {
	private final SysResourceService sysResourceService;

	@GetMapping("/page")
	@Operation(summary = "分页")
	public ApiResponse<PageData<SysResourceDTO>> page(PageRequest page) {
		return ApiResponseUtil.ok(sysResourceService.selectPage(page, null));
	}

	@GetMapping("/tree")
	@Operation(summary = "所有资源,树形结构")
	public ApiResponse<List<SysResourceDTO>> allResources(@RequestParam(required = false) Boolean showRoot){
		if(showRoot != null && showRoot){
			return ApiResponseUtil.ok(Arrays.asList(sysResourceService.getTree(SysConstant.ROOT_RESOURCE_ID)));
		}else{
			return ApiResponseUtil.ok(sysResourceService.getNodes(SysConstant.ROOT_RESOURCE_ID));
		}

	}

	@GetMapping("/user/tree")
	@Operation(summary = "当前用户的菜单资源")
	public ApiResponse<List<SysResourceDTO>> getCurrentUserResource() {
		LoginUser user = SecurityUtil.getLoginUser().orElse(null);
		return ApiResponseUtil.ok(sysResourceService.getNodes(SysConstant.ROOT_RESOURCE_ID));
	}

	@Override
	protected void prePostCheck(SysResourceDTO obj) throws BizException {

	}

	@Override
	protected void prePutCheck(SysResourceDTO obj, SysResource entity) throws BizException {
		if (entity == null) {
			throw new BizException(SysErrorCodes.E_CONFLICT,String.format("资源不存在"));
		}
		checkOpFlagNq(entity,OpFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许修改");
	}

	@Override
	protected void preReadCheck(SysResourceDTO obj) throws BizException {

	}

	@Override
	protected void preDeleteCheck(SysResourceDTO obj, SysResource entity) throws BizException {
		checkOpFlagNq(entity,OpFlagEnum.SYS_LOCKED.getValue(),"系统数据不允许删除");
	}
}
