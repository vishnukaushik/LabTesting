package org.example.sample;

public class Student {
    private final String name;
    private final String roll;

    public Student(String name, String roll) {
        this.name = name;
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }
}
