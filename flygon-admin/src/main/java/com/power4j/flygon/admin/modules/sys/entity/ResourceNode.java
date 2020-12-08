package com.power4j.flygon.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.power4j.flygon.common.data.tree.entity.TreePath;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/30
 * @since 1.0
 */
@Data
@TableName("t_resource_node")
@NoArgsConstructor
public class ResourceNode extends TreePath implements Serializable {
	private static final long serialVersionUID = 1L;

	public ResourceNode(Long ancestor, Long descendant, Integer distance) {
		super(ancestor, descendant, distance);
	}
}
