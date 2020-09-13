package com.github.kuzznya.iniparser.exception;

public class PropertyNotFoundException extends ParserException {
    public PropertyNotFoundException() {
        super("Property not found");
    }

    public PropertyNotFoundException(String key) {
        super("Property with " + key + " not found");
    }
}
