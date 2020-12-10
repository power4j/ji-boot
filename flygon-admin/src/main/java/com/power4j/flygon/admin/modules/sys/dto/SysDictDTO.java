package com.power4j.flygon.admin.modules.sys.dto;

import com.power4j.flygon.common.core.validate.Groups;
import com.power4j.flygon.common.data.crud.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 代码，唯一
	 */
	@Schema(description = "代码，唯一", example = "web-color")
	@NotNull(groups = { Groups.Default.class })
	@Size(min = 2, max = 40, groups = { Groups.Default.class })
	private String code;

	/**
	 * 名称
	 */
	@Schema(description = "名称", example = "color")
	@NotNull(groups = { Groups.Default.class })
	@Size(min = 1, max = 40, groups = { Groups.Default.class })
	private String name;

	/**
	 * 备注
	 */
	@Schema(description = "备注", example = "colors")
	@Size(max = 20, groups = { Groups.Default.class })
	private String remarks;

}
