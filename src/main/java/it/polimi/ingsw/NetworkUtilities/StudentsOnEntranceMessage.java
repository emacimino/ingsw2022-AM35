package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.io.Serial;
import java.util.Map;

/**
 * Class used to send a map of students in entrance
 */
public class StudentsOnEntranceMessage extends Message {

    @Serial
    private static final long serialVersionUID = 1505728531966785838L;
    private final Map<Integer, Student> students;

    /**
     * Construcot of the class
     * @param students a Map of students in entrance
     */
    public StudentsOnEntranceMessage(Map<Integer, Student> students) {
        this.students = students;
        setType(TypeMessage.STUDENTS_ON_ENTRANCE);
    }

    /**
     * Method that returns the map of students contained in the message
     * @return a map of students with an integer key
     */
    public Map<Integer, Student> getStudents() {
        return students;
    }
}
