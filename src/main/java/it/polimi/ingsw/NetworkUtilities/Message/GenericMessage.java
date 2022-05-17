package it.polimi.ingsw.NetworkUtilities.Message;

public class GenericMessage extends Message{
    public GenericMessage(String message) {
        setType(GameStateMessage.GENERIC_MESSAGE);
    }
}
