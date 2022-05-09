package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewInterface;

import java.io.Serializable;
import java.util.Map;

public class GameController implements Serializable,Observer {

    private static final long serialVersionUID = -3248504358856237848L;
    private BasicMatch match;
    private Map<String, ViewInterface> viewMap;

    public GameController(){
        match = setAMatch();
    }

    private BasicMatch setAMatch() {
        //return new FactoryMatch().newMatch()
    }


    @Override
    public void update(Message message) {

    }
}
