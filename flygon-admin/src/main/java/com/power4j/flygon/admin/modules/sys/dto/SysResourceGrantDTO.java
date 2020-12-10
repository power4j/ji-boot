package com.power4j.flygon.admin.modules.sys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
@Data
@Schema(title = "资源授权")
public class SysResourceGrantDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID", accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long roleId;

	/**
	 * 资源ID
	 */
	@Schema(description = "资源ID")
	private Long resourceId;

}
