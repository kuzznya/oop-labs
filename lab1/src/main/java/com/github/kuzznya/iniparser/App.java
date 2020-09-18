package com.github.kuzznya.iniparser;

import com.github.kuzznya.iniparser.exception.ParserException;
import com.github.kuzznya.iniparser.exception.PropertyNotFoundException;
import com.github.kuzznya.iniparser.model.Configuration;

import java.io.File;
import java.io.FileNotFoundException;

public class App {

    public static void main(String[] args) {
        if (args.length < 3 || args.length > 4) {
            System.err.println("Invalid args count");
            return;
        }

        IniParser parser = new IniParser();
        try {
            if (!parser.checkFileFormat(new File(args[0])))
                throw new ParserException();
            Configuration config = parser.parse(new File(args[0]));

            if (args.length == 3)
                System.out.println(
                        config.section(args[1])
                                .getProperty(args[2])
                                .orElseThrow(PropertyNotFoundException::new)
                );
            else {
                switch (args[3]) {
                    case "int":
                        System.out.println(
                                config.section(args[1])
                                        .getInt(args[2])
                        );
                        break;
                    case "double":
                        System.out.println(
                                config.section(args[1])
                                        .getDouble(args[2])
                        );
                        break;
                    case "string":
                        System.out.println(
                                config.section(args[1])
                                        .getString(args[2])
                        );
                        break;
                    default:
                        System.err.println("Invalid type");
                }
            }
        } catch (FileNotFoundException fex) {
            System.err.println("File not found");
        } catch (ParserException pex) {
            System.err.println("Parsing error");
            pex.printStackTrace();
        }
    }
}
