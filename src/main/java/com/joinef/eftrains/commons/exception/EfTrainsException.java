package com.joinef.eftrains.commons.exception;

public class EfTrainsException extends RuntimeException {

    private final int status;

    public EfTrainsException(int status, String message) {
        super(message);
        this.status = status;
    }

    public EfTrainsException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
