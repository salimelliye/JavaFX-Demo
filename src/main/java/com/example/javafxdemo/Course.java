package com.example.javafxdemo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.List;

public class Course {
    private final ObjectProperty<Color> color;
    private final String number;
    private final String name;
    private final ObservableList<File> files;

    public Course(Color color, String number, String name, List<File> files) {
        this.color = new SimpleObjectProperty<>(color);
        this.number = number;
        this.name = name;
        this.files = FXCollections.observableArrayList(files);
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public Color getColor() {
        return color.get();
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public ObservableList<File> getFiles() {
        return files;
    }
}