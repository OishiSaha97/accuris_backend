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
public class BadRequestException extends RuntimeException {
    ErrorCodes errorCodes;

    public BadRequestException(ErrorCodes codes) {
        super(codes.getMessage());
        this.errorCodes = codes;
    }
}
