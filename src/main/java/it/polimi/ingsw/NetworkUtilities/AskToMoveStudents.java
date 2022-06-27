package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;
/**
 * Asking the view to move students
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
