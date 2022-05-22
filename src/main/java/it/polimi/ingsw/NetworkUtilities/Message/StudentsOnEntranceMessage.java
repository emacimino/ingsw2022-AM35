package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.Map;

public class StudentsOnEntranceMessage extends Message {

    private static final long serialVersionUID = 1505728531966785838L;
    private final Map<Integer, Student> students;

    public StudentsOnEntranceMessage(Map<Integer, Student> students) {
        this.students = students;
        setType(TypeMessage.STUDENTS_ON_ENTRANCE);
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }
}
