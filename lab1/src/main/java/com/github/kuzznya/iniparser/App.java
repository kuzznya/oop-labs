package com.github.kuzznya.iniparser;

import com.github.kuzznya.iniparser.exception.ParserException;
import com.github.kuzznya.iniparser.model.Configuration;

import java.io.File;
import java.io.FileNotFoundException;

public class App {

    public static void main(String[] args) {
        IniParser parser = new IniParser();
        try {
            if (!parser.checkFileFormat(new File(args[0])))
                throw new ParserException();
            Configuration config = parser.parse(new File(args[0]));

            System.out.println(config.section("ADC_DEV").getProperty("BufferLenSeconds").get());
        } catch (FileNotFoundException fex) {
            System.err.println("File not found");
        } catch (ParserException pex) {
            System.err.println("Parsing error");
            pex.printStackTrace();
        }
    }
}
