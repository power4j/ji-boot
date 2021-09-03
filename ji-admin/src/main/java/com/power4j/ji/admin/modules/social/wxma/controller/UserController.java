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

import com.power4j.ji.admin.modules.social.wxma.service.AccountService;
import com.power4j.ji.common.security.audit.ApiLog;
import com.power4j.ji.common.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2021/8/25
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wx-mini/user")
@Tag(name = "账号服务")
public class UserController {

	private final AccountService accountService;

	// ~ Helper
	// ===================================================================================================

	@ApiLog(module = "微信小程序", tag = "查看账号绑定二维码")
	@GetMapping(value = "/binding/qr/img", produces = MediaType.IMAGE_JPEG_VALUE)
	@Operation(summary = "查看账号绑定二维码")
	public ResponseEntity<Resource> getAccBindingQrCodeImage() {
		Long uid = SecurityUtil.getLoginUserId().orElse(null);
		if (Objects.isNull(uid)) {
			return ResponseEntity.notFound().build();
		}
		try {
			ByteArrayResource inputStream = new ByteArrayResource(accountService.getAccBindingQrImgData(uid));
			return ResponseEntity.ok().contentLength(inputStream.contentLength()).body(inputStream);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().build();
		}
	}

}
