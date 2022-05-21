package it.polimi.ingsw.NetworkUtilities.Message;

public class yourTurnMessage extends Message{
    private static final long serialVersionUID = -6588876372077057029L;
    private final String message = "It's your turn!";

    public yourTurnMessage() {
        setType(TypeMessage.YOUR_TURN);
    }
}


