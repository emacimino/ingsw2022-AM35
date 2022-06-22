package it.polimi.ingsw.Client;

import it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.Observer.Observable;

public abstract class Client extends Observable {
    public abstract void sendMessage(Message message);

    public abstract void readMessage();

    public abstract void disconnect();

    public abstract void enablePingPong(boolean enabled);

}
