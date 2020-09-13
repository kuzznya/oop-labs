package com.github.kuzznya.iniparser.exception;

public class InvalidValueTypeException extends ParserException {
    public InvalidValueTypeException() {
        super("Invalid type of configuration value");
    }
}
