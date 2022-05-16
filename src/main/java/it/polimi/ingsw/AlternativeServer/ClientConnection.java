package it.polimi.ingsw.AlternativeServer;


import it.polimi.ingsw.Observer.Observer;

public interface ClientConnection {

    void closeConnection();

    void addObserver(Observer observer);

    void asyncSend(Object message);

    Integer getNumOfMatch();
}
