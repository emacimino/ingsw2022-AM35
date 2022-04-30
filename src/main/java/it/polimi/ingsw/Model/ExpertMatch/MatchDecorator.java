package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.FactoryMatch.MatchInterface;

public abstract class MatchDecorator implements MatchInterface {
    String requireExpertMatch = "ExpertMatch";

    public ExpertMatch ExpertMatchDecorator(Match match, String expertMatch) throws ExceptionGame{
        if(expertMatch.equals(requireExpertMatch))
            return new ExpertMatch(match);
        else
            throw new ExceptionGame("please require a correct type of match");
    }
}
