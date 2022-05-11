package it.polimi.ingsw.NetworkUtilities.Message;

public class Pong extends Message{
    private static final long serialVersionUID = 8550122873407340272L;
    private final String ping = "Pong";

    public Pong(String nickname){
        super(nickname, "Pong", GameStateMessage.PONG);
    }

}