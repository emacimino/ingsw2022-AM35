package it.polimi.ingsw;

import java.util.Collection;
import java.util.HashSet;

/**
 * Wizard is the class who is related to the username of the Player and manage to play the assistant's card
 * and move the Student from the Entrance of the Board
 */

public class Wizard {
   // private Board board = new Board;
    private final AssistantsDeck assistantsDeck= new AssistantsDeck();
    private final String username;
    private final Collection<Coin> coins = new HashSet<>();
   // private Collection<Arcrhipelago> archipelagosOfWizard = new HashSet<>();
    private AssistantsCards roundAssistantsCard;

    public Wizard(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public AssistantsDeck getAssistantsDeck() {
        return assistantsDeck;
    }

    public Collection<Coin> getCoins() {
        return coins;
    }

    public AssistantsCards getRoundAssistantsCard() {
        return roundAssistantsCard;
    }

    /**
     *Method who assign the parameter passed to this.roundAssistantsCard
     * @param roundAssistantsCard is the card to assign to this.roundAssistantsCard
     */
    private void setRoundAssistantsCard(AssistantsCards roundAssistantsCard) {
        this.roundAssistantsCard = roundAssistantsCard;
        assistantsDeck.usedAssistantCard(roundAssistantsCard);

    }

    /**
     * The method manage the case either the card is playable or not
     * @param assistantsCard is the Card the Player wants to play
     * @param playedCardsByOpponent are the assistant's card already played by the opponents
     * @throws ExceptionGame is the Exception thrown when it is not possibile to play the card choose by the player
     */
    public void playAssistantsCard(AssistantsCards assistantsCard, Collection<AssistantsCards> playedCardsByOpponent) throws ExceptionGame{
        if(playableAssistantsCard(assistantsCard, playedCardsByOpponent)){
            setRoundAssistantsCard(assistantsCard);
        }
        else throw new ExceptionGame("Card already played by another Wizard \n");
    }

    /**
     * @param assistantsCard is the Card the Player wants to play
     * @param playedCardsByOpponent are the assistant's card already played by the opponents
     * @return if the card is playable the method returns true
     */
    public boolean playableAssistantsCard(AssistantsCards assistantsCard, Collection<AssistantsCards> playedCardsByOpponent) {

        if (checkIfAssistantsCardAlreadyPlayed(assistantsCard, playedCardsByOpponent)) {
            if (checkIfThereIsAlternativeAssistantsCard( playedCardsByOpponent))
                return false;
        }
        return true;
    }

    /**
     * @param assistantsCard is the Card the Player wants to play
     * @param playedCardsByOpponent are the assistant's card already played by the opponent
     * @return if the card is already played by an opponent, the method returns true
     */
    public boolean checkIfAssistantsCardAlreadyPlayed(AssistantsCards assistantsCard, Collection<AssistantsCards> playedCardsByOpponent){
        boolean alreadyPlayed;
        if(!playedCardsByOpponent.isEmpty()){
             if(playedCardsByOpponent.contains(assistantsCard))
                   alreadyPlayed = true;
             else
                   alreadyPlayed = false;

             return alreadyPlayed;
        }
        return false;
    }

    /**
     * @param playedCardsByOpponent are the assistant's card already played by the opponent
     * @return if in the deck of the Wizard there is an assistant's card different from the cards already played by the opponents, the method returns true
     */
    public boolean checkIfThereIsAlternativeAssistantsCard( Collection<AssistantsCards> playedCardsByOpponent){
       for( AssistantsCards a : assistantsDeck.playableAssistants){
           if(!playedCardsByOpponent.contains(a)){
               return true;
           }
       }
       return false;
    }

 /*
    public void placeStudentOnArchipelago(Student student, Archipelago archipelago) throws ExceptionGame{
        if(checkIfStudentIsMovable(student)){
            archipelago.addStudentInArchipelago(student);
            board.studentInEntrance.remove(student);
        }else{
            throw new ExceptionGame("Can't move Student \n");
        }
    }


    public void placeStudentOnTable(Student student) throws ExceptionGame{
        if(checkIfStudentIsMovable(student)){
            board.addStudentInTable(student);
            board.studentInEntrance.remove(student);
        }else{
            throw new ExceptionGame("Can't move Student \n");
        }
    }


    public boolean checkIfStudentIsMovable(Student student) throws ExceptionGame{
        if(board.studentInEntrance.contains(student)){
            if(board.studentInEntrance.size() > board.limitStudentEntrance - 3){
                    return true;
            }else{
                throw new ExceptionGame("Already move 3 students \n");
            }
        }else{
            throw new ExceptionGame("Student is not in Board Entrance \n");
        }
        return false;
    }
*/
}

