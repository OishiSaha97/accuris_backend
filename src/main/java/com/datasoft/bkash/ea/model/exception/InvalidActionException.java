package com.datasoft.bkash.ea.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
@EqualsAndHashCode(callSuper = false)
public class InvalidActionException extends RuntimeException {
	ErrorCodes errorCodes;

	public InvalidActionException(ErrorCodes codes) {
		super(codes.getMessage());
		this.errorCodes = codes;
	}

	public InvalidActionException(ErrorCodes codes, Object... args) {
		super(String.format(codes.getMessage(), args));
		this.errorCodes = codes;
	}
}
