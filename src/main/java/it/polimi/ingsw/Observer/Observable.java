package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.Exception.ExceptionGame;

import java.util.ArrayList;
import java.util.List;

public class Observable{

    private final List<Observer> observers = new ArrayList<>();

    /**
     * Adds an observer.
     *
     * @param observer is the observer to be added.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer.
     *
     * @param observer is the observer to be removed.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     *
     * @param message is the message to be passed to the observers.
     */
    protected void notifyObserver(Object message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }


}
