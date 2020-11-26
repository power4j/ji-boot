package com.power4j.flygon.admin.modules.security.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.flygon.admin.modules.security.dao.ApiTokenMapper;
import com.power4j.flygon.admin.modules.security.entity.ApiTokenEntity;
import com.power4j.flygon.admin.modules.security.service.ApiTokenService;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.flygon.common.security.config.SecurityProperties;
import com.power4j.flygon.common.security.model.ApiToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ApiTokenServiceImpl extends AbstractCrudService<ApiTokenMapper, ApiToken, ApiTokenEntity> implements ApiTokenService {

	private final SecurityProperties securityProperties;

	@Override
	public ApiToken loadApiToken(String tokenValue) {
		LambdaQueryWrapper<ApiTokenEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.ge(ApiTokenEntity::getToken,tokenValue);
		return searchOne(queryWrapper).orElse(null);
	}

	@Override
	public boolean deleteToken(String tokenValue) {
		LambdaQueryWrapper<ApiTokenEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.ge(ApiTokenEntity::getToken,tokenValue);
		return remove(queryWrapper);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ApiToken createToken(String username) {
		LocalDateTime expire = LocalDateTime.now().plusSeconds(securityProperties.getApiToken().getExpireSec());
		ApiTokenEntity entity = getBaseMapper().selectForUpdate(username);
		if(entity == null){
			entity = new ApiTokenEntity();
			entity.setToken(UUID.fastUUID().toString());
			entity.setUsername(username);
			entity.setExpireIn(expire);
			save(entity);
		}else{
			entity.setExpireIn(expire);
			updateById(entity);
		}
		return toDto(entity).setIssuedBy(securityProperties.getApiToken().getIssueBy());
	}
}
