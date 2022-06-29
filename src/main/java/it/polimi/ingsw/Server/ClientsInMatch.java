package it.polimi.ingsw.Server;

import java.util.Collection;

/**
 * Set a group of client for every match that has to be created
 */
public class ClientsInMatch {
    private final Collection<ClientConnection> clientConnectionList;

    /**
     * Constructor of the Class
     * @param matchCounter number of the match
     * @param clientConnectionList list of clients that belongs to this match
     */
    public ClientsInMatch(int matchCounter, Collection<ClientConnection> clientConnectionList) {
        this.clientConnectionList = clientConnectionList;
        for(ClientConnection c : clientConnectionList){
            ((SocketClientConnection)c).setNumOfMatch(matchCounter);
        }
    }

    /**
     * Getter for client collection list
     * @return client connection list
     */
    public Collection<ClientConnection> getClientConnectionList() {
        return clientConnectionList;
    }

}
