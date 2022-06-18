package it.polimi.ingsw.Server;

import java.util.Collection;

public class ClientsInMatch {
    private final Collection<ClientConnection> clientConnectionList;

    public ClientsInMatch(int matchCounter, Collection<ClientConnection> clientConnectionList) {
        this.clientConnectionList = clientConnectionList;
        for(ClientConnection c : clientConnectionList){
            ((SocketClientConnection)c).setNumOfMatch(matchCounter);
        }
    }

    public Collection<ClientConnection> getClientConnectionList() {
        return clientConnectionList;
    }

}
