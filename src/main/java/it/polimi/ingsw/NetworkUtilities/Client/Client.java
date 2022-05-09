package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observable;

public abstract class Client extends Observable {

    public abstract void sendAMessage(Message message);

    public abstract void readAMessage(Message message);

    public abstract void disconnect();
}
