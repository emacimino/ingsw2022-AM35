package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.List;

public abstract class CharacterCard {
    protected int cost;
    protected Game game;


    public CharacterCard(Game game) {
        this.game = game;
    }

    public void usedBakerCard(Player player1, Player player2, Professor professor) throws ExceptionGame {
        throw new ExceptionGame("This method is not available for this card");
    }

    public int getCost() {
        return cost;
    }

    public int usedChefCard(Wizard wizard, Island island, Color color) throws ExceptionGame {
        throw new ExceptionGame("This method is not available for this card");
    }

    public int usedArcherCard(Wizard wizard, Island island) throws ExceptionGame {
        throw new ExceptionGame("This method is not available for this card");
    }

    public int usedKnightCard(Wizard wizard, Archipelago archipelago) throws ExceptionGame {
        throw new ExceptionGame("This method is not available for this card");
    }

    public void usedMessengerCard(Wizard wizard, Archipelago archipelago) throws ExceptionGame {
        throw new ExceptionGame("This method is not available for this card");
    }

    public void usedFriarCard(Archipelago archipelago, Student chosenStudent) throws ExceptionGame {
        throw new ExceptionGame("This method is not available for this card");

    }

    public void usedPrincessCard(Wizard wizard, Student chosenStudent) throws ExceptionGame{
        throw new ExceptionGame("This method is not available for this card");
    }

    public void usedJesterCard(Wizard wizard, List<Student> StudentOnEntranceToBeSwitched, List<Student> StudentOnCardToBeSwitched) throws ExceptionGame {
        throw new ExceptionGame("This method is not available for this card");
    }


}
