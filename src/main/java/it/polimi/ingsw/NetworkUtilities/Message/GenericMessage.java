package it.polimi.ingsw.NetworkUtilities.Message;

public class GenericMessage extends Message{
    private static final long serialVersionUID = 3876787195775446713L;
    private final Object content;
    public GenericMessage(Object content) {
        this.content = content;
        setType(TypeMessage.GENERIC_MESSAGE);
    }

    public Object getContent() {
        return content;
    }
}
