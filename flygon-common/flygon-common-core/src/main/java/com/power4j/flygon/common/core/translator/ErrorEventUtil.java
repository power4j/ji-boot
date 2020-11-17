package com.power4j.flygon.common.core.translator;

import cn.hutool.core.lang.UUID;
import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/21
 * @since 1.0
 */
@UtilityClass
public class ErrorEventUtil {

	/**
	 * 发布异常错误事件
	 * @param publisher
	 * @param error
	 */
	public void publishEvent(ApplicationEventPublisher publisher, Throwable error) {
		ErrorEvent errorEvent = new ErrorEvent();
		errorEvent.setId(UUID.fastUUID().toString());
		errorEvent.setEx(error.getClass().getName());
		errorEvent.setInfo(error.getMessage());

		publisher.publishEvent(errorEvent);
	}

}
