package it.polimi.ingsw;

import java.util.*;

public class StudentBag {
    private Collection<Student> StudentInBag = new HashSet<Student>(){};

    public StudentBag(Collection<Student> studentInBag) {
        StudentInBag = studentInBag;
    }

    public Collection<Student> getStudentInBag() {
        return StudentInBag;
    }

    public void setStudentInBag(Collection<Student> studentInBag) {
        StudentInBag = studentInBag;
    }

    public Student DrawStudent(){};

    public int GetNumberOfStudents(){};

}
