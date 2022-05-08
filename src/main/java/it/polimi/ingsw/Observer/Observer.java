package it.polimi.ingsw.Observer;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

public interface Observer {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);
    
    void notifyObserver(Message message);

    void update(Message message);
}
