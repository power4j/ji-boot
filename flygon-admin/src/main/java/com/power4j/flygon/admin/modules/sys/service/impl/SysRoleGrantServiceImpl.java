package com.power4j.flygon.admin.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.flygon.admin.modules.sys.constant.DictConstant;
import com.power4j.flygon.admin.modules.sys.dao.SysRoleGranteeMapper;
import com.power4j.flygon.admin.modules.sys.dao.SysUserMapper;
import com.power4j.flygon.admin.modules.sys.dto.SysRoleGrantDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysRoleGrant;
import com.power4j.flygon.admin.modules.sys.service.SysRoleGrantService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.flygon.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/14
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysRoleGrantServiceImpl extends AbstractCrudService<SysRoleGranteeMapper, SysRoleGrantDTO, SysRoleGrant> implements SysRoleGrantService {

	private final SysUserMapper sysUserMapper;

	@Override
	public int removeByUser(Long userId) {
		Wrapper<SysRoleGrant> wrapper = Wrappers.<SysRoleGrant>lambdaQuery()
				.eq(SysRoleGrant::getUserId, userId);
		return getBaseMapper().delete(wrapper);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<SysRoleGrant> add(Long userId, List<SysRoleGrantDTO> roles) {
		if (roles == null || roles.isEmpty()) {
			return Collections.emptyList();
		}
		List<SysRoleGrant> list = roles.stream()
				.map(o -> {
					SysRoleGrant grantee = new SysRoleGrant();
					grantee.setUserId(userId);
					grantee.setRoleId(o.getRoleId());
					grantee.setGrantType(o.getGrantType());
					return grantee;
				}).collect(Collectors.toList());
		saveBatch(list);
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<SysRoleGrant> setRoles(Long userId, List<SysRoleGrantDTO> roles) {
		return null;
	}

	@Override
	public List<SysRoleGrant> getByUser(Long userId,String grantType) {
		Wrapper<SysRoleGrant> wrapper = Wrappers.<SysRoleGrant>lambdaQuery()
				.eq(SysRoleGrant::getUserId, userId)
				.eq(StrUtil.isNotEmpty(grantType),SysRoleGrant::getGrantType,grantType);
		return list(wrapper);
	}

	@Override
	protected SysRoleGrantDTO prePostHandle(SysRoleGrantDTO dto) throws BizException {
		grantCheck(dto);
		return super.prePostHandle(dto);
	}

	@Override
	protected SysRoleGrantDTO prePutHandle(SysRoleGrantDTO dto) {
		grantCheck(dto);
		return super.prePutHandle(dto);
	}

	@Override
	protected SysRoleGrant preDeleteHandle(Serializable id) {
		return super.preDeleteHandle(id);
	}

	protected void grantCheck(SysRoleGrantDTO obj){
		// 没有检查授权用户,角色是否存在
		if(SecurityUtil.getLoginUserId().get().equals(obj.getUserId())){
			throw new BizException(SysErrorCodes.E_FORBIDDEN, "不能修改自己的权限");
		}
		long count = getByUser(obj.getUserId(), DictConstant.Role.GRANT_TYPE_PERMITTED)
				.stream()
				.filter(o -> o.getRoleId().equals(obj.getRoleId()))
				.count();
		if(count <= 0){
			throw new BizException(SysErrorCodes.E_FORBIDDEN, String.format("无权限对角色%s进行授权", obj.getRoleId()));
		}
	}

}
