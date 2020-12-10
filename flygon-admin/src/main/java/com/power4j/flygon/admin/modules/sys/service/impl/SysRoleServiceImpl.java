package com.power4j.flygon.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.power4j.flygon.admin.modules.sys.dao.SysRoleMapper;
import com.power4j.flygon.admin.modules.sys.dao.SysUserMapper;
import com.power4j.flygon.admin.modules.sys.dto.SysRoleDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysRole;
import com.power4j.flygon.admin.modules.sys.entity.SysUser;
import com.power4j.flygon.admin.modules.sys.service.SysRoleService;
import com.power4j.flygon.common.core.constant.SysErrorCodes;
import com.power4j.flygon.common.core.exception.BizException;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.flygon.common.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/10
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends AbstractCrudService<SysRoleMapper, SysRoleDTO, SysRole>
		implements SysRoleService {
	private final SysUserMapper sysUserMapper;

	@Override
	public int countRoleCode(String code, Long ignoreId) {
		return countByColumn("code", code, ignoreId);
	}

	@Override
	public List<SysRole> listForUser(String username, String grantType) {
		SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername,username));
		return user == null ? Collections.emptyList() : getBaseMapper().selectByUserId(user.getId(),grantType);
	}

	@Override
	protected SysRoleDTO prePostHandle(SysRoleDTO dto) {
		if (countRoleCode(dto.getCode(), null) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("%s 不能使用", dto.getCode()));
		}
		dto.setOwner(SecurityUtil.getLoginUserId().orElse(null));
		return super.prePostHandle(dto);
	}

	@Override
	protected SysRoleDTO prePutHandle(SysRoleDTO dto) {
		if (countRoleCode(dto.getCode(), dto.getId()) > 0) {
			throw new BizException(SysErrorCodes.E_CONFLICT, String.format("%s 不能使用", dto.getCode()));
		}
		return super.prePutHandle(dto);
	}

	@Override
	protected SysRole preDeleteHandle(Serializable id) {
		return super.preDeleteHandle(id);
	}
}
