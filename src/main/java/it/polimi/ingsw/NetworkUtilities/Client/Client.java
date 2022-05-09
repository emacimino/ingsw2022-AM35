package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

public abstract class Client extends Observer {

    public abstract void sendAMessage(Message message);

    public abstract void readAMessage(Message message);

    public abstract void disconnect();
}
