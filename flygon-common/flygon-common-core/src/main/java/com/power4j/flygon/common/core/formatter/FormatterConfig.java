package com.power4j.flygon.common.core.formatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.YearMonth;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
public class FormatterConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatterForFieldType(YearMonth.class, new YearMonthFormatter());
		registry.addFormatterForFieldType(MonthDay.class, new MonthDayFormatter());
		registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
		registry.addFormatterForFieldType(LocalTime.class, new LocalTimeFormatter());
		registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
	}

}
