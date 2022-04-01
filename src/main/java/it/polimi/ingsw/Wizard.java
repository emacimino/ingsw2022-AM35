package it.polimi.ingsw;

import java.util.Collection;
import java.util.HashSet;

/**
 * Wizard is the class who is related to the username of the Player and manage to play the assistant's card
 * and move the Student from the Entrance of the Board
 */

public class Wizard {
    private final Board board = new Board();
    private final AssistantsDeck assistantsDeck= new AssistantsDeck();
    private final String username;
    private final Collection<Coin> coins = new HashSet<>();
    private final Collection<Archipelago> archipelagosOfWizard = new HashSet<>();
    private AssistantsCards roundAssistantsCard;
    private int numOfStudentMovable;
    private int limitOfStudentInEntrance;

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

    public Board getBoard() {
        return board;
    }

    public Collection<Archipelago> getArchipelagosOfWizard() {
        return archipelagosOfWizard;
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



    public void placeStudentOnArchipelago(Student student, Archipelago archipelago, int limitStudentEntrance, int numOfStudentMovable) throws ExceptionGame{
        if(checkIfStudentIsMovable(student, limitStudentEntrance, numOfStudentMovable)){
            archipelago.addStudentInArchipelago(student);
            board.getStudentsInEntrance().remove(student);
        }else{
            throw new ExceptionGame("Can't move Student \n");
        }
    }


    public void placeStudentOnTable(Student student, int limitOfStudentInEntrance, int numOfStudentMovable) throws ExceptionGame{
        try{
            if(checkIfStudentIsMovable(student, limitOfStudentInEntrance, numOfStudentMovable)) {
                board.getStudentsInEntrance().remove(student);
                board.addStudentInTable(student);
            }
        }catch(ExceptionGame exceptionGame){
                throw new ExceptionGame("Can't move Student-> " + exceptionGame, exceptionGame);
            }
    }


    public boolean checkIfStudentIsMovable(Student student, int limitStudentEntrance, int numOfStudentMovable) throws ExceptionGame{
        if(board.getStudentsInEntrance().contains(student)){
            if(board.getStudentsInEntrance().size() > limitStudentEntrance - numOfStudentMovable){
                    return true;
            }else{
                throw new ExceptionGame("Already move " + numOfStudentMovable + "students");
            }
        }else
            throw new ExceptionGame("Student is not in Board Entrance");
    }

    public void placeStudentInEntrance( Collection<Student> students){
        board.getStudentsInEntrance().addAll(students);
    }

    @Override
    public String toString() {
        return "Wizard{" +
                "username='" + username + '\'' +
                '}';
    }
}

