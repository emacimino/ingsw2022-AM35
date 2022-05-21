package it.polimi.ingsw.Server;

import java.util.Collection;

public class ClientsInMatch {
    private final Collection<ClientConnection> clientConnectionList;

    public ClientsInMatch(Collection<ClientConnection> clientConnectionList) {
        this.clientConnectionList = clientConnectionList;
    }

    public Collection<ClientConnection> getClientConnectionList() {
        return clientConnectionList;
    }

}
