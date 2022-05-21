package it.polimi.ingsw.Server;


import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observer;

public interface ClientConnection {

    void closeConnection();

    void asyncSendMessage(Message message);

    Integer getNumOfMatch();
}
