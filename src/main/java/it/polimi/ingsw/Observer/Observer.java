package it.polimi.ingsw.Observer;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

public interface Observer {
    void update(Message message);
}
