package com.datasoft.bkash.ea.exception;

public class ExcelLimitExceededException extends RuntimeException {

    public ExcelLimitExceededException(String msg) {
        super(msg);
    }
}
