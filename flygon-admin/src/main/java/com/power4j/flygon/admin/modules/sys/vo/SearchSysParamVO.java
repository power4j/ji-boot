package com.power4j.flygon.admin.modules.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/9
 * @since 1.0
 */
@Data
@Schema(title = "参数查询")
public class SearchSysParamVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 参数键
	 */
	@Schema(description = "参数名")
	private String paramKey;

	/**
	 * 状态 0 有效 1 停用
	 */
	@Schema(description = "状态")
	private String status;

}
