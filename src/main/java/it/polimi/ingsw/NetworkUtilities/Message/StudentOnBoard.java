package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.List;

public class StudentOnBoard extends Message{
    private static final long serialVersionUID = 3712768545549517262L;

    public StudentOnBoard(String username, List<Student> students){
        super(username, students, GameStateMessage.STUDENTONBOARD);
    }

    public StudentOnBoard(List<String> studentsToBoardString) {
        super();
    }
}
