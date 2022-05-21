package it.polimi.ingsw.NetworkUtilities.Message;

public class AskToMoveMotherNatureMessage extends Message{

    private static final long serialVersionUID = -298606158447402860L;
    private final int numOfSteps;

    public AskToMoveMotherNatureMessage(int numOfSteps) {
        this.numOfSteps = numOfSteps;
        setType(TypeMessage.ASK_MOVE_MOTHER_NATURE);
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }
}