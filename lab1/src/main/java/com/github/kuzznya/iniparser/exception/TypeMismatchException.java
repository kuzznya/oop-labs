package com.github.kuzznya.iniparser.exception;

import com.github.kuzznya.iniparser.model.ValueType;

public class TypeMismatchException extends ParserException {
    public TypeMismatchException(ValueType expected, ValueType actual) {
        super(expected.name() + " value type expected, but was " + actual.name());
    }
}
