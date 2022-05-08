package it.polimi.ingsw.ClientServerNetwork.Server.Message;

public class PlayerMessages {
    private final String playerCreated = "A new players ha been created";
    private final String playersAddedToGame = "List of players had been selected";

    public String notifyPlayerCreated() {
        return playerCreated;
    }

    public String notifyPlayersAddedToGame() {
        return playersAddedToGame;
    }
}
