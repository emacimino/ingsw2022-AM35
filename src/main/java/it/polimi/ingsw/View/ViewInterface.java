package it.polimi.ingsw.View;

import it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

/**
 * Represent the methods that help the view to communicate
 */
public abstract class ViewInterface extends Observable implements Observer{
    /**
     * Update after an event
     * @param message message with the event
     */
    public abstract void update(Message message) ;

    /**
     * Message to be sent from view
     *
     * @param message message to be sent
     */
    public abstract void sendMessage(Message message);
}