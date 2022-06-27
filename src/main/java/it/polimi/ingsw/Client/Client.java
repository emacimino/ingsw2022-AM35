package it.polimi.ingsw.Client;

import it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.Observer.Observable;

/**
 * Client class used to send and read messages
 */
public abstract class Client extends Observable {
    /**
     * Create a message and send it to the server
     * @param message message to be sent
     */
    public abstract void sendMessage(Message message);

    /**
     * receive a message and elaborate it
     */
    public abstract void readMessage();

    /**
     * handle the disconnection
     */
    public abstract void disconnect();

    public abstract void enablePingPong(boolean enabled);

}
