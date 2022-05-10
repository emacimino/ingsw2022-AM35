package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;

public interface Client {

    public void sendMessage(Message message) throws IOException;

    public void readMessage() throws IOException;

    public void disconnect() throws IOException;

}
