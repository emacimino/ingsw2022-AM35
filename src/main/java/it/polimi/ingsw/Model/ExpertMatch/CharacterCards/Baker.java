package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Baker extends CharacterCard {

    public Baker(Game game, String name) {
        super(game, name);
        setCost(2);
    }
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        usedBakerCard( match);
        resetCard();
    }

    private void usedBakerCard(ExpertMatch match) throws ExceptionGame{
        Player player = match.getPlayerFromWizard(getActiveWizard());
        Player captain = match.getCaptainTeamOfPlayer(player);
        match.setBakerEffect(match.getGame().getWizardFromPlayer(captain));
    }
}
