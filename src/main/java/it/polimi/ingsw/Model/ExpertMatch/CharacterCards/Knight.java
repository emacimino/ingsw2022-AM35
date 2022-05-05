package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Knight extends CharacterCard implements InfluenceEffectCard{

    public Knight(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(2);
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame{
        super.useCard(match);
        match.setActiveInfluenceCard(this);
        this.cost++;
    }


    public int calculateEffectInfluence(Wizard wizard, Archipelago archipelago,  int normalInfluence) throws ExceptionGame{
        Player activePlayer = getBasicMatch().getPlayerFromWizard(getActiveWizard());
        Player captainOfActivePlayer = getBasicMatch().getCaptainTeamOfPlayer(activePlayer);
        Wizard wizardCaptain = getBasicMatch().getGame().getWizardFromPlayer(captainOfActivePlayer);
        int add = 0;
        if(wizard.equals(wizardCaptain)){
            add = 2;
        }
        return normalInfluence + add;
    }

    @Override
    public void resetCard() {
        super.resetCard();
    }
}
