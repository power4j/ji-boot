/*
 * Copyright 2020 ChenJun (power4j@outlook.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.power4j.ji.admin.modules.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.power4j.ji.admin.modules.security.dao.UserTokenMapper;
import com.power4j.ji.admin.modules.security.entity.UserToken;
import com.power4j.ji.admin.modules.security.service.ApiTokenService;
import com.power4j.ji.admin.modules.sys.constant.CacheConstant;
import com.power4j.ji.common.data.crud.service.impl.BaseServiceImpl;
import com.power4j.ji.common.security.LoginUser;
import com.power4j.ji.common.security.config.SecurityProperties;
import com.power4j.ji.common.security.model.ApiToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/26
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiTokenServiceImpl extends BaseServiceImpl<UserTokenMapper, UserToken> implements ApiTokenService {

	private final SecurityProperties securityProperties;

	private final CacheManager cacheManager;

	@Cacheable(cacheNames = CacheConstant.Name.API_TOKEN_TO_USER_TOKEN, key = "#tokenValue")
	@Override
	public ApiToken loadApiToken(String tokenValue) {
		LambdaQueryWrapper<UserToken> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(UserToken::getToken, tokenValue);
		UserToken userToken = getOne(queryWrapper);
		return userToken == null ? null : BeanUtil.toBean(userToken, ApiToken.class);
	}

	@CacheEvict(cacheNames = CacheConstant.Name.API_TOKEN_TO_USER_TOKEN, key = "#tokenValue")
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
		int maxToken = securityProperties.getApiToken().getMaxUserToken();
		Assert.isInstanceOf(LoginUser.class, authentication.getPrincipal());
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		int count = removeOverLimited(loginUser.getUid(), maxToken);
		if (count > 0) {
			log.warn("Max user token exceed, {} token deleted for user: {}", count, loginUser.getUsername());
		}
		UserToken entity = new UserToken();
		entity.setToken(UUID.fastUUID().toString());
		entity.setUsername(loginUser.getUsername());
		entity.setUuid(loginUser.getUid());
		entity.setExpireIn(expire);
		save(entity);
		ApiToken token = BeanUtil.toBean(entity, ApiToken.class);
		token.setName(loginUser.getName());
		token.setIssuedBy(securityProperties.getApiToken().getIssueBy());
		return token;
	}

	protected List<UserToken> loadUserToken(Long uid, LocalDateTime deadline) {
		Wrapper<UserToken> wrapper = Wrappers.<UserToken>lambdaQuery().eq(UserToken::getUuid, uid)
				.gt(UserToken::getExpireIn, deadline);
		return getBaseMapper().selectList(wrapper);
	}

	protected int removeOverLimited(Long uid, int limit) {
		List<UserToken> tokenList = loadUserToken(uid, LocalDateTime.now()).stream()
				.sorted(Comparator.comparing(UserToken::getExpireIn)).collect(Collectors.toList());
		if (tokenList.size() >= limit) {
			List<UserToken> removed = CollectionUtil.sub(tokenList, 0, tokenList.size() - limit + 1);
			Cache cache = cacheManager.getCache(CacheConstant.Name.API_TOKEN_TO_USER_TOKEN);
			removed.forEach(userToken -> cache.evict(userToken.getToken()));
			List<Long> idList = removed.stream().map(UserToken::getId).collect(Collectors.toList());
			getBaseMapper().deleteBatchIds(idList);
			return idList.size();
		}
		return 0;
	}

}
