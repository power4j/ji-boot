package com.power4j.flygon.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.power4j.flygon.admin.modules.sys.dao.SysResourceGranteeMapper;
import com.power4j.flygon.admin.modules.sys.entity.SysResourceGrantee;
import com.power4j.flygon.admin.modules.sys.service.SysResourceGrantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
@Service
public class SysResourceGrantServiceImpl extends ServiceImpl<SysResourceGranteeMapper, SysResourceGrantee>
		implements SysResourceGrantService {

	@Override
	public int removeByRole(Long roleId) {
		Wrapper<SysResourceGrantee> wrapper = Wrappers.<SysResourceGrantee>lambdaQuery()
				.eq(SysResourceGrantee::getRoleId, roleId);
		return getBaseMapper().delete(wrapper);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<SysResourceGrantee> add(Long roleId, Collection<Long> resourceIds) {
		if (resourceIds == null || resourceIds.isEmpty()) {
			return Collections.emptyList();
		}
		List<SysResourceGrantee> list = resourceIds.stream()
				.map(o -> new SysResourceGrantee().setResourceId(o).setRoleId(roleId)).collect(Collectors.toList());
		saveBatch(list);
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<SysResourceGrantee> setResources(Long roleId, Collection<Long> resourceIds) {
		removeByRole(roleId);
		return add(roleId, resourceIds);
	}

	@Override
	public List<SysResourceGrantee> getByRole(Long roleId) {
		Wrapper<SysResourceGrantee> wrapper = Wrappers.<SysResourceGrantee>lambdaQuery()
				.eq(SysResourceGrantee::getRoleId, roleId);
		return getBaseMapper().selectList(wrapper);
	}

}
