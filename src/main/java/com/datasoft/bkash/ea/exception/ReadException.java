package com.datasoft.bkash.ea.exception;

public class ReadException extends RuntimeException {

  public ReadException(String msg, Exception e) {
    super(msg, e);
  }
}
