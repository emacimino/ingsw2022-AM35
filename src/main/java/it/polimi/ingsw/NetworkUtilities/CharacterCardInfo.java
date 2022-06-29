package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Class used to send information about the character cards
 */
public class CharacterCardInfo extends Message implements Serializable {
    @Serial
    private final static long serialVersionUID = -4417610454958723784L;
    private final Map<Integer, Student> studentsOnCardMap;
    private final Map<Integer, Student> studentsOnEntranceMap;
    private final Map<Integer, Archipelago> archipelagoMap;
    private final String characterCardName;

    /**
     * Constructor of the class
     * @param characterCardName Name of the card
     * @param studentsOnCardMap Students on the card
     * @param studentsOnEntranceMap Students in entrance
     * @param archipelagoMap Map of the archipelagos
     */
    public CharacterCardInfo(String characterCardName, Map<Integer, Student> studentsOnCardMap, Map<Integer, Student> studentsOnEntranceMap, Map<Integer, Archipelago> archipelagoMap) {
        this.characterCardName = characterCardName;
        this.studentsOnCardMap = studentsOnCardMap;
        this.studentsOnEntranceMap = studentsOnEntranceMap;
        this.archipelagoMap = archipelagoMap;
        super.setType(TypeMessage.SHOW_CHARACTER_CARD_INFO);
    }

    /**
     * Method that return the card's name
     * @return a string containing the card's name
     */
    public String getCharacterCardName() {
        return characterCardName;
    }

    /**
     * Method that returns the student on the card
     * @return a map containing the students
     */
    public Map<Integer, Student> getStudentsOnCardMap() {
        return studentsOnCardMap;
    }

    /**
     * Method that returns the student on the entrance
     * @return a map containing the students in the entrance
     */
    public Map<Integer, Student> getStudentsOnEntranceMap() {
        return studentsOnEntranceMap;
    }

    /**
     * Method that returns the map of the archipelagos
     * @return a map of archipelagos
     */
    public Map<Integer, Archipelago> getArchipelagoMap() {
        return archipelagoMap;
    }
}
