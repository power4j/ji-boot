package com.power4j.flygon.admin.modules.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
@Data
@Schema(title = "角色查询")
public class SearchSysRoleVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "角色编码", example = "admin")
	private String code;

	@Schema(description = "状态 0 有效 1 停用", example = "0")
	private String status;

}
