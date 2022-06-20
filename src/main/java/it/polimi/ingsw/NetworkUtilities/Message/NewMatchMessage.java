package it.polimi.ingsw.NetworkUtilities.Message;

public class NewMatchMessage extends Message{
    public NewMatchMessage() {
        setType(TypeMessage.NEW_MATCH);
    }
}
