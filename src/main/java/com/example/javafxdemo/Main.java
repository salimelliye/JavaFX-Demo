package com.example.javafxdemo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class Main extends Application {
    private TableView<Course> tableView;
    private ListView<Person> listView;
    private Pagination pagination;
    private ObservableList<Person> people;
    private ObservableList<Course> courses;

    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a ProgressBar
        ProgressBar progressBar = new ProgressBar(.2);
        progressBar.setPrefWidth(600);

        // LISTVIEW
        // Create Person objects
        Set<String> nourResponsibilities = new HashSet<>(Arrays.asList("List View", "Tree View", "Date Picker"));
        Set<String> kevinResponsibilities = new HashSet<>(Arrays.asList("Table View", "Color Picker", "File Upload"));
        Set<String> salimResponsibilities = new HashSet<>(Arrays.asList("Pagination", "HTML Manipulation", "Progress Bar"));

        Person nour = new Person("Nour", nourResponsibilities);
        Person kevin = new Person("Kevin", kevinResponsibilities);
        Person salim = new Person("Salim", salimResponsibilities);

        // Populate a ListView and add Person objects to it
        listView = new ListView<>();
        ObservableList<Person> people = FXCollections.observableArrayList(nour, kevin, salim);
        listView.setItems(people);


        // TABLEVIEW
        tableView = new TableView<>();
        // Create columns for the TableView
        TableColumn<Course, Color> colorColumn = new TableColumn<>("Course Color");
        TableColumn<Course, String> numberColumn = new TableColumn<>("Course Number");
        TableColumn<Course, String> nameColumn = new TableColumn<>("Course Name");
        TableColumn<Course, Void> filesColumn = new TableColumn<>("Course Files");

        // Set cell value factories for TableView
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

        // Add dummy data for TableView
        ObservableList<Course> courses = FXCollections.observableArrayList(
                new Course(Color.RED, "101", "Introduction to Java", new ArrayList<>()),
                new Course(Color.BLUE, "201", "Advanced Java Programming", new ArrayList<>())
                // Add more dummy data as needed
        );
        tableView.setItems(courses);

        // PAGINATION
        // Create Pagination with 3 pages (updated)
        pagination = new Pagination(3);
        pagination.setPageFactory(this::createPage);


        BorderPane root = new BorderPane();
        root.setTop(progressBar);
        root.setCenter(pagination);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Presentation");
        primaryStage.show();
    }

    private VBox createPage(int pageIndex) {
        VBox pageContent = new VBox();
        pageContent.setSpacing(10);

        if (pageIndex == 0) {
            // Page 1: ListView
            pageContent.getChildren().addAll(listView);
        } else if (pageIndex == 1) {
            // Page 2: TableView
            pageContent.getChildren().addAll(tableView);
        } else if (pageIndex == 2) {
            // Page 3: HTMLEditor for HTML Manipulation
            HTMLEditor htmlEditor = new HTMLEditor();
            pageContent.getChildren().addAll(htmlEditor);
        }

        return pageContent;
    }
}
