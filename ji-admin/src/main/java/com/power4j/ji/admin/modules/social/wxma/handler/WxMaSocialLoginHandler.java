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

package com.power4j.ji.admin.modules.social.wxma.handler;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.power4j.ji.common.security.social.AbstractSocialLoginHandler;
import com.power4j.ji.common.security.social.SocialConstant;
import com.power4j.ji.common.security.social.SocialLoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/26
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Component(SocialConstant.SOCIAL_KEY_WX_MINI_APP)
public class WxMaSocialLoginHandler extends AbstractSocialLoginHandler {

	private final WxMaService wxMaService;

	@Override
	public String getUserId(String code) throws SocialLoginException {
		try {
			WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo(code);
			return Optional.ofNullable(result.getUnionid()).orElse(result.getOpenid());
		}
		catch (WxErrorException e) {
			throw new SocialLoginException(e.getMessage(), e);
		}
	}

}
