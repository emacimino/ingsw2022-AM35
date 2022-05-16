package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;

public interface ClientInterface {
    public void sendMessage(Message message) throws IOException, ExceptionGame;

    public void readMessage() throws IOException;

    public void disconnect() throws IOException;
}
