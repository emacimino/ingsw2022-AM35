package it.polimi.ingsw.NetworkUtilities.Message;

public class AskToMoveStudents extends Message{
    private static final long serialVersionUID = 2236354021060235326L;

    public AskToMoveStudents() {
        setType(TypeMessage.ASK_TO_MOVE_STUDENT);
    }
}
