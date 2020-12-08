package com.power4j.flygon.admin.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.flygon.admin.modules.sys.dao.SysUserMapper;
import com.power4j.flygon.admin.modules.sys.dto.SysUserDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysUser;
import com.power4j.flygon.admin.modules.sys.service.SysUserService;
import com.power4j.flygon.admin.modules.sys.vo.SearchSysUserVO;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.data.crud.util.CrudUtil;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.flygon.common.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Service
public class SysUserServiceImpl extends AbstractCrudService<SysUserMapper, SysUserDTO, SysUser>
		implements SysUserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public SysUserDTO put(SysUserDTO dto) {
		SysUser entity = toEntity(dto);
		entity.setUpdateBy(SecurityUtil.getLoginUsername().orElse(null));
		updateById(toEntity(dto));
		return toDto(entity);
	}

	@Override
	public SysUserDTO post(SysUserDTO dto) {
		SysUser entity = toEntity(dto);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		entity.setCreateBy(SecurityUtil.getLoginUsername().orElse(null));
		save(entity);
		return toDto(entity);
	}

	@Override
	public PageData<SysUserDTO> selectPage(PageRequest pageRequest, SearchSysUserVO param) {
		Wrapper<SysUser> wrapper = new QueryWrapper<>();
		if (param != null) {
			wrapper = new QueryWrapper<SysUser>().lambda()
					.likeRight(StrUtil.isNotEmpty(param.getUsername()), SysUser::getUsername, param.getUsername())
					.ge(null != param.getStartDate(), SysUser::getCreateAt,
							CrudUtil.dayStart(param.getStartDate()))
					.le(null != param.getEndDate(), SysUser::getCreateAt, CrudUtil.dayEnd(param.getEndDate()));
		}
		Page<SysUser> page = getBaseMapper()
				.selectPage(CrudUtil.toPage(pageRequest, Arrays.asList(new OrderItem("create_at", true))), wrapper);
		return CrudUtil.toPageData(page).map(o -> toDto(o));
	}

	@Override
	public int countUsername(String username, Long ignoreId) {
		Wrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username)
				.ne(null != ignoreId, SysUser::getId, ignoreId);
		return getBaseMapper().selectCount(wrapper);
	}

	@Override
	public SysUser toEntity(SysUserDTO dto) {
		return BeanUtil.toBean(dto, SysUser.class, CopyOptions.create().setIgnoreProperties("slat"));
	}

}
