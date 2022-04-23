package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Match;

public abstract class MatchDecorator extends Match{

    protected Match match;
    String requireExpertMatch = "ExpertMatch";

    public ExpertMatch ExpertMatchDecorator(Match match, String expertMatch) throws ExceptionGame{
        if(expertMatch.equals(requireExpertMatch))
            return new ExpertMatch(match);
        else
            throw new ExceptionGame("please require a correct type of match");
    }
}
