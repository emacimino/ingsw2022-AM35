package it.polimi.ingsw.Server;


import it.polimi.ingsw.NetworkUtilities.Message;

public interface ClientConnection {

    void closeConnection();

    void asyncSendMessage(Message message);

    Integer getNumOfMatch();
}
