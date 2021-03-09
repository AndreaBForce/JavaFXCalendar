package ch.supsi.serie1;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Analisi"));
        courses.add(new Course("Algebra"));
        courses.add(new Course("Fisica"));

        courses.get(0).addStudent(new Student("Paolo", "Frassini"));
        courses.get(0).addStudent(new Student("Franco", "Giorgino"));
        courses.get(0).addStudent(new Student("Simon", "Wave"));
        courses.get(1).addStudent(new Student("Simon", "Wave"));
        courses.get(2).addStudent(new Student("Simon", "Wave"));
        courses.get(2).addStudent(new Student("Giuseppe", "Borsello"));

        courses.stream().forEach(c -> c.print());
    }
}
