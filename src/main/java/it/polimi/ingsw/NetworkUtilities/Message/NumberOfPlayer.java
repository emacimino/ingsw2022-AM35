package it.polimi.ingsw.NetworkUtilities.Message;

public class NumberOfPlayer extends Message{
    private static final long serialVersionUID = -8472880972033867050L;
    private final int numberOfPlayers;

    public NumberOfPlayer(String message, int numberOfPlayers) {
        super("Number Of players : " + numberOfPlayers , GameStateMessage.NUMBEROFPLAYERS);
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    @Override
    public String toString(){
        return "Number of Players" + numberOfPlayers ;
    }
}