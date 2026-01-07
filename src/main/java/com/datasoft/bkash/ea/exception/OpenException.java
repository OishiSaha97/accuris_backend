package com.datasoft.bkash.ea.exception;

public class OpenException extends RuntimeException {

  public OpenException(String msg, Exception e) {
    super(msg, e);
  }

}
