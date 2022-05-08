package it.polimi.ingsw.Observer;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.util.ArrayList;
import java.util.List;

public class Observable implements Observer {

    List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(Message message) {
        for (Observer observerForLoop: observers)
            observerForLoop.update(message);
    }


    public void update(Message message) {

    }
}
