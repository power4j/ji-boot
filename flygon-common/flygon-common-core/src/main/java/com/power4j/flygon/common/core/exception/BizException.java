package com.power4j.flygon.common.core.exception;

/**
 * 业务异常
 * @author CJ (power4j@outlook.com)
 * @date 2020/11/27
 * @since 1.0
 */
public class BizException extends RtException{
	private int code;

	public BizException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BizException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public BizException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
