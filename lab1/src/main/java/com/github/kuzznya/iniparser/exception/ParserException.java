package com.github.kuzznya.iniparser.exception;

public class ParserException extends Exception {

    public ParserException() {}

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
