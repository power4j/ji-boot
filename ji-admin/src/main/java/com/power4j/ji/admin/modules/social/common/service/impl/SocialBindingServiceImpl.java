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

package com.power4j.ji.admin.modules.social.common.service.impl;

import com.power4j.ji.admin.modules.social.common.entity.SocialBinding;
import com.power4j.ji.admin.modules.social.common.mapper.SocialBindingMapper;
import com.power4j.ji.admin.modules.social.common.service.SocialBindingService;
import com.power4j.ji.common.core.constant.SysErrorCodes;
import com.power4j.ji.common.core.exception.BizException;
import com.power4j.ji.common.data.crud.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/9/1
 * @since 1.0
 */
@Service
public class SocialBindingServiceImpl extends BaseServiceImpl<SocialBindingMapper, SocialBinding>
		implements SocialBindingService {

	@Override
	public Optional<SocialBinding> findByOpenId(String type, String openId) {
		return lambdaQuery().eq(SocialBinding::getType, type).eq(SocialBinding::getOpenId, openId).oneOpt();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public SocialBinding createBinding(String type, String openId, long uid) {
		SocialBinding binding = lambdaQuery().eq(SocialBinding::getType, type).eq(SocialBinding::getUid, uid).one();
		if (Objects.nonNull(binding)) {
			throw new BizException(SysErrorCodes.E_CONFLICT, "用户已经绑定同类型社交账号");
		}
		binding = new SocialBinding();
		binding.setOpenId(openId);
		binding.setUid(uid);
		binding.setType(type);
		save(binding);
		return binding;
	}

	@Override
	public void deleteBinding(String type, long uid) {
		lambdaUpdate().eq(SocialBinding::getType, type).eq(SocialBinding::getUid, uid).remove();
	}

}
