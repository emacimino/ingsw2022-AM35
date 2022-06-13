package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHandler {
    private Map<Integer, Student> studentsOnEntranceMap = new HashMap<>();
    private Map<Integer, Archipelago> archipelagoMap = new HashMap<>();
    private Map<Integer, Cloud> cloudMap = new HashMap<>();
    private final Map<String, Color> colorMap = setColorMap();
    private Map<String, CharacterCard> characterCardMap = new HashMap<>();
    private Map<Player, AssistantsCards> assistantsCardsUsedInTurnMap = new HashMap<>();
    private Map<Integer,Student> studentsOnCardMap = new HashMap<>();

    public void setStudentOnCardMap(List<Student> studentsOnCard) {
        studentsOnCardMap.clear();
        Integer i = 1;
        for(Student s: studentsOnCard){
            studentsOnEntranceMap.put(i, s);
            i++;
        }
    }

    public Map<Integer, Student> getStudentsOnCardMap() {
        return studentsOnCardMap;
    }

    protected void setArchipelagoMap(List<Archipelago> archipelagos){
        archipelagoMap.clear();
        Integer i = 1;
        for(Archipelago a: archipelagos){
            archipelagoMap.put(i, a);
            i++;
        }
    }
    protected void setStudentOnEntranceMap(List<Student> students){
        studentsOnEntranceMap.clear();
        Integer i = 1;
        for(Student s: students){
            studentsOnEntranceMap.put(i, s);
            i++;
        }
    }
    protected void setCloudMap(List<Cloud> clouds){
        cloudMap.clear();
        Integer i = 1;
        for(Cloud c: clouds){
            cloudMap.put(i, c);
            i++;
        }
    }

    public void setCharacterCardMap(List<CharacterCard> characterCard) {
        characterCardMap.clear();
        for(CharacterCard c: characterCard){
            characterCardMap.put(c.getName(), c);

        }
    }

    public void setStudentsOnCharacterCardMap(){}

    private Map<String, Color>  setColorMap(){
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

    public Map<Player, AssistantsCards> getAssistantsCardsUsedInTurnMap() {
        Map<Player, AssistantsCards> tmpAssistantsCardsUsedInTurnMap = assistantsCardsUsedInTurnMap;
        assistantsCardsUsedInTurnMap.clear();
        return tmpAssistantsCardsUsedInTurnMap;
    }

    public void setAssistantsCardsUsedInTurnMap(Player activePlayer,AssistantsCards assistantsCardUsed) {
        this.assistantsCardsUsedInTurnMap.put(activePlayer,assistantsCardUsed);
    }

    public Map<Integer, Student> getStudentsOnEntranceMap() {
        return studentsOnEntranceMap;
    }
    public Map<Integer, Archipelago> getArchipelagoMap() {
        return archipelagoMap;
    }
    public Map<Integer, Cloud> getCloudMap() {
        return cloudMap;
    }
    public Map<String, Color> getColorMap() {
        return colorMap;
    }

    public Map<String, CharacterCard> getCharacterCardMap() {
        return characterCardMap;
    }


}
