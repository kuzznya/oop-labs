package com.github.kuzznya.iniparser.exception;

public class InvalidFormatException extends ParserException {
    public InvalidFormatException() {
        super("Invalid format");
    }

    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException(Throwable cause) {
        super("Invalid format", cause);
    }
}
