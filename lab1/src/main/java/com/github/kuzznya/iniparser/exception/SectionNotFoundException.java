package com.github.kuzznya.iniparser.exception;

public class SectionNotFoundException extends ParserException {
    public SectionNotFoundException() {
        super("Section not found");
    }

    public SectionNotFoundException(String name) {
        super("Section with name " + name + " not found");
    }
}
