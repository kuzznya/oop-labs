package com.github.kuzznya.iniparser.model;

import com.github.kuzznya.iniparser.exception.InvalidValueTypeException;

import java.util.Objects;

public class Property<T> {
    private final String key;
    private final ValueType type;
    private final T value;

    public Property(String key, T value) throws InvalidValueTypeException {
        this.key = key;

        if (value instanceof String)
            type = ValueType.STR;
        else if (Double.class.isAssignableFrom(value.getClass()))
            type = ValueType.DOUBLE;
        else if (Integer.class.isAssignableFrom(value.getClass()))
            type = ValueType.INT;
        else
            throw new InvalidValueTypeException();

        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public ValueType getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    public static Property<?> createProperty(String key, String value) {
        try {
            if (value.matches("\\d+"))
                return new Property<>(key, Integer.parseInt(value));
            else if (value.matches("\\d+\\.\\d+"))
                return new Property<>(key, Double.parseDouble(value));
            else
                return new Property<>(key, value);
        } catch (InvalidValueTypeException ex) {
            throw new RuntimeException("Cannot create property with key " + key);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property<?> property = (Property<?>) o;
        return Objects.equals(key, property.key) &&
                type == property.type &&
                Objects.equals(value, property.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, type, value);
    }

    @Override
    public String toString() {
        return "Property{" +
                "key='" + key + '\'' +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}
