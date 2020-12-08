package com.power4j.flygon.admin.modules.security.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.flygon.admin.modules.security.dao.UserTokenMapper;
import com.power4j.flygon.admin.modules.security.entity.UserToken;
import com.power4j.flygon.admin.modules.security.service.ApiTokenService;
import com.power4j.flygon.common.data.crud.service.impl.AbstractCrudService;
import com.power4j.flygon.common.security.LoginUser;
import com.power4j.flygon.common.security.config.SecurityProperties;
import com.power4j.flygon.common.security.model.ApiToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
public class ApiTokenServiceImpl extends AbstractCrudService<UserTokenMapper, ApiToken, UserToken>
		implements ApiTokenService {

	private final SecurityProperties securityProperties;

	@Override
	public ApiToken loadApiToken(String tokenValue) {
		LambdaQueryWrapper<UserToken> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(UserToken::getToken, tokenValue);
		return searchOne(queryWrapper).orElse(null);
	}

	@Override
	public boolean deleteToken(String tokenValue) {
		LambdaQueryWrapper<UserToken> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(UserToken::getToken, tokenValue);
		return remove(queryWrapper);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ApiToken createToken(Authentication authentication) {
		LocalDateTime expire = LocalDateTime.now().plusSeconds(securityProperties.getApiToken().getExpireSec());
		Assert.isInstanceOf(LoginUser.class, authentication.getPrincipal());
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		UserToken entity = getBaseMapper().selectForUpdate(loginUser.getUid());
		if (entity == null) {
			entity = new UserToken();
			entity.setToken(UUID.fastUUID().toString());
			entity.setUsername(loginUser.getUsername());
			entity.setUuid(loginUser.getUid());
			entity.setExpireIn(expire);
			save(entity);
		}
		else {
			entity.setToken(UUID.fastUUID().toString());
			entity.setExpireIn(expire);
			updateById(entity);
		}
		return toDto(entity).setName(loginUser.getName()).setIssuedBy(securityProperties.getApiToken().getIssueBy());
	}

}
