package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.util.List;

public class EndMatchMessage extends Message{
    private static final long serialVersionUID = 5766057707166659044L;

    private final List<Player> winners;

    public EndMatchMessage(List<Player> winners) {
        this.winners = winners;
        setType(TypeMessage.END_MATCH);
    }
}
