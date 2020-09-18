package com.github.kuzznya.iniparser.model;

import com.github.kuzznya.iniparser.exception.InvalidValueTypeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectionTest {

    private static Section section;

    @BeforeAll
    static void setUp() throws InvalidValueTypeException {
        section = new Section("TEST",
                new Property<>("int", 1),
                new Property<>("double", 2.0),
                new Property<>("str", "string")
        );
    }

    @Test
    public void getProperty_WhenPropertyExists_ReturnProperty() throws InvalidValueTypeException {
        assertEquals(new Property<>("int", 1), section.getProperty("int").orElseThrow());
        assertEquals(new Property<>("double", 2.0), section.getProperty("double").orElseThrow());
        assertEquals(new Property<>("str", "string"), section.getProperty("str").orElseThrow());
    }
}