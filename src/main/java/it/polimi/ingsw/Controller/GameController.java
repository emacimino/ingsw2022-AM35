package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.NetworkUtilities.Message.TypeOfMessage;
import it.polimi.ingsw.View.ActualView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GameController implements Serializable {

    private Game game;
    private Map<String, ActualView> viewMap = new HashMap<>();

    private TypeOfMessage typeOfMessage;

    public GameController gameController(){

    }


}
