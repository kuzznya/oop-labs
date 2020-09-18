package com.github.kuzznya.iniparser.model;

import com.github.kuzznya.iniparser.exception.InvalidValueTypeException;
import com.github.kuzznya.iniparser.exception.PropertyNotFoundException;
import com.github.kuzznya.iniparser.exception.TypeMismatchException;

import java.util.*;

public class Section {
    private final String name;

    private final Map<String, Property<?>> data = new LinkedHashMap<>();

    public Section(String name) {
        this.name = name;
    }

    public Section(String name, Property<?>... properties) {
        this.name = name;

        List.of(properties).forEach(this::addProperty);
    }

    public String getName() {
        return name;
    }

    public Optional<Property<?>> getProperty(String key) {
        return Optional.ofNullable(
                data.get(key)
        );
    }

    public int getInt(String key) throws TypeMismatchException, PropertyNotFoundException {
        Property<?> property = getProperty(key)
                .orElseThrow(() -> new PropertyNotFoundException(key));

        if (property.getType().equals(ValueType.INT))
            return (Integer) property.getValue();
        else
            throw new TypeMismatchException(ValueType.INT, property.getType());
    }

    public double getDouble(String key) throws TypeMismatchException, PropertyNotFoundException {
        Property<?> property = getProperty(key)
                .orElseThrow(() -> new PropertyNotFoundException(key));

        if (property.getType().equals(ValueType.DOUBLE))
            return (Double) property.getValue();
        else if (property.getType().equals(ValueType.INT))
            return (Integer) property.getValue();
        else
            throw new TypeMismatchException(ValueType.DOUBLE, property.getType());
    }

    public String getString(String key) throws PropertyNotFoundException {
        return getProperty(key)
                .orElseThrow(() -> new PropertyNotFoundException(key))
                .getValue()
                .toString();
    }

    public Optional<Integer> tryGetInt(String key) {
        return getProperty(key).map(Integer.class::cast);
    }

    public Optional<Double> tryGetDouble(String key) {
        return getProperty(key).map(Double.class::cast);
    }

    public Optional<String> tryGetString(String key) {
        return getProperty(key).map(Object::toString);
    }

    public <T> void addProperty(String key, T value) throws InvalidValueTypeException {
        data.put(key, new Property<>(key, value));
    }

    public <T> void addProperty(Property<T> property) {
        data.put(property.getKey(), property);
    }

    public void removeProperty(String key) {
        data.remove(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Objects.equals(name, section.name) &&
                data.equals(section.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, data);
    }

    @Override
    public String toString() {
        return "Section{" +
                "name='" + name + '\'' +
                ", data=" + data +
                '}';
    }
}
