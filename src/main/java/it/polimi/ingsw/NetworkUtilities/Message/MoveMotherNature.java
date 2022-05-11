package it.polimi.ingsw.NetworkUtilities.Message;

public class MoveMotherNature extends Message{
    private static final long serialVersionUID = 5541410460045942543L;

    public MoveMotherNature(String username, int steps){
        super(username, steps, GameStateMessage.MOVEMOTHERNATURE);
    }

}
