package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

import java.io.Serial;
import java.util.Map;

/**
 * Archipelagos state in game Message
 */
public class ArchipelagoInGameMessage extends Message{
    @Serial
    private static final long serialVersionUID = 5281586546181603771L;
    private final Map<Integer, Archipelago> archipelagos;

    /**
     * constructor of the class
     * @param archipelagos map of archipelagos in game
     */
    public ArchipelagoInGameMessage(Map<Integer, Archipelago> archipelagos){
        this.archipelagos = archipelagos;
        setType(TypeMessage.ARCHIPELAGOS_IN_GAME);
    }

    /**
     * Getter for archipelago
     * @return archipelagos in game
     */
    public Map<Integer, Archipelago> getArchipelago() {
        return archipelagos;
    }
}
