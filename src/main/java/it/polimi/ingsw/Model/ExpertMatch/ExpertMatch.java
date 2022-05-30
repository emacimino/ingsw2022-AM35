package it.polimi.ingsw.Model.ExpertMatch;


import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.*;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.Message.CharacterCardInGameMessage;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
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

    @Override
    public void setGame(List<Player> players) throws ExceptionGame {
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

    public Map<String, CharacterCard> getCharactersForThisGame() {
        return characterCardInMatchMap;
    }


    @Override
    public void moveMotherNature(Player player, Archipelago archipelago) throws ExceptionGame {
        if(activeProhibitionCard!=null)
            if(archipelago.isProhibition()){
                activeProhibitionCard.resetAProhibitionEffect(archipelago);
                basicMatch.checkVictory();
                if(basicMatch.getActionPhaseOrderOfPlayers().size() == 0)
                    throw new ExceptionGame("Every player has played in this round phase");
                if (player.equals(basicMatch.getActionPhaseOrderOfPlayers().get(basicMatch.getActionPhaseOrderOfPlayers().size() - 1))) {
                    basicMatch.resetRound();
                }
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
            basicMatch.checkVictory();
        }
        if(basicMatch.getActionPhaseOrderOfPlayers().size() == 0)
            throw new ExceptionGame("Every player has played in this round phase");
        if (player.equals(basicMatch.getActionPhaseOrderOfPlayers().get(basicMatch.getActionPhaseOrderOfPlayers().size() - 1))) {
            basicMatch.resetRound();
        }

        if(activeInfluenceCard != null) {
            activeInfluenceCard.resetCard();
            activeInfluenceCard = null;
        }
        if(activeMotherNatureCard != null) {
            activeMotherNatureCard.resetCard();
            activeMotherNatureCard = null;
        }
    }

    protected void buildTower(Player player, Archipelago archipelago) throws ExceptionGame {
        boolean isMostInfluence = true;
        for(Player p : getRivals(player)){
            if(getWizardInfluenceInArchipelago(p, archipelago) >= getWizardInfluenceInArchipelago(player, archipelago)) { //influenza del player in negativo
                isMostInfluence = false;
            }
        }
        if(isMostInfluence) {
            basicMatch.getGame().buildTower( getGame().getWizardFromPlayer(basicMatch.getCaptainTeamOfPlayer(player)), archipelago);
        }


    }

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

    public Map<String, CharacterCard> getCharacterCardInMatchMap() {
        return characterCardInMatchMap;
    }
}


