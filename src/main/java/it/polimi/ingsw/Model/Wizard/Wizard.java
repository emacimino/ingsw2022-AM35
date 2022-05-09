package it.polimi.ingsw.Model.Wizard;

import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

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
    private int coins = 0;
    private final Collection<Archipelago> archipelagosOfWizard = new HashSet<>();
    private AssistantsCards roundAssistantsCard;
    private final int numOfStudentMovable;
    private final int limitOfStudentInEntrance;
    private boolean KNIGHT_EFFECT;
    private int MESSANGER_EFFECT;


    /**
     * THis is the constructor of the Wizard Class, it needs the username of the player, the limit
     * of number of students allowed in the entrance of the board and the number of students
     * movable during a round
     * @param username is the username of the player
     * @param limitOfStudentInEntrance is the limit of number of students allowed in the entrance of the board
     * @param numOfStudentMovable is number of students movable during a round
     */
    public Wizard(String username, int limitOfStudentInEntrance, int numOfStudentMovable) {
        this.username = username;
        this.limitOfStudentInEntrance = limitOfStudentInEntrance;
        this.numOfStudentMovable = numOfStudentMovable;
        KNIGHT_EFFECT = false;
        MESSANGER_EFFECT = 0;

    }

    /**
     * This method returns the username of the wizard
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the AssistantsDeck of the wizard
     * @return AssistantsDeck
     */
    public AssistantsDeck getAssistantsDeck() {
        return assistantsDeck;
    }


    /**
     * This method returns the Assistant's card played by the wizard in the current round
     * @return AssistantsCard
     */
    public AssistantsCards getRoundAssistantsCard() {
        return roundAssistantsCard;
    }

    /**
     * This method returns the board of the wizard
     * @return the board of the wizard
     */
    public Board getBoard() {
        return board;
    }

    /**
     * This method returns the archipelagos under the influence of the wizard
     * @return collection of Archipelago
     */
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
     * The method manage the case either the card is playable or not,
     * the methode use the method playableAssistantsCard to assure that the card selected by the player
     *  is playable and procede to invoke setRoundAssistantsCard, otherwise throws Exception
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
     * This method returns true if the card selected by the player can be actually played.
     * This method invoke the method checkIfAssistantsCardAlreadyPlayer and checkIfAssistantsCardAlreadyPlayed
     * @param assistantsCard is the Card the Player wants to play
     * @param playedCardsByOpponent are the assistant's card already played by the opponents
     * @return if the card is playable the method returns true
     */
    public boolean playableAssistantsCard(AssistantsCards assistantsCard, Collection<AssistantsCards> playedCardsByOpponent) {

        if (checkIfAssistantsCardAlreadyPlayed(assistantsCard, playedCardsByOpponent)) {
            return !checkIfThereIsAlternativeAssistantsCard(playedCardsByOpponent);
        }
        return true;
    }

    /**
     * This method return true if the card selected by the player is already played by an opponent in the same round
     * @param assistantsCard is the Card the Player wants to play
     * @param playedCardsByOpponent are the assistant's card already played by the opponent
     * @return if the card is already played by an opponent, the method returns true
     */
    public boolean checkIfAssistantsCardAlreadyPlayed(AssistantsCards assistantsCard, Collection<AssistantsCards> playedCardsByOpponent){
        boolean alreadyPlayed;
        if(!playedCardsByOpponent.isEmpty()){
            alreadyPlayed = playedCardsByOpponent.contains(assistantsCard);
             return alreadyPlayed;
        }
        return false;
    }

    /**
     * This method return true if in the player has playable cards which aren't already played by the opponent
     * @param playedCardsByOpponent are the assistant's card already played by the opponent
     * @return if in the deck of the Wizard there is an assistant's card different from the cards already played by the opponents, the method returns true
     */
    public boolean checkIfThereIsAlternativeAssistantsCard( Collection<AssistantsCards> playedCardsByOpponent){
       for( AssistantsCards a : assistantsDeck.getPlayableAssistants()){
           if(!playedCardsByOpponent.contains(a)){
               return true;
           }
       }
       return false;
    }


    /**
     * This method, with the help of the method checkIfStudentIsMovable(Student s), places the student of the player
     * on the archipelago selected by the player if possible, otherwise throws an Exception
     * @param student  is the student that the player wants to move
     * @param archipelago is the archipelago where the player wants to place his student
     * @throws ExceptionGame if the student can't be moved
     */
    public void placeStudentOnArchipelago(Student student, Archipelago archipelago) throws ExceptionGame{
        if(checkIfStudentIsMovable(student)){
            archipelago.addStudentInArchipelago(student);
            board.getStudentsInEntrance().remove(student);
        }else{
            throw new ExceptionGame("Can't move Student");
        }
    }

    /**
     * This method place the student passed, if movable, on the relative Table (based by its color), otherwise throws Exception
     * the method used the method checkIfStudentIsMovable.
     * @param student is the Student which the player wants to place on the table of the board
     * @throws ExceptionGame if the student can't be moved
     */
    public void placeStudentOnTable(Student student) throws ExceptionGame{
        try{
            if(checkIfStudentIsMovable(student)) {
                board.getStudentsInEntrance().remove(student);
                board.addStudentInTable(student);
            }
        }catch(ExceptionGame exceptionGame){
                throw new ExceptionGame("Can't move Student-> " + exceptionGame, exceptionGame);
            }
    }


    /**
     * this method return true if the students pass is movable, otherwise throws exception
     * @param student is the Student that the player wants to move
     * @return true if the student is movable
     * @throws ExceptionGame if the student is not in the board entrance or if the player has reach the number of movable students
     */
    public boolean checkIfStudentIsMovable(Student student) throws ExceptionGame{
        if(board.getStudentsInEntrance().contains(student)){
            if(board.getStudentsInEntrance().size() > limitOfStudentInEntrance - numOfStudentMovable){
                    return true;
            }else{
                throw new ExceptionGame("Already move " + numOfStudentMovable + " students");
            }
        }else
            throw new ExceptionGame("Student is not in Board Entrance");
    }

    /**
     * This method place the collection of students passed in the entrance of the board
     * @param students is the collection of student to add in the entrance of the board
     */
    public void placeStudentInEntrance( Collection<Student> students){
        board.getStudentsInEntrance().addAll(students);
    }

    /**
     *
     * @return the number of coins that belongs to the wizard
     */
    public int getCoins() {
        return coins;
    }

    public void reduceCoins(int reduce) throws ExceptionGame{
        if(reduce > getCoins())
            throw new ExceptionGame("wizard does not have enough coins");
        this.coins -= reduce;

    }

    /**
     * Override of the toString method
     * @return String
     */
    @Override
    public String toString() {
        return "Wizard{" +
                "username='" + username + '\'' +
                '}';
    }

    /**
     * Add a coin when is needed.
     */
    public void addACoin() {
        coins++;
    }


}

