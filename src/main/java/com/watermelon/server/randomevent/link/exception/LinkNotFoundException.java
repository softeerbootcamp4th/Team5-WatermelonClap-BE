package com.watermelon.server.randomevent.link.exception;

public class LinkNotFoundException extends RuntimeException{
    public LinkNotFoundException() {
        super();
    }

    public LinkNotFoundException(String message) {
        super(message);
    }

    public LinkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LinkNotFoundException(Throwable cause) {
        super(cause);
    }

    protected LinkNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
