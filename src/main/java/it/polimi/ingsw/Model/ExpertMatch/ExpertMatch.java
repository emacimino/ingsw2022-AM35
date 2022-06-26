package it.polimi.ingsw.Model.ExpertMatch;


import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.*;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.CharacterCardInGameMessage;
import it.polimi.ingsw.NetworkUtilities.CurrentGameMessage;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class extends the MatchDecorator to implements the ExpertMode
 */
public class ExpertMatch extends MatchDecorator implements Serializable {
    @Serial
    private final static long serialVersionUID = -8269485069293820321L;
    private final transient DeckCharacterCard deckCharacterCard;
    private Map<String, CharacterCard> characterCardInMatchMap = new HashMap<>();
    private InfluenceEffectCard activeInfluenceCard;
    private MotherNatureEffectCard activeMotherNatureCard;
    private ProhibitionEffectCard activeProhibitionCard;



    /**
     * Constructor of ExpertMatch
     *
     * @param basicMatch is the match that has to be modified to play in ExpertMode
     */
    public ExpertMatch(BasicMatch basicMatch) {
        super(basicMatch); //match della classe MatchDecorator
        deckCharacterCard = new DeckCharacterCard();
        activeInfluenceCard = null;
        activeMotherNatureCard = null;

    }

    /**
     * Overrides the setGame method from basicMatch
     * @param players a list of players
     * @throws ExceptionGame if a method call throws an exception
     */
    @Override
    public void setGame(List<Player> players) throws ExceptionGame {
        basicMatch.addAllObserver(this.getObservers());
        basicMatch.setGame(players);
        setFirstCoin();
        characterCardInMatchMap = deckCharacterCard.drawCharacterCard(basicMatch);

        notifyObserver(new CharacterCardInGameMessage(characterCardInMatchMap));

    }

    /**
     * The override add a check to the number of student that are placed on the board when a student is moved
     *
     * @param player  is the player which moves the student
     * @param student is the student
     */
    @Override
    public void moveStudentOnBoard(Player player, Student student) throws ExceptionGame {
        basicMatch.moveStudentOnBoard(player, student);
        if (basicMatch.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size() == 3 ||
                basicMatch.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size() == 6 ||
                basicMatch.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size() == 9) {
                basicMatch.getGame().getWizardFromPlayer(player).addACoin();
        }
    }

    /**
     * Add a coin to every wizard when we set the Match to Expert Mode
     */
    private void setFirstCoin() {
        for (Wizard wizard : basicMatch.getGame().getWizards())
            wizard.addACoin();
    }

    /**
     * Get method returning a map of character cards
     * @return a map of character cards
     */
    public Map<String, CharacterCard> getCharactersForThisGame() {
        return characterCardInMatchMap;
    }

    /**
     * Method used to move Mother Nature
     * @param player      is the player that move Mother Nature
     * @param archipelago is the archipelago where the player wants to place Mother Nature
     * @throws ExceptionGame is thrown if the tower can't be built
     */
    @Override
    public void moveMotherNature(Player player, Archipelago archipelago) throws ExceptionGame {
        if(activeProhibitionCard!=null)
            if(archipelago.isProhibition()){
                basicMatch.getGame().placeMotherNature(player, archipelago);
                activeProhibitionCard.resetProhibitionEffect(archipelago);
                basicMatch.checkVictory(player);
                notifyObserver(new CurrentGameMessage(getGame()));
                return;
            }

        if(activeMotherNatureCard != null){
            activeMotherNatureCard.effectMotherNatureCard(player, archipelago);
        }else {
            basicMatch.getGame().placeMotherNature(player, archipelago);
        }

        try {
            buildTower(player, archipelago);
            basicMatch.lookUpArchipelago(archipelago);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        } finally {
            basicMatch.checkVictory(player);
        }

        if(activeInfluenceCard != null) {
            activeInfluenceCard.resetCard();
            activeInfluenceCard = null;
        }
        if(activeMotherNatureCard != null) {
            activeMotherNatureCard.resetCard();
            activeMotherNatureCard = null;
        }
        notifyObserver(new CurrentGameMessage(getGame()));
    }

    /**
     * Method used to build towers
     * @param player      is the player
     * @param archipelago is the archipelago
     * @throws ExceptionGame if a tower can't be built
     */
    public void buildTower(Player player, Archipelago archipelago) throws ExceptionGame {
        boolean isMostInfluence = true;
        for(Player p : getRivals(player)){
            if(getWizardInfluenceInArchipelago(p, archipelago) >= getWizardInfluenceInArchipelago(player, archipelago)) {
                isMostInfluence = false;
            }
        }
        if(isMostInfluence) {
            basicMatch.getGame().buildTower( getGame().getWizardFromPlayer(basicMatch.getCaptainTeamOfPlayer(player)), archipelago);
        }
    }


    /**
     * This Method returns the influence of the given wizard in the archielago
     * @param p player used for influence calculation
     * @param archipelago target archipelago
     * @return an int representing the influence
     * @throws ExceptionGame if a method call throws an exception
     */
    @Override
    public int getWizardInfluenceInArchipelago(Player p, Archipelago archipelago) throws ExceptionGame {
        Wizard wizard = getGame().getWizardFromPlayer(basicMatch.getCaptainTeamOfPlayer(p));
        int CardEffectWizardInfluence = basicMatch.getWizardInfluenceInArchipelago(p, archipelago);

        if(activeInfluenceCard != null){
            CardEffectWizardInfluence = activeInfluenceCard.calculateEffectInfluence(wizard, archipelago, CardEffectWizardInfluence);
        }
        return CardEffectWizardInfluence;
    }


    public void setActiveInfluenceCard(InfluenceEffectCard activeInfluenceCard) {
        this.activeInfluenceCard = activeInfluenceCard;
    }

    public InfluenceEffectCard getActiveInfluenceCard() {
        return activeInfluenceCard;
    }

    public void setActiveMotherNatureCard(MotherNatureEffectCard activeMotherNatureCard) {
        this.activeMotherNatureCard = activeMotherNatureCard;
    }

    public MotherNatureEffectCard getActiveMotherNatureCard() {
        return activeMotherNatureCard;
    }

    public ProhibitionEffectCard getActiveProhibitionCard() {
        return activeProhibitionCard;
    }

    public void setActiveProhibitionCard(ProhibitionEffectCard activeProhibitionCard) {
        this.activeProhibitionCard = activeProhibitionCard;
    }


}


