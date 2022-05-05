package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Herbalist extends CharacterCard implements ProhibitionEffectCard{

    public Herbalist(BasicMatch basicMatch, String name){
        super(basicMatch, name);
        setCost(2);
        setProhibitionPass(4);
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        if(match.getActiveProhibitionCard() == null)
            match.setActiveProhibitionCard(this);
        useProhibitionEffect();
        this.cost++;
    }

    public void useProhibitionEffect() throws ExceptionGame{
        if(getProhibitionPass() > 0 ) {
            getArchipelagoEffected().setProhibition(true);
            setProhibitionPass(getProhibitionPass() - 1);
        }
        else throw new ExceptionGame("No more Prohibition pass on this card");
        resetCard();
    }

    @Override
    public void resetAProhibitionEffect(Archipelago archipelago) throws ExceptionGame {
        if(getProhibitionPass() <= getProhibitionPass() ) {
            archipelago.setProhibition(false);
            setProhibitionPass(getProhibitionPass() + 1);
        }
    }

    @Override
    public int getProhibitionPass() {
        return super.getProhibitionPass();
    }
}
