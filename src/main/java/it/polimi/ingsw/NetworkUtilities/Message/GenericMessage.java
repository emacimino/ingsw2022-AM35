package it.polimi.ingsw.NetworkUtilities.Message;

public class GenericMessage extends Message{
    public GenericMessage(String nickname, Object content, GameStateMessage type) {
        super(nickname, content, type);
    }

    @Override
    public Object getContent() {
        return super.getContent();
    }

    @Override
    public GameStateMessage getType() {
        return super.getType();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
