package com.power4j.flygon.admin.modules.sys.vo;

import com.power4j.flygon.common.core.model.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/20
 * @since 1.0
 */
@Data
public class SysMenuVO extends TreeNode implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "类型 0 菜单 1 按钮")
	@Pattern(regexp = "0|1")
	private String type;

	@Schema(description = "路由")
	@Size(min = 1, max = 255)
	private String route;

}
