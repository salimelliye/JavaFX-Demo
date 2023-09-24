package com.example.javafxdemo;

import java.util.Set;

public class Person {
    private final String name;
    private final Set<String> responsibilities;

    public Person(String name, Set<String> responsibilities) {
        this.name = name;
        this.responsibilities = responsibilities;
    }

    public String getName() {
        return name;
    }

    public Set<String> getResponsibilities() {
        return responsibilities;
    }

    @Override
    public String toString() {
        return name + " - Responsibilities: " + responsibilities.toString();
    }
}
