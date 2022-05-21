package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.List;

public class StudentsListMessage extends Message {

    private static final long serialVersionUID = 1505728531966785838L;
    private final List<Student> students;

    public StudentsListMessage(List<Student> students) {
        this.students = students;
    }
}
