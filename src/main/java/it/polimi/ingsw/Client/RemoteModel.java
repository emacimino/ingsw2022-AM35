package it.polimi.ingsw.Client;


import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;

import java.util.HashMap;
import java.util.Map;

public class RemoteModel {
    private Game game;



    private Map<String , AssistantsCards> assistantsCardsMap = new HashMap<>();
    private Map<Integer, Student> studentsOnEntranceMap = new HashMap<>();
    private Map<Integer, Student> studentsOnBoardMap = new HashMap<>();

    private Board currentBoard;

    private Map<Integer, Student> studentsOnCardMap = new HashMap<>();
    private Map<Integer, Archipelago> archipelagosMap = new HashMap<>();
    private Map<Integer, Cloud> cloudsMap = new HashMap<>();
    private Map<String, Color> colorMap = setColorCardMap();
    private Map<String, CharacterCard> characterCardMap = new HashMap<>();
    private AssistantsCards assistantsCardUsed;
    private String activeCharacterCard;

    public void setStudentOnEntranceMap(Map<Integer, Student> map){
        studentsOnEntranceMap = map;
    }
    public void setStudentOnBoardMap(Map<Integer, Student> map){
        studentsOnBoardMap = map;
    }
    public void setArchipelagosMap(Map<Integer, Archipelago> map){
        archipelagosMap = map;
    }
    public void setCloudsMap(Map<Integer, Cloud> map){
        cloudsMap = map;
    }

    public void setCharacterCardMap(Map<String, CharacterCard> map ){
        characterCardMap = map;
    }

    private Map<String, Color> setColorCardMap(){
        Map<String, Color> map = new HashMap<>();
        for(Color c: Color.values()){
            switch (c){
                case BLUE -> map.put("BLUE", Color.BLUE);
                case GREEN -> map.put("GREEN", Color.GREEN);
                case YELLOW -> map.put("YELLOW", Color.YELLOW);
                case PINK -> map.put("PINK", Color.PINK);
                case RED -> map.put("RED", Color.RED);
            }
        }
        return map;
    }
    public void setStudentsOnCardMap(Map<Integer, Student> studentsOnCard) {
        System.out.println("in Remote model \n " + studentsOnCard);
        this.studentsOnCardMap = studentsOnCard;
        }

    public Map<Integer, Student> getStudentsOnCardMap() {
        return studentsOnCardMap;
    }


    public Map<String, AssistantsCards> getAssistantsCardsMap() {
        return assistantsCardsMap;
    }
    public Map<Integer, Student> getStudentsOnEntranceMap() {
        return studentsOnEntranceMap;
    }
    public Map<Integer, Archipelago> getArchipelagosMap() {
        return archipelagosMap;
    }
    public Map<Integer, Cloud> getCloudsMap() {
        return cloudsMap;
    }

    public Game getGame() {
        return game;
    }

    public Map<String, CharacterCard> getCharacterCardMap() {
        return characterCardMap;
    }
    public Map<String, Color> getColorMap() {
        return colorMap;
    }
    public Map<Integer, Student> getStudentsOnBoardMap() {
        return studentsOnBoardMap;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void assistantCardUsed(AssistantsCards assistantsCards) {
        this.assistantsCardUsed = assistantsCards;
    }

    public AssistantsCards getAssistantsCardUsed() {
        return assistantsCardUsed;
    }

    public void setActiveCharacterCard(String activeCharacterCardName) {
        this.activeCharacterCard = activeCharacterCardName;
    }

    public String getActiveCharacterCard() {
        return activeCharacterCard;
    }


    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }


}
