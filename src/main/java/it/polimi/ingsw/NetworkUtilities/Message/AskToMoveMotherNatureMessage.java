package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

public class AskToMoveMotherNatureMessage extends Message{

    private static final long serialVersionUID = -298606158447402860L;
    private final int numOfSteps;

    public AskToMoveMotherNatureMessage(int numOfSteps) {
        this.numOfSteps = numOfSteps;
        setType(GameStateMessage.ASK_MOVE_MOTHER_NATURE);
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }
}