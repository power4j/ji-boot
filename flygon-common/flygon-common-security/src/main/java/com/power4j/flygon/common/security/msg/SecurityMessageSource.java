package com.power4j.flygon.common.security.msg;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/23
 * @since 1.0
 */
public class SecurityMessageSource extends ReloadableResourceBundleMessageSource {
	public SecurityMessageSource() {
		setBasename("classpath:messages/messages");
		setDefaultLocale(Locale.CHINA);
	}

	public static MessageSourceAccessor getAccessor() {
		return new MessageSourceAccessor(new SecurityMessageSource());
	}
}
