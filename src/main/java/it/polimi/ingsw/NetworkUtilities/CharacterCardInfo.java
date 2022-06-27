package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Current CharacterCard selected info
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
     * @param characterCardName characterCard name
     * @param studentsOnCardMap students on card list
     * @param studentsOnEntranceMap student on the entrance of the wizard that called the card
     * @param archipelagoMap current archipelago map
     */
    public CharacterCardInfo(String characterCardName, Map<Integer, Student> studentsOnCardMap, Map<Integer, Student> studentsOnEntranceMap, Map<Integer, Archipelago> archipelagoMap) {
        this.characterCardName = characterCardName;
        this.studentsOnCardMap = studentsOnCardMap;
        this.studentsOnEntranceMap = studentsOnEntranceMap;
        this.archipelagoMap = archipelagoMap;
        super.setType(TypeMessage.SHOW_CHARACTER_CARD_INFO);
    }

    /**
     * Getter for characterCard name
     * @return character card name
     */
    public String getCharacterCardName() {
        return characterCardName;
    }

    /**
     * Getter for students on card
     * @return students on card list
     */
    public Map<Integer, Student> getStudentsOnCardMap() {
        return studentsOnCardMap;
    }
    /**
     * Getter for students on entrance
     * @return students on entrance list
     */
    public Map<Integer, Student> getStudentsOnEntranceMap() {
        return studentsOnEntranceMap;
    }
    /**
     * Getter for archipelago map
     * @return archipelago map
     */
    public Map<Integer, Archipelago> getArchipelagoMap() {
        return archipelagoMap;
    }
}
