package com.datasoft.bkash.ea.exception;

public class ParseException extends RuntimeException {

  public ParseException(Exception e) {
    super(e);
  }

  public ParseException(String msg, Exception e) {
    super(msg, e);
  }
}
