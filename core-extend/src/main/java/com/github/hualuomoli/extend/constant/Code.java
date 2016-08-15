package com.github.hualuomoli.extend.constant;

import com.github.hualuomoli.exception.CodeException.CodeError;

public enum Code implements CodeError {

	ROLE_NOT_FOUND(101, "角色不存在"), //
	ROLE_ALREADY_EXISTS(102, "角色编码已存在"), //
	;

	private Integer code;
	private String message;

	private Code(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}