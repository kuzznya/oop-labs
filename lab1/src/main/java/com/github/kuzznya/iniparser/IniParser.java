package com.github.kuzznya.iniparser;

import com.github.kuzznya.iniparser.exception.InvalidFormatException;
import com.github.kuzznya.iniparser.exception.ParserException;
import com.github.kuzznya.iniparser.model.Configuration;
import com.github.kuzznya.iniparser.model.Property;
import com.github.kuzznya.iniparser.model.Section;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IniParser {

    private final String sectionNameRegEx = "\\[\\w+]";
    private final String propertyRegEx = "\\w+\\s*=\\s*[\\w./]+";

    private final String commentRegEx = ";.*";
    private final String lineEndingRegEx = "\\s*(" + commentRegEx + ")?";

    private final Pattern validLinePattern =
            Pattern.compile("\\s*(" + sectionNameRegEx + "|" + propertyRegEx + ")?" + lineEndingRegEx);

    private final Pattern sectionNameStringPattern = Pattern.compile("\\s*" + sectionNameRegEx + lineEndingRegEx);
    private final Pattern sectionNamePattern = Pattern.compile(sectionNameRegEx);

    private final Pattern propertyStringPattern = Pattern.compile("\\s*" + propertyRegEx + lineEndingRegEx);

    private Property<?> parseProperty(String line) throws InvalidFormatException {
        if (!propertyStringPattern.matcher(line).matches())
            throw new InvalidFormatException("Line " + line + " does not match property pattern");

        String property = line.replaceFirst(commentRegEx, "").trim();

        return Property.createProperty(
                property.split("=")[0].stripTrailing(),
                property.split("=")[1].stripLeading()
        );
    }

    private Section parseNextSection(Scanner scanner) throws InvalidFormatException {
        try {
            if (!scanner.hasNextLine() || !scanner.hasNext(sectionNameStringPattern))
                throw new InvalidFormatException();

            String sectionNameString = scanner.next(sectionNameStringPattern);

            Matcher matcher = sectionNamePattern.matcher(sectionNameString);
            if (!matcher.find())
                throw new InvalidFormatException("Line " + sectionNameString + " does not have section name");

            String sectionName = sectionNameString.substring(
                    matcher.start() + 1,
                    matcher.end() - 1
            ).trim();

            Section section = new Section(sectionName);

            while (!scanner.hasNext(sectionNameStringPattern) && scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (!validLinePattern.matcher(line).matches())
                    throw new InvalidFormatException();
                if (propertyStringPattern.matcher(line).matches())
                    section.addProperty(
                            parseProperty(line)
                    );
            }

            return section;

        } catch (NoSuchElementException ex) {
            throw new InvalidFormatException();
        }
    }

    public Configuration parse(File file) throws FileNotFoundException, InvalidFormatException {
        Configuration configuration = new Configuration();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                if (!scanner.hasNext(validLinePattern))
                    throw new InvalidFormatException();

                if (scanner.hasNext(sectionNamePattern))
                    configuration.addSection(parseNextSection(scanner));
                else if (scanner.hasNext(propertyStringPattern))
                    throw new ParserException("An error occurred while trying to parse file");
                else
                    scanner.nextLine();
            }

        } catch (FileNotFoundException fex) {
            throw fex;
        }
        catch (Exception ex) {
            throw new InvalidFormatException(ex);
        }

        return configuration;
    }

    public boolean checkFileFormat(File file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                if (!validLinePattern.matcher(scanner.nextLine()).find())
                    return false;
            }
        }
        return true;
    }
}
