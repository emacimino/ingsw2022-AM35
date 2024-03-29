package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handle the parameters to create a message
 */
public class MessageHandler {
    private Map<Integer, Student> studentsOnEntranceMap = new HashMap<>();
    private Map<Integer, Archipelago> archipelagoMap = new HashMap<>();
    private Map<Integer, Cloud> cloudMap = new HashMap<>();
    private Map<String, CharacterCard> characterCardMap = new HashMap<>();
    private Map<Integer,Student> studentsOnCardMap = new HashMap<>();

    /**
     * Setter for studentsOnCardMap
     * @param studentsOnCard list of student on card
     */
    public void setStudentOnCardMap(List<Student> studentsOnCard) {
        studentsOnCardMap.clear();
        Integer i = 1;
        for(Student s: studentsOnCard){
            studentsOnCardMap.put(i, s);
            i++;
        }
    }
    /**
     * Getter for studentsOnCardMap
     */
    public Map<Integer, Student> getStudentsOnCardMap() {
        return studentsOnCardMap;
    }
    /**
     * Setter for archipelagoMap
     * @param archipelagos list archipelagos
     */
    protected void setArchipelagoMap(List<Archipelago> archipelagos){
        archipelagoMap.clear();
        Integer i = 1;
        for(Archipelago a: archipelagos){
            archipelagoMap.put(i, a);
            i++;
        }
    }
    /**
     * Setter for studentsOnEntranceMap
     * @param students list of student on
     * entrance
     */
    protected void setStudentOnEntranceMap(List<Student> students){
        studentsOnEntranceMap.clear();
        Integer i = 1;
        for(Student s: students){
            studentsOnEntranceMap.put(i, s);
            i++;
        }
    }
    /**
     * Setter for cloudsMap
     * @param clouds list of clouds
     */
    protected void setCloudMap(List<Cloud> clouds){
        cloudMap.clear();
        Integer i = 1;
        for(Cloud c: clouds){
            cloudMap.put(i, c);
            i++;
        }
    }

    /**
     * Setter for characterCardMap
     * @param characterCard character card map
     */
    public void setCharacterCardMap(Map<String, CharacterCard> characterCard) {
        characterCardMap = characterCard;
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
    public Map<Integer, Archipelago> getArchipelagoMap() {
        return archipelagoMap;
    }
    /**
     * getter for CloudsMap
     * @return clouds map
     */
    public Map<Integer, Cloud> getCloudMap() {
        return cloudMap;
    }


    /**
     * getter for CharacterCardMap
     * @return character card map
     */
    public Map<String, CharacterCard> getCharacterCardMap() {
        return characterCardMap;
    }

}
