package it.polimi.ingsw.NetworkUtilities.Message;

public class NumberOfPlayer extends Message{
    private static final long serialVersionUID = -8472880972033867050L;
    private final int numberOfPlayers;

    public NumberOfPlayer(String nickname, String message, int numberOfPlayers) {
        super(nickname, "Number Of players : " + numberOfPlayers , GameStateMessage.NUMBER_OF_PLAYERS);
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
