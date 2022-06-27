package it.polimi.ingsw.Client;


import it.polimi.ingsw.Client.Cli.Constants;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to display a remote model on the client
 */
public class RemoteModel {
    private Game game;
    private Map<String , AssistantsCards> assistantsCardsMap = new HashMap<>();
    private Map<Integer, Student> studentsOnEntranceMap = new HashMap<>();
    private Board currentBoard;
    private Map<Integer, Student> studentsOnCardMap = new HashMap<>();
    private Map<Integer, Archipelago> archipelagosMap = new HashMap<>();
    private Map<Integer, Cloud> cloudsMap = new HashMap<>();
    private Map<String, CharacterCard> characterCardMap = new HashMap<>();
    private String activeCharacterCard;
    private List<Integer> studentsFromCard = new ArrayList<>();
    private Integer archipelagoSelected;
    private List<Color> colorSelected = new ArrayList<>();
    private List<Integer> studentFromEntrance = new ArrayList<>();

    private Integer studentSelectedFromEntrance;

    public void setStudentSelectedFromEntrance(Integer studentSelectedFromEntrance) {
        this.studentSelectedFromEntrance = studentSelectedFromEntrance;
    }

    public Integer getStudentSelectedFromEntrance() {
        return studentSelectedFromEntrance;
    }

    /**
     * Set method for studentFromCard
     * @param studentsFromCard list of students
     */
    public void setStudentsFromCard(List<Integer> studentsFromCard) {
        this.studentsFromCard = studentsFromCard;
    }

    /**
     * Set method for color selection
     * @param colorSelected color selected
     */
    public void setColorSelected(Color colorSelected) {
        this.colorSelected.add(colorSelected);
    }

    /**
     * Set method for student from entrance
     * @param student student from entrance
     */
    public void setStudentFromEntrance(Student student) {
        Integer indexStud = getStudentOnEntranceIndex(student);
        if(!studentFromEntrance.contains(indexStud)){
            studentFromEntrance.add(indexStud);
        }else
            studentFromEntrance.remove(studentFromEntrance.indexOf(indexStud));
    }

    /**
     * Get method returning the students on the card
     * @return a list of int representing the students
     */
    public List<Integer> getStudentsFromCard() {
        return studentsFromCard;
    }

    /**
     * Get method returning the color selected
     * @return the color selected
     */
    public List<Color> getColorSelected() {
        return colorSelected;
    }

    /**
     * Get method returning the students from the entrance
     * @return a list of int representing the students
     */
    public List<Integer> getStudentFromEntrance() {
        return studentFromEntrance;
    }

    /**
     * Get method returning the archipelago selected
     * @return an int representing the archipelago
     */
    public Integer getArchipelagoSelected() {
        return archipelagoSelected;
    }

    /**
     * Set method for selecting an archipelago
     * @param archipelagoSelected an int representing the set archipelago
     */
    public void setArchipelagoSelected(Integer archipelagoSelected) {
        this.archipelagoSelected = archipelagoSelected;
    }

    /**
     * Set the StudentOnEntranceMap
     * @param map student on entrance map
     */
    public void setStudentOnEntranceMap(Map<Integer, Student> map){
        studentsOnEntranceMap = map;
    }

    /**
     * Set the archipelago map
     * @param map archipelago map
     */
    public void setArchipelagosMap(Map<Integer, Archipelago> map){
        archipelagosMap = map;
    }
    /**
     * Set the clouds map
     * @param map clouds map
     */
    public void setCloudsMap(Map<Integer, Cloud> map){
        cloudsMap = map;
    }

    /**
     * Set the characterCard map
     * @param map characterCard map
     */
    public void setCharacterCardMap(Map<String, CharacterCard> map ){
        characterCardMap = map;
    }
    /**
     * Set the Student On Character Card Map
     * @param studentsOnCard student on character card map
     */
    public void setStudentsOnCardMap(Map<Integer, Student> studentsOnCard) {
        this.studentsOnCardMap = studentsOnCard;
        }

    /**
     * getter for StudentsOnCardMap
     * @return student on card map
     */
    public Map<Integer, Student> getStudentsOnCardMap() {
        return studentsOnCardMap;
    }
    /**
     * getter for AssistantsCardsMap
     * @return assistant card map
     */
    public Map<String, AssistantsCards> getAssistantsCardsMap() {
        return assistantsCardsMap;
    }
    /**
     * getter for StudentsOnEntranceMap
     * @return student on entrance map
     */
    public Map<Integer, Student> getStudentsOnEntranceMap() {
        return studentsOnEntranceMap;
    }
    /**
     * getter for ArchipelagoMap
     * @return archipelago map
     */
    public Map<Integer, Archipelago> getArchipelagosMap() {
        return archipelagosMap;
    }
    /**
     * getter for CloudsMap
     * @return clouds map
     */
    public Map<Integer, Cloud> getCloudsMap() {
        return cloudsMap;
    }
    /**
     * getter for current game
     * @return instance of game
     */
    public Game getGame() {
        return game;
    }
    /**
     * getter for CharacterCardsMap
     * @return character card map
     */
    public Map<String, CharacterCard> getCharacterCardMap() {
        return characterCardMap;
    }

    /**
     * setter for game
     * @param game game to be set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * setter for assistantsCardsInTurn
     * @param assistantsCardsInTurn assistant card used in turn
     */
    public void setAssistantsCardsMap(List<AssistantsCards> assistantsCardsInTurn) {
        for (AssistantsCards a : assistantsCardsInTurn) {
            assistantsCardsMap.put(Constants.getAssistantCardCLI(a), a);
        }
    }
    /**
     * setter for activeCharacterCard
     * @param activeCharacterCardName characterCard card used in turn
     */
    public void setActiveCharacterCard(String activeCharacterCardName) {
        this.activeCharacterCard = activeCharacterCardName;
    }

    /**
     * getter for activeCharacterCard
     */
    public String getActiveCharacterCard() {
        return activeCharacterCard;
    }

    /**
     * getter for current board
     */
    public Board getCurrentBoard() {
        return currentBoard;
    }
    /**
     * setter for current board
     * @param currentBoard board used in turn
     */
    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

    /**
     * Method that sets the student selected attribute
     * @param studentSelected a list of integers representing the students
     */
    public void setStudentSelected(List<Integer> studentSelected) {
        this.studentsFromCard = studentSelected;
    }

    /**
     * Get method returning the students selected
     * @return a list of int representing the students
     */
    public List<Integer> getStudentSelected() {
        return studentsFromCard;
    }

    /**
     * Get method returning the index of the student
     * @param student student selected
     * @return the index of the student
     */
    public Integer getStudentOnEntranceIndex(Student student){
        Integer indexStud = null;
        for (Integer i : studentsOnEntranceMap.keySet()) {
            if (studentsOnEntranceMap.get(i).equals(student)) {
                indexStud = i;
            }
        }
        return indexStud;
    }

    /**
     * Method used to clear the previous selections
     */
    public void clearSelection() {
        colorSelected.clear();
        studentFromEntrance.clear();
    }
}
