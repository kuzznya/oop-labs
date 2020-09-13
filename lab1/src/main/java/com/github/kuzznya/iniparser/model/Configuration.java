package com.github.kuzznya.iniparser.model;

import com.github.kuzznya.iniparser.exception.SectionNotFoundException;

import java.util.*;

public class Configuration {

    private final Map<String, Section> data = new LinkedHashMap<>();

    public Configuration(Collection<Section> sections) {
        sections.forEach(section -> data.put(section.getName(), section));
    }

    public Configuration(Section... sections) {
        this(List.of(sections));
    }

    public Section section(String name) throws SectionNotFoundException {
        return Optional
                .ofNullable(data.get(name))
                .orElseThrow(() -> new SectionNotFoundException(name));
    }

    public void addSection(Section section) {
        data.put(section.getName(), section);
    }

    public void removeSection(String name) {
        data.remove(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "data=" + data +
                '}';
    }
}
