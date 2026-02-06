package com.pote.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessLogicException extends RuntimeException {

	public BusinessLogicException(String message) {
		super(message);
	}
}