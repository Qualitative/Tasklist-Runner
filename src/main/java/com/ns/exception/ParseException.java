package com.ns.exception;

public class ParseException extends Exception {

    private static final long serialVersionUID = 5349353445407208827L;

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
