package ch.supsi.serie1;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
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

        courses.stream().forEach(c -> c.print());
    }
}
