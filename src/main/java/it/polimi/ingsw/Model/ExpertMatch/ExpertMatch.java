package it.polimi.ingsw.Model.ExpertMatch;


import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * this class extends the MatchDecorator to implements the ExpertMode
 */
public class ExpertMatch extends MatchDecorator{
    protected ExpertMatch expertMatch;
    private DeckCharacterCard deckCharacterCard = new DeckCharacterCard();
    public String CharacterOne = deckCharacterCard.drawCharacterCard();
    public String CharacterTwo = deckCharacterCard.drawCharacterCard();
    public String CharacterThree = deckCharacterCard.drawCharacterCard();

    /**
     * Constructor of ExpertMatch
     * @param match is the match that has to be modified to play in ExpertMode
     */
    public ExpertMatch(Match match) {
        super();
        this.setFirstCoin();
    }


    /**
     * The override add a check to the number of student that are placed on the board when a student is moved
     * @param player is the player which moves the student
     * @param student is the student
     */
    @Override
    public void moveStudentOnBoard(Player player, Student student) throws ExceptionGame {
        super.moveStudentOnBoard(player, student);
        if(match.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size()==3 ||
                match.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size()==6 ||
                match.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size()==9){
            match.getGame().getWizardFromPlayer(player).addACoin();
        }
    }

    /**
     * Add a coin to every wizard when we set the Match to Expert Mode
     */
    public void setFirstCoin(){
        for(Wizard wizard : match.getGame().getWizards())
            wizard.addACoin();
    }

    /**
     *
     * @return the CharacterCards for one match from the deck
     */
    public HashSet<String> getCharacterCards(){
        HashSet<String> tmp = new HashSet<>();
        tmp.add(CharacterOne);
        tmp.add(CharacterTwo);
        tmp.add(CharacterThree);
        return tmp;
    }


}
