package it.polimi.ingsw.AlternativeServer;

import java.util.List;

public class ClientsInMatch {
    private final List<SocketClientConnection> clientConnectionList;

    public ClientsInMatch( List<SocketClientConnection> clientConnectionList) {
        this.clientConnectionList = clientConnectionList;
    }

    public List<SocketClientConnection> getClientConnectionList() {
        return clientConnectionList;
    }

}
