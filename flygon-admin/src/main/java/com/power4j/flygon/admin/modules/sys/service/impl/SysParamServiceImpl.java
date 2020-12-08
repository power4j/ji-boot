package com.power4j.flygon.admin.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.flygon.admin.modules.sys.dao.SysParamMapper;
import com.power4j.flygon.admin.modules.sys.dto.SysParamDTO;
import com.power4j.flygon.admin.modules.sys.entity.SysParam;
import com.power4j.flygon.admin.modules.sys.service.SysParamService;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.flygon.common.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/12/9
 * @since 1.0
 */
@Slf4j
@Service
public class SysParamServiceImpl extends AbstractCrudService<SysParamMapper, SysParamDTO, SysParam> implements SysParamService {
	@Override
	public int countParamKey(String key, Long ignoreId) {
		return countByColumn("param_key",key,ignoreId);
	}

	@Override
	public Optional<SysParamDTO> findByKey(String key) {
		SysParam entity = getBaseMapper().selectOne(Wrappers.<SysParam>lambdaQuery().eq(SysParam::getParamKey,key));
		return Optional.ofNullable(toDto(entity));
	}

	@Override
	protected Wrapper<SysParam> getSearchWrapper(SysParamDTO param) {
		if(param == null){
			return Wrappers.emptyWrapper();
		}
		return Wrappers.<SysParam>lambdaQuery()
				.eq(StrUtil.isNotBlank(param.getStatus()),SysParam::getStatus,param.getStatus())
				.like(StrUtil.isNotBlank(param.getParamKey()),SysParam::getParamKey,param.getParamKey());
	}

	@Override
	public SysParamDTO post(SysParamDTO dto) {
		dto.setCreateBy(SecurityUtil.getLoginUsername().orElse(null));
		return super.post(dto);
	}

	@Override
	public SysParamDTO put(SysParamDTO dto) {
		dto.setUpdateBy(SecurityUtil.getLoginUsername().orElse(null));
		return super.put(dto);
	}
}
