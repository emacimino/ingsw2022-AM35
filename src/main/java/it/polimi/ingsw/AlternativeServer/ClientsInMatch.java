package it.polimi.ingsw.AlternativeServer;

import java.util.Collection;
import java.util.List;

public class ClientsInMatch {
    private final Collection<ClientConnection> clientConnectionList;

    public ClientsInMatch(Collection<ClientConnection> clientConnectionList) {
        this.clientConnectionList = clientConnectionList;
    }

    public Collection<ClientConnection> getClientConnectionList() {
        return clientConnectionList;
    }

}
