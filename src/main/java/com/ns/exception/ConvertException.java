package com.ns.exception;

public class ConvertException extends Exception {

    private static final long serialVersionUID = -4758873671177359334L;

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
