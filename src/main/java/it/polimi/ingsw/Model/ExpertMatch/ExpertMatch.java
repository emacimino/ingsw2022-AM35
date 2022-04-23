package it.polimi.ingsw.Model.ExpertMatch;


import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.Random;

/**
 * this class extends the MatchDecorator to implements the ExpertMode
 */
public class ExpertMatch extends MatchDecorator{
    protected ExpertMatch expertMatch;
    private DeckCharacterCard deckCharacterCard;
    private CharacterCard CharacterOne = deckCharacterCard.drawCharacterCard();
    private CharacterCard CharacterTwo = deckCharacterCard.drawCharacterCard();
    private CharacterCard CharacterThree = deckCharacterCard.drawCharacterCard();

    /**
     * Constructor of ExpertMatch
     * @param match is the match that has to be modified to play in ExpertMode
     */
    public ExpertMatch(Match match) {
        super();
        expertMatch.setFirstCoin();
    }

    /**
     * The override add a check to the number of student that are placed on the board when a student is moved
     * @param player is the player which moves the student
     * @param student is the student
     * @throws ExceptionGame
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

    public CharacterCard drawCharacterCard(){
        Random random = new Random();
        String[] draw;
        CharacterCard characterCard;


        return characterCard.remove(random.nextInt(studentsInBag.size()));
    }

    /*
+expertMatch( Match): void
+drawCharacterCard(): void
+setFirstCoin(): void
            (@override)+moveStudentOnBoard(Player, Student): void

+useCoins(CharacterCard): void

+playCharacterCard(Player, CharacterCard): void
*/
}
