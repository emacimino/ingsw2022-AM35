package it.polimi.ingsw.NetworkUtilities.Message;

public class GenericMessage extends Message{
    private final Object content;
    public GenericMessage(Object content) {
        this.content = content;
        setType(GameStateMessage.GENERIC_MESSAGE);
    }

    public Object getContent() {
        return content;
    }
}
