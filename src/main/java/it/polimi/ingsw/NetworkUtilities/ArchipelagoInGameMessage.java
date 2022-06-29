package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

import java.io.Serial;
import java.util.Map;

/**
 * Class used for the archipelago message, contains a map of archipelagos and integer keys
 */
public class ArchipelagoInGameMessage extends Message{
    @Serial
    private static final long serialVersionUID = 5281586546181603771L;
    private final Map<Integer, Archipelago> archipelagos;

    /**
     * Constructor
     * @param archipelagos Map of archipelagos in the game
     */
    public ArchipelagoInGameMessage(Map<Integer, Archipelago> archipelagos){
        this.archipelagos = archipelagos;
        setType(TypeMessage.ARCHIPELAGOS_IN_GAME);
    }

    /**
     * Get method that returns a map of archipelagos
     * @return a map of archipelagos
     */
    public Map<Integer, Archipelago> getArchipelago() {
        return archipelagos;
    }
}
