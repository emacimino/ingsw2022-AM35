package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

public class Magician extends CharacterCard implements MotherNatureEffectCard{
    public Magician(BasicMatch basicMatch, String name){
        super(basicMatch, name);
        setCost(1);
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        match.setActiveMotherNatureCard(this);
        this.cost++;
    }

    public void effectMotherNatureCard(Player player, Archipelago archipelago) throws ExceptionGame{
        if (getBasicMatch().getGame().getArchipelagos().contains(archipelago)) {
            int positionMotherNature = getBasicMatch().getPositionOfMotherNature();
            int positionOfArchipelago = getBasicMatch().getGame().getArchipelagos().indexOf(archipelago);
            int stepsAllowed = getBasicMatch().getGame().getWizardFromPlayer(player).getRoundAssistantsCard().getStep();
            int numberOfArchipelagos = getBasicMatch().getGame().getArchipelagos().size();
            int numberOfSteps = ((positionOfArchipelago + numberOfArchipelagos) - positionMotherNature) % numberOfArchipelagos;
            if(getBasicMatch().getGame().getWizardFromPlayer(player).equals(getActiveWizard())){
                    if ((numberOfSteps >0 && numberOfSteps <= stepsAllowed + 2 )|| (positionMotherNature == positionOfArchipelago && numberOfArchipelagos <= stepsAllowed + 2)) {
                        getBasicMatch().getGame().getArchipelagos().get(positionMotherNature).setMotherNaturePresence(false);
                        getBasicMatch().getGame().getMotherNature().setPosition(positionOfArchipelago);
                        archipelago.setMotherNaturePresence(true);
                    }else
                        throw new ExceptionGame("This archipelago is not allowed by the assistant's card");
                }else
                    throw new ExceptionGame("Wizard does not have an Assistant's card");
        }else
            throw new ExceptionGame("This archipelago is not part of the game");

        }

    @Override
    public void resetCard() {
        super.resetCard();
    }
}
