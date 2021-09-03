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

package com.power4j.ji.admin.modules.social.wxma.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.power4j.ji.admin.modules.social.common.constant.SocialTypeEnum;
import com.power4j.ji.admin.modules.social.common.service.SocialBindingService;
import com.power4j.ji.admin.modules.social.wxma.dto.UserBindingDTO;
import com.power4j.ji.admin.modules.social.wxma.dto.UserInfoDTO;
import com.power4j.ji.admin.modules.social.wxma.service.AccountService;
import com.power4j.ji.admin.modules.sys.constant.StatusEnum;
import com.power4j.ji.admin.modules.sys.dto.SysUserDTO;
import com.power4j.ji.admin.modules.sys.service.SysUserService;
import com.power4j.ji.common.core.exception.RtException;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.core.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/25
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final static String ACC_BING_PAGE = null;

	private final static String APP_SCENE_BIND_ACC = "bind@%d";

	private final WxMaService wxMaService;

	private final SysUserService sysUserService;

	private final SocialBindingService socialBindingService;

	@Override
	public byte[] getAccBindingQrImgData(long uid) {
		SysUserDTO userDto = sysUserService.read(uid).orElse(null);
		if (Objects.isNull(userDto)) {
			throw new RtException("用户不存在");
		}
		// FIXME : 并不安全,仅用于流程演示
		final String scene = String.format(APP_SCENE_BIND_ACC, uid);
		try {
			return wxMaService.getQrcodeService().createWxaCodeUnlimitBytes(scene, ACC_BING_PAGE, 320, true, null,
					false);
		}
		catch (WxErrorException e) {
			log.error(e.getError().toString());
			throw new RtException(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ApiResponse<UserInfoDTO> userBind(UserBindingDTO dto) {
		final String serverToken = dto.getServerToken();
		long uid;
		try {
			uid = Long.parseLong(serverToken);
		}
		catch (NumberFormatException e) {
			if (log.isDebugEnabled()) {
				log.debug("非法请求参数 :uid = {}", serverToken);
			}
			return ApiResponseUtil.badRequest("非法请求参数");
		}

		SysUserDTO userDTO = sysUserService.read(uid).orElse(null);
		if (Objects.isNull(userDTO) || !StatusEnum.NORMAL.getValue().equals(userDTO.getStatus())) {
			return ApiResponseUtil.conflict("用户不存在或者已禁用");
		}
		try {
			WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo(dto.getWeiChatCode());
			String socialId = Optional.ofNullable(result.getUnionid()).orElse(result.getOpenid());
			if (log.isDebugEnabled()) {
				log.debug("绑定小程序用户,uid = {},微信ID = {}", uid, socialId);
			}
			socialBindingService.createBinding(SocialTypeEnum.WX_MA.getValue(), socialId, uid);
		}
		catch (WxErrorException e) {
			log.error(e.getError().toString());
			throw new RtException(e.getMessage(), e);
		}
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setUsername(userDTO.getUsername());
		userInfoDTO.setName(userDTO.getName());
		userInfoDTO.setCreateAt(userDTO.getCreateAt());

		return ApiResponseUtil.ok(userInfoDTO);
	}

}
