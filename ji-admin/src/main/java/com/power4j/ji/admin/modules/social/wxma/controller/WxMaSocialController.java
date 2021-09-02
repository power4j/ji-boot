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

package com.power4j.ji.admin.modules.social.wxma.controller;

import com.power4j.ji.admin.modules.social.wxma.dto.UserBindingDTO;
import com.power4j.ji.admin.modules.social.wxma.dto.UserInfoDTO;
import com.power4j.ji.admin.modules.social.wxma.service.AccountService;
import com.power4j.ji.common.core.model.ApiResponse;
import com.power4j.ji.common.security.audit.ApiLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/26
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/social/wx-mini")
@Tag(name = "账号服务")
public class WxMaSocialController {
	private final AccountService accountService;

	@ApiLog(module = "社交账号", tag = "小程序账号绑定")
	@PostMapping("/connect")
	@Operation(summary = "绑定账号")
	public ApiResponse<UserInfoDTO> userBind(@RequestBody UserBindingDTO dto) {
		return accountService.userBind(dto);
	}

}
