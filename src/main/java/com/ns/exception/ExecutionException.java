package com.ns.exception;

public class ExecutionException extends Exception {

    private static final long serialVersionUID = 6386938060080183184L;

    private int exitCode;

    public ExecutionException(String message, int exitCode) {
        super(message);
        this.exitCode = exitCode;
    }

    public ExecutionException(String message, int exitCode, Throwable throwable) {
        super(message, throwable);
        this.exitCode = exitCode;
    }

    public int getExitCode() {
        return exitCode;
    }

    @Override
    public String toString() {
        return super.toString() + "; exitCode=" + exitCode;
    }

}
