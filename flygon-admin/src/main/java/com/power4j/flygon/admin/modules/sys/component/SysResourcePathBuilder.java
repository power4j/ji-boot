package com.power4j.flygon.admin.modules.sys.component;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.flygon.admin.modules.sys.entity.SysResourceNode;
import com.power4j.flygon.common.data.tree.util.AbstractTreePathBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/30
 * @since 1.0
 */
@Component
public class SysResourcePathBuilder extends AbstractTreePathBuilder<SysResourceNode, BaseMapper<SysResourceNode>> {

	@Override
	protected SysResourceNode createNode(Long ancestor, Long descendant, Integer distance) {
		return new SysResourceNode(ancestor, descendant, distance);
	}

	@Override
	protected Wrapper<SysResourceNode> getRemoveNodeWrapper(Serializable id) {
		return Wrappers.<SysResourceNode>lambdaQuery().eq(SysResourceNode::getDescendant, id);
	}

	@Override
	protected Wrapper<SysResourceNode> getLoadAncestorsWrapper(Serializable id, int distanceMin, int distanceMax) {
		return Wrappers.<SysResourceNode>lambdaQuery().eq(SysResourceNode::getDescendant, id)
				.ge(distanceMin >= 0, SysResourceNode::getDistance, distanceMin)
				.le(distanceMax >= 0, SysResourceNode::getDistance, distanceMax);
	}

	@Override
	protected Wrapper<SysResourceNode> getLoadDescendantsWrapper(Serializable id, int distanceMin, int distanceMax) {
		return Wrappers.<SysResourceNode>lambdaQuery().eq(SysResourceNode::getAncestor, id)
				.ge(distanceMin >= 0, SysResourceNode::getDistance, distanceMin)
				.le(distanceMax >= 0, SysResourceNode::getDistance, distanceMax);
	}

	@Override
	protected Wrapper<SysResourceNode> getLoadDescendantsWrapper(Collection<Serializable> ids, int distanceMin, int distanceMax) {
		return Wrappers.<SysResourceNode>lambdaQuery().in(SysResourceNode::getAncestor, ids)
				.ge(distanceMin >= 0, SysResourceNode::getDistance, distanceMin)
				.le(distanceMax >= 0, SysResourceNode::getDistance, distanceMax);
	}

	@Override
	protected Wrapper<SysResourceNode> getLoadByDistanceWrapper(int distance) {
		return Wrappers.<SysResourceNode>lambdaQuery().eq(SysResourceNode::getDistance, distance);
	}

}
