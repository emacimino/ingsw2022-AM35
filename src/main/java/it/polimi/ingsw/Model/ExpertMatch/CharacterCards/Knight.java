package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Knight extends CharacterCard {

    public Knight(Game game, String name) {
        super(game, name);
        setCost(2);
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame{
        super.useCard(match);
        usedKnightCard(match);
        resetCard();
    }

    private void usedKnightCard(ExpertMatch match) {
        match.setKnightEffect(true);
    }

}
