package it.polimi.ingsw.NetworkUtilities.Message;

public class YourTurnMessage extends Message{
    private static final long serialVersionUID = -6588876372077057029L;
    private final String message = "It's your turn!";

    public YourTurnMessage() {
        setType(TypeMessage.YOUR_TURN);
    }

    public String getContent() {
        return message;
    }
}


