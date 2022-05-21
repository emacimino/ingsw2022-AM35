package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Client.Printable;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class CurrentGameMessage extends Message {
    private static final long serialVersionUID = -9016040814084856548L;
    private Game game;
    private Color color;

    public CurrentGameMessage(Game game) {
        this.game = game;
        setType(TypeMessage.GAME_INFO);
    }

    public Game getGame() {
        return game;
    }


}
