package ch.supsi.serie1;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;

public class ApplicationFX extends Application{

    public List<Course> createList(){
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Analisi"));
        courses.add(new Course("Algebra"));
        courses.add(new Course("Fisica"));

        courses.get(0).addStudent(new Student("Paolo", "Frassini","05-02-2000"));
        courses.get(0).addStudent(new Student("Franco", "Giorgino","15-05-1997"));
        courses.get(0).addStudent(new Student("Simon", "Wave","24-08-1999"));
        courses.get(1).addStudent(new Student("Simon", "Wave","08-11-2000"));
        courses.get(2).addStudent(new Student("Simon", "Wave","24-12-1998"));
        courses.get(2).addStudent(new Student("Giuseppe", "Borsello","23-06-1996"));

        return courses;
    }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Serie2");

        List<Course> courses = createList();

        ListView<Course> courseListView = new ListView<>();
        courseListView.getItems().addAll(courses);

        ListView<Student> studentListView = new ListView<>();

        Pane coursePane = new Pane(courseListView,studentListView);

        studentListView.setLayoutX(200);
        studentListView.setMaxSize(200,300);
        courseListView.setMaxSize(200,300);

        courseListView.setStyle("-fx-selection-bar: blue;");
        studentListView.setStyle("-fx-background-color: green;");

        TextArea logText = new TextArea();
        Pane logPanel = new Pane(logText);

        logText.setMaxSize(400, 200);

        BorderPane root = new BorderPane();
        root.setCenter(coursePane);
        root.setBottom(logPanel);

        stage.setScene(new Scene(root,400,300));
        stage.show();

        courseListView.getSelectionModel().selectedItemProperty().addListener((observableValue,course,t1) -> {
            if(!studentListView.getItems().isEmpty()){
                studentListView.getItems().removeAll(course.getStudents());
            }

            studentListView.getItems().addAll(t1.getStudents());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            logText.appendText(formatter.format(date) + " " +t1.toString() + "\n");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
