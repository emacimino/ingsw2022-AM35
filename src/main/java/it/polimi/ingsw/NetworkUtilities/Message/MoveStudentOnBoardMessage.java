package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsMembers.Student;

public class MoveStudentOnBoardMessage extends Message{
    private static final long serialVersionUID = 3712768545549517262L;
    private final String username;
    private final Student student;

    public MoveStudentOnBoardMessage(String username, Student student) {
        this.username = username;
        this.student = student;
        setType(GameStateMessage.STUDENT_ON_BOARD);
    }

    public String getUsername() {
        return username;
    }

    public Student getStudent() {
        return student;
    }
}
