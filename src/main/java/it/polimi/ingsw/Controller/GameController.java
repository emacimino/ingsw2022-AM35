package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Observer.MatchObserver;
import it.polimi.ingsw.Observer.Observable;

import java.util.ArrayList;
import java.util.List;

public class GameController extends Observable {
    private MatchObserver observer;
    private List<Match> matches = new ArrayList<>();

    public GameController(String username) {
        this.observer = new MatchObserver(username);
        super.addObserver(observer);

    }

    public void findAMatch(){
        if(super.getObservers().size()==1){
            matches.add(createAMatch());
        }
        else {
            for(Match match: matches){
                match.getPlayers().add(observer.)
            }
        }

    }

    public Match createAMatch(){
        int numbersOfPlayers = askHowManyPlayers();
        FactoryMatch createAMatch = new FactoryMatch();
        return createAMatch.newMatch(numbersOfPlayers);
    }
    //to implement
    private int askHowManyPlayers() {
        return ;
    }
}
