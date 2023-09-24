package com.example.javafxdemo;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Create a ProgressBar
        ProgressBar progressBar = new ProgressBar(.2);
        progressBar.setPrefWidth(600);

        // Create TableView
        TableView<Course> tableView = new TableView<>();

        // Create columns
        TableColumn<Course, Color> colorColumn = new TableColumn<>("Course Color");
        TableColumn<Course, String> numberColumn = new TableColumn<>("Course Number");
        TableColumn<Course, String> nameColumn = new TableColumn<>("Course Name");
        TableColumn<Course, Void> filesColumn = new TableColumn<>("Course Files");

        // Set cell value factories
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Create a ColorPicker cell factory for the Color column
        colorColumn.setCellFactory(column -> new TableCell<>() {
            private final ColorPicker colorPicker = new ColorPicker();

            {
                colorPicker.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());
                    course.setColor(colorPicker.getValue());
                });
            }

            @Override
            protected void updateItem(Color item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    colorPicker.setValue(item);
                    setGraphic(colorPicker);
                }
            }
        });

        // Create an "Upload Files" button cell factory for the Course Files column
        filesColumn.setCellFactory(column -> new TableCell<>() {
            private final Button uploadButton = new Button("Upload Files");
            private final FileChooser fileChooser = new FileChooser();

            {
                HBox hbox = new HBox(uploadButton);
                hbox.setSpacing(5);
                setGraphic(hbox);

                uploadButton.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());
                    File selectedFile = fileChooser.showOpenDialog(primaryStage);
                    if (selectedFile != null) {
                        course.getFiles().addAll(selectedFile);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(uploadButton);
                }
            }

        });

        // Add columns to the TableView
        tableView.getColumns().addAll(colorColumn, numberColumn, nameColumn, filesColumn);

        // Add dummy data
        ObservableList<Course> courses = FXCollections.observableArrayList(
                new Course(Color.RED, "101", "Introduction to Java", new ArrayList<>()),
                new Course(Color.BLUE, "201", "Advanced Java Programming", new ArrayList<>())
                // Add more dummy data as needed
        );
        tableView.setItems(courses);

        // Create a VBox to hold the ProgressBar and TableView
        VBox vbox = new VBox(progressBar, tableView);
        vbox.setSpacing(10);

        // Create a BorderPane as the root of the Scene
        BorderPane root = new BorderPane();
        root.setTop(vbox);

        // Create a Scene with the BorderPane
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX TableView Example");
        primaryStage.show();

    }

    // Method to show a file chooser dialog
    private List<File> showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Files");
        return fileChooser.showOpenMultipleDialog(null);
    }

    // Course class to represent data
    public static class Course {
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
}
