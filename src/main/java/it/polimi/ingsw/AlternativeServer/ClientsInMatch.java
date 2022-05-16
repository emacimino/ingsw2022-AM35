package it.polimi.ingsw.AlternativeServer;

import java.util.List;

public class ClientsInMatch {
    private final boolean isExpert;
    private final int numberOfPlayers;
    private final List<ClientConnection> clientConnectionList;

    public ClientsInMatch(boolean isExpert, int numberOfPlayers, List<ClientConnection> clientConnectionList) {
        this.isExpert = isExpert;
        this.numberOfPlayers = numberOfPlayers;
        this.clientConnectionList = clientConnectionList;
    }

    public List<ClientConnection> getClientConnectionList() {
        return clientConnectionList;
    }

    public boolean isExpert() {
        return isExpert;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
