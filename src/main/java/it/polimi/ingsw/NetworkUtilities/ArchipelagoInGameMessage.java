package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

import java.io.Serial;
import java.util.Map;

public class ArchipelagoInGameMessage extends Message{
    @Serial
    private static final long serialVersionUID = 5281586546181603771L;
    private final Map<Integer, Archipelago> archipelagos;

    public ArchipelagoInGameMessage(Map<Integer, Archipelago> archipelagos){
        this.archipelagos = archipelagos;
        setType(TypeMessage.ARCHIPELAGOS_IN_GAME);
    }

    public Map<Integer, Archipelago> getArchipelago() {
        return archipelagos;
    }
}
