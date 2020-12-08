package com.power4j.flygon.common.security.endpoint;

import com.power4j.flygon.common.core.model.ApiResponse;
import com.power4j.flygon.common.core.util.ApiResponseUtil;
import com.power4j.flygon.common.security.model.ApiToken;
import com.power4j.flygon.common.security.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/23
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "api-token", description = "api-token管理")
@RequestMapping("${flygon.token.endpoint.path:/token}")
public class ApiTokenEndpoint {

	private final TokenService tokenService;

	private final AuthenticationManager authenticationManager;

	@Operation(summary = "获取token")
	@PostMapping
	public ApiResponse<ApiToken> getToken(@RequestBody TokenRequest tokenRequest) {
		Authentication userAuth = new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(),
				tokenRequest.getPassword());
		try {
			userAuth = authenticationManager.authenticate(userAuth);
		}
		catch (AccountStatusException e) {
			log.error(e.getMessage(), e);
			// expired, locked, disabled
			return ApiResponseUtil.fail("账号无效");
		}
		catch (BadCredentialsException e) {
			log.error(e.getMessage(), e);
			// username/password are wrong
			return ApiResponseUtil.fail("用户名或密码错误");
		}
		if (userAuth == null || !userAuth.isAuthenticated()) {
			return ApiResponseUtil.fail("认证失败:" + tokenRequest.getUsername());
		}

		return ApiResponseUtil.ok(tokenService.createToken(userAuth));
	}

	@Operation(summary = "删除token")
	@DeleteMapping("/{tokenId}")
	public ApiResponse<Boolean> delToken(@PathVariable("tokenId") String tokenId) {
		log.info("删除token: {}", tokenId);
		boolean deleted = tokenService.deleteToken(tokenId);
		if (!deleted) {
			log.warn("token删除失败:{}", tokenId);
		}
		return ApiResponseUtil.ok(deleted);
	}

}
