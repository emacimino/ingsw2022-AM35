package it.polimi.ingsw.Observer;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObserver(Message message) {
        for (Observer observerForLoop: observers)
            observerForLoop.update(message);
    }

}
