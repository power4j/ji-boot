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
public class SysDictItemDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典ID
	 */
	@Schema(description = "字典ID")
	@NotNull(groups = { Groups.Default.class })
	private Long dictId;

	/**
	 * 值
	 */
	@Schema(description = "值", example = "1")
	@NotNull(groups = { Groups.Default.class })
	@Size(min = 1, max = 255, groups = { Groups.Default.class })
	private String value;

	/**
	 * 标签
	 */
	@Schema(description = "标签", example = "red color")
	@NotNull(groups = { Groups.Default.class })
	@Size(min = 1, max = 20, groups = { Groups.Default.class })
	private String label;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	@Size(min = 1, max = 40, groups = { Groups.Default.class })
	private String remarks;

}
