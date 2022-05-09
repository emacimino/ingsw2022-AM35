package it.polimi.ingsw.NetworkUtilities.Server;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;

public interface ClientHandlerInterface {

    boolean isConnected();

    void disconnect() throws IOException;

    void sendMessage(Message message);
}
