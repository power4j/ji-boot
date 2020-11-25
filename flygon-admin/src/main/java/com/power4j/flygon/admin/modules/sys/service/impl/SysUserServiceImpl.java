package com.power4j.flygon.admin.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power4j.flygon.admin.modules.sys.dao.SysUserMapper;
import com.power4j.flygon.admin.modules.sys.dto.SysUserDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysUserEntity;
import com.power4j.flygon.admin.modules.sys.service.SysUserService;
import com.power4j.flygon.admin.modules.sys.vo.SearchSysUserVO;
import com.power4j.flygon.common.core.model.PageData;
import com.power4j.flygon.common.core.model.PageRequest;
import com.power4j.flygon.common.data.crud.CrudUtil;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.flygon.common.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Service
public class SysUserServiceImpl extends AbstractCrudService<SysUserMapper, SysUserDTO, SysUserEntity>
		implements SysUserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public SysUserDTO create(SysUserDTO dto) {
		SysUserEntity entity = toEntity(dto);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		save(entity);
		return toDto(entity);
	}

	@Override
	public PageData<SysUserDTO> selectPage(PageRequest pageRequest, SearchSysUserVO param) {
		Wrapper<SysUserEntity> wrapper = new QueryWrapper<>();
		if (param != null) {
			wrapper = new QueryWrapper<SysUserEntity>().lambda()
					.likeRight(StrUtil.isNotEmpty(param.getUsername()), SysUserEntity::getUsername, param.getUsername())
					.ge(null != param.getStartDate(), SysUserEntity::getCreateAt,
							CrudUtil.dayStart(param.getStartDate()))
					.le(null != param.getEndDate(), SysUserEntity::getCreateAt, CrudUtil.dayEnd(param.getEndDate()));
		}
		Page<SysUserEntity> page = getBaseMapper()
				.selectPage(CrudUtil.toPage(pageRequest, Arrays.asList(new OrderItem("create_at", true))), wrapper);
		return CrudUtil.toPageData(page).map(o -> toDto(o));
	}

	@Override
	public int countUsername(String username, Long ignoreId) {
		return getBaseMapper().countUsernameIgnoreLogicDel(username, ignoreId);
	}

	@Override
	public SysUserEntity toEntity(SysUserDTO dto) {
		return BeanUtil.toBean(dto, SysUserEntity.class, CopyOptions.create().setIgnoreProperties("slat"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Wrapper<SysUserEntity> wrapper = new QueryWrapper<SysUserEntity>().lambda().eq(SysUserEntity::getUsername,
				username);
		SysUserEntity entity = getBaseMapper().selectOne(wrapper);
		if (entity == null) {
			log.debug(String.format("用户不存在:{}", username));
			throw new UsernameNotFoundException(String.format("用户不存在:{}", username));
		}
		LoginUser loginUser = new LoginUser(entity.getUsername(), entity.getPassword(), Collections.emptyList());
		return loginUser;
	}

}
