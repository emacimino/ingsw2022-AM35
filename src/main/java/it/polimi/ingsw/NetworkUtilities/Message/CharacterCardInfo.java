package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class CharacterCardInfo extends Message implements Serializable {
    @Serial
    private final static long serialVersionUID = -4417610454958723784L;
    private final Map<Integer, Student> studentsOnCardMap;
    private final Map<Integer, Student> studentsOnEntranceMap;
    private final Map<Integer, Archipelago> archipelagoMap;
    private final String characterCardName;
    public CharacterCardInfo(String characterCardName, Map<Integer, Student> studentsOnCardMap, Map<Integer, Student> studentsOnEntranceMap, Map<Integer, Archipelago> archipelagoMap) {
        this.characterCardName = characterCardName;
        this.studentsOnCardMap = studentsOnCardMap;
        this.studentsOnEntranceMap = studentsOnEntranceMap;
        this.archipelagoMap = archipelagoMap;
        super.setType(TypeMessage.SHOW_CHARACTER_CARD_INFO);
    }

    public String getCharacterCardName() {
        return characterCardName;
    }

    public Map<Integer, Student> getStudentsOnCardMap() {
        return studentsOnCardMap;
    }

    public Map<Integer, Student> getStudentsOnEntranceMap() {
        return studentsOnEntranceMap;
    }

    public Map<Integer, Archipelago> getArchipelagoMap() {
        return archipelagoMap;
    }
}
