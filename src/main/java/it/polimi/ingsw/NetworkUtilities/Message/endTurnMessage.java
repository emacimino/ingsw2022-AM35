package it.polimi.ingsw.NetworkUtilities.Message;

public class endTurnMessage extends Message{
    private static final long serialVersionUID = 3002290940031968259L;
    private final String message = "Please, wait for your turn";

    public endTurnMessage() {
        setType(TypeMessage.END_OF_TURN);
    }
}
