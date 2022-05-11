package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observer;

public class TurnController implements Observer {
    private final BasicMatch match;

    public TurnController(BasicMatch match) {
        this.match = match;
    }


    @Override
    public void update(Message message) {

    }
}
