package com.power4j.flygon.admin.modules.sys.component;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.flygon.admin.modules.sys.entity.ResourceNode;
import com.power4j.flygon.common.data.tree.util.AbstractTreePathBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author  CJ (power4j@outlook.com)
 * @date    2020/11/30
 * @since   1.0
 */
@Component
public class ResourcePathBuilder extends AbstractTreePathBuilder<ResourceNode, BaseMapper<ResourceNode>> {
	@Override
	protected ResourceNode createNode(Long ancestor, Long descendant, Integer distance) {
		return new ResourceNode(ancestor,descendant,distance);
	}

	@Override
	protected Wrapper<ResourceNode> getRemoveNodeWrapper(Serializable id) {
		return Wrappers.<ResourceNode>lambdaQuery().eq(ResourceNode::getDescendant,id);
	}

	@Override
	protected Wrapper<ResourceNode> getLoadAncestorsWrapper(Serializable id,int distanceMin, int distanceMax) {
		return Wrappers.<ResourceNode>lambdaQuery().eq(ResourceNode::getDescendant,id)
				.ge(distanceMin >= 0,ResourceNode::getDistance,distanceMin)
				.le(distanceMax >= 0,ResourceNode::getDistance,distanceMax);
	}

	@Override
	protected Wrapper<ResourceNode> getLoadDescendantsWrapper(Serializable id, int distanceMin, int distanceMax) {
		return Wrappers.<ResourceNode>lambdaQuery()
				.eq(ResourceNode::getAncestor,id)
				.ge(distanceMin >= 0,ResourceNode::getDistance,distanceMin)
				.le(distanceMax >= 0,ResourceNode::getDistance,distanceMax);
	}

	@Override
	protected Wrapper<ResourceNode> getLoadByDistanceWrapper(int distance) {
		return Wrappers.<ResourceNode>lambdaQuery()
				.eq(ResourceNode::getDistance,distance);
	}
}
