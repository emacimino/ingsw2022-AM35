package it.polimi.ingsw.NetworkUtilities.Message;

public class MessageFromServer extends Message {
    private static final long serialVersionUID = -1035422453835737473L;

    public MessageFromServer(String server,GameStateMessage gameStateMessage) {
        super(server,gameStateMessage);
    }
}
