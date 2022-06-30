package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to request movement of mother nature
 */
public class AskToMoveMotherNatureMessage extends Message{

    @Serial
    private static final long serialVersionUID = -298606158447402860L;
    private final int numOfSteps;
    private final String message;

    /**
     * Constructor of the class
     * @param numOfSteps number of steps to move mother nature by
     */
    public AskToMoveMotherNatureMessage(int numOfSteps) {
        this.numOfSteps = numOfSteps;
        setType(TypeMessage.ASK_MOVE_MOTHER_NATURE);
        message = "Please, move Mother Nature, you allowed to do at most "+numOfSteps+" steps ";
    }

    /**
     * Getter for num of steps
     * @return num of steps
     */
    public int getNumOfSteps() {
        return numOfSteps;
    }

    /**
     * Method that return the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}