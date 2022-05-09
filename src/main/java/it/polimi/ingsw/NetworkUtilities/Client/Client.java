package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

public interface Client {

    public void sendMessage(Message message);

    public void readMessage(Message message);

    public void disconnect();

}
