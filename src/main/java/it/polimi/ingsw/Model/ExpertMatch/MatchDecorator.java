package it.polimi.ingsw.Model.ExpertMatch;

public abstract class MatchDecorator {

    protected Match match;

    public ExpertMatchDecorator(Match match) {
        this.match = match;
    }
}
