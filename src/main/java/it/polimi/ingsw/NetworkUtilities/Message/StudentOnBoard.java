package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.List;

public class StudentOnBoard extends Message{
    private static final long serialVersionUID = 3712768545549517262L;


    public StudentOnBoard(String username, Student student, GameStateMessage studentOnBoard) {
        super(username,student,GameStateMessage.STUDENT_ON_BOARD);
    }
}
