package it.polimi.ingsw.NetworkUtilities.Message;

public class ErrorMessage extends Message {
    public ErrorMessage(Object o, String message_not_sent) {
        super(null,message_not_sent,GameStateMessage.ERROR);
    }
}
