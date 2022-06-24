package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Used to communicate how many steps can do motherNature
 */
public class AskToMoveMotherNatureMessage extends Message{

    @Serial
    private static final long serialVersionUID = -298606158447402860L;
    private final int numOfSteps;
    private final String message;

    /**
     * Constructor of the class
     * @param numOfSteps numOfSteps allowed
     */
    public AskToMoveMotherNatureMessage(int numOfSteps) {
        this.numOfSteps = numOfSteps;
        setType(TypeMessage.ASK_MOVE_MOTHER_NATURE);
        message = "Please, move Mother Nature, you allowed to do at most "+numOfSteps+" steps ";
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }

    public String getMessage() {
        return message;
    }
}