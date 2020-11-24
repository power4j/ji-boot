package com.power4j.flygon.common.security.auth;

import cn.hutool.core.util.StrUtil;
import com.power4j.flygon.common.security.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/22
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
public class ApiTokenAuthenticationProvider implements AuthenticationProvider {
	protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private final TokenService tokenService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(authentication.isAuthenticated()){
			return authentication;
		}
		String tokenValue = authentication.getCredentials().toString();
		if(StrUtil.isBlank(tokenValue)){
			log.debug("认证失败:token为空");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials","无效的凭据"));
		}
		Authentication savedAuthentication = tokenService.loadAuthentication(tokenValue);
		if(savedAuthentication == null || savedAuthentication.getPrincipal() == null || !(savedAuthentication.getPrincipal() instanceof  UserDetails)){
			log.debug("认证失败:无效的token");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials","无效的凭据"));
		}
		UserDetails userDetails = (UserDetails)savedAuthentication.getPrincipal();
		PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(userDetails, tokenValue,userDetails.getAuthorities());
		preAuthenticatedAuthenticationToken.setAuthenticated(true);
		return preAuthenticatedAuthenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return ApiTokenAuthentication.class.isAssignableFrom(authentication);
	}
}
