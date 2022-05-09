package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatchFourPlayers;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatchThreePlayers;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class GameController implements Serializable,Observer {

    private static final long serialVersionUID = -3248504358856237848L;
    private BasicMatch match;
    private Map<String, ViewInterface> viewMap;

    public GameController(int numOfPlayers) throws ExceptionGame {
        if(numOfPlayers == 2)
            match = new BasicMatch();
        else if(numOfPlayers == 3)
            match = new BasicMatchThreePlayers();
        else if(numOfPlayers == 4)
            match = new BasicMatchFourPlayers();
        match.setGame((ArrayList)viewMap.keySet());
    }



    @Override
    public void update(Message message) {

    }
}
