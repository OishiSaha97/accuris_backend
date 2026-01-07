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
public class ResourceNotFoundException extends RuntimeException {
    private String id;
    private ErrorCodes code;

    public ResourceNotFoundException(Object id, ErrorCodes codes) {
        super(String.format(codes.getMessage(), id));
        this.id = (String) id;
        this.code = codes;
    }
}
