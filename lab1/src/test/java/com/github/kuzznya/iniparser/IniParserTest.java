package com.github.kuzznya.iniparser;

import com.github.kuzznya.iniparser.exception.InvalidFormatException;
import com.github.kuzznya.iniparser.exception.PropertyNotFoundException;
import com.github.kuzznya.iniparser.exception.SectionNotFoundException;
import com.github.kuzznya.iniparser.exception.TypeMismatchException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IniParserTest {

    @Test
    void parse_WhenValidFile_ReturnConfiguration()
            throws URISyntaxException, FileNotFoundException, InvalidFormatException,
            SectionNotFoundException, PropertyNotFoundException, TypeMismatchException {
        var parser = new IniParser();
        File iniFile = new File(this.getClass().getResource("/valid.ini").toURI());

        var config = parser.parse(iniFile);

        assertEquals(5000, config.section("COMMON").getInt("StatisterTimeMs"));
        assertEquals("/sata/panorama", config.section("COMMON").getString("DiskCachePath"));
        assertEquals(0.65, config.section("ADC_DEV").getDouble("BufferLenSeconds"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/invalid1.ini", "/invalid2.ini"})
    void parse_WhenInvalidFile_ThrowException(String filename) throws URISyntaxException {
        var parser = new IniParser();
        File iniFile = new File(this.getClass().getResource(filename).toURI());

        assertThrows(InvalidFormatException.class, () -> parser.parse(iniFile));
    }
}