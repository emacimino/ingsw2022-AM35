package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.io.Serial;
import java.io.Serializable;

/**Implements the effect from Character card
 * and uses InfluenceEffectCard interface
 */

public class Herbalist extends CharacterCard implements ProhibitionEffectCard, Serializable {
    @Serial
    private final static long serialVersionUID = -361716328708727964L;
    /**
     * Constructor of the class
     * @param basicMatch current match
     * @param name name of the card
     */
    public Herbalist(BasicMatch basicMatch, String name){
        super(basicMatch, name);
        setCost(2);
        setProhibitionPass(4);
    }

    /**
     * This method lets the player use the card
     * @param match the current match
     * @throws ExceptionGame if a move that is not permitted is made or a method fails to return a value
     */
    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        if(match.getActiveProhibitionCard() == null)
            match.setActiveProhibitionCard(this);
        useProhibitionEffect();
        this.cost++;
    }

    /**
     * This method is used to calculate the prohibition effect
     * @throws ExceptionGame if a move that is not permitted is made or a method fails to return a value
     */
    public void useProhibitionEffect() throws ExceptionGame{
        if(getProhibitionPass() > 0 ) {
            getArchipelagoEffected().setProhibition(true);
            setProhibitionPass(getProhibitionPass() - 1);
        }
        else throw new ExceptionGame("No more Prohibition pass on this card");
        resetCard();
    }

    /**
     * Resets a prohibition on an archipelago
     * @param archipelago targeted archipelago
     * @throws ExceptionGame if a move that is not permitted is made or a method fails to return a value
     */
    @Override
    public void resetProhibitionEffect(Archipelago archipelago) throws ExceptionGame {
        if(getProhibitionPass() <= 3 ) {
            archipelago.setProhibition(false);
            setProhibitionPass(getProhibitionPass() + 1);
        }
    }

    /**
     * This method gets the number of prohibition passes
     * @return an int representing how many prohibition passes are left
     */
    @Override
    public int getProhibitionPass() {
        return super.getProhibitionPass();
    }
}
