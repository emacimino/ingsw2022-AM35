package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to request the movement of students
 */
public class AskToMoveStudents extends Message{
    @Serial
    private static final long serialVersionUID = 2236354021060235326L;

    /**
     * Constructor of the class
     */
    public AskToMoveStudents() {
        setType(TypeMessage.ASK_TO_MOVE_STUDENT);
        setMessage("Move your students!");
    }


}
