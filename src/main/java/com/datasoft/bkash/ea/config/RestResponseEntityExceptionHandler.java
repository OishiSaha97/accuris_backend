package com.datasoft.bkash.ea.config;

import com.datasoft.bkash.ea.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        if (ex.getCause() != null && ex.getCause() instanceof SQLException) {
            SQLException se = (SQLException) ex.getCause();
            if (se.getSQLState().equals("42000")) {
                ApiResponse apiResponse = new ApiResponse(HttpStatus.LOCKED.value(), se.getMessage(), null);
                return handleExceptionInternal(se, apiResponse, new HttpHeaders(), HttpStatus.LOCKED, request);
            }
        }
        throw ex;
    }
}