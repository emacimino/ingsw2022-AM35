package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.io.Serial;

public abstract class AlternativeMessage {
    @Serial
    private static final long serialVersionUID = 6345566953463396637L;

    private Object content;
    private GameStateMessage type;
}
