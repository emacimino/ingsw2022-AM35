package it.polimi.ingsw;

import java.util.*;

public class Cloud {
    private Collection<Student> StudentInCloud = new HashSet<Student>();

    public void setStudentInCloud(Collection<Student> studentInCloud) {
        StudentInCloud = studentInCloud;
    }

    public Collection<Student> getStudentInCloud() {
        return StudentInCloud;
    }

    public Cloud(Collection<Student> studentInCloud) {
        StudentInCloud = studentInCloud;
    }
}
