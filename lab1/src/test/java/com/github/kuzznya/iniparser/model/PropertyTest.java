package com.github.kuzznya.iniparser.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    @Test
    void createProperty_WhenDouble_ReturnDoubleProperty() {
        assertEquals(1.5, Property.createProperty("test", "1.5").getValue());
        assertEquals(10.1, Property.createProperty("test", "10.1").getValue());
    }

    @Test
    void createProperty_WhenInt_ReturnIntProperty() {
        assertEquals(1, Property.createProperty("test", "1").getValue());
        assertEquals(10, Property.createProperty("test", "10").getValue());
    }

    @Test
    void createProperty_WhenString_ReturnStrProperty() {
        assertEquals("str", Property.createProperty("test", "str").getValue());
    }
}