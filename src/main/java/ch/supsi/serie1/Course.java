package ch.supsi.serie1;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private List<Student> students;

    public Course(String name){
        this.name = name;
        students = new ArrayList<>();
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void print(){
        System.out.println("Corso " + name);
        students.stream().forEach(System.out::println);
        System.out.println();
    }

    @Override
    public String toString() {
        return "" + name;
    }
}
