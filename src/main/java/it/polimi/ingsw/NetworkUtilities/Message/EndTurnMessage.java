package it.polimi.ingsw.NetworkUtilities.Message;

public class EndTurnMessage extends Message{
    private static final long serialVersionUID = 3002290940031968259L;
    private final String message = "Please, wait for your turn";

    public EndTurnMessage() {
        setType(TypeMessage.END_OF_TURN);
    }

    public String getContent() {
        return message;
    }
}
