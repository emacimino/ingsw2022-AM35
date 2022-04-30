package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.Wizard.Wizard;


public abstract class InfluenceEffectCard extends CharacterCard {


    public InfluenceEffectCard(Game game) {
        super(game);
    }

    @Override
    public void usedBakerCard(Player player1, Player player2, Professor professor) throws ExceptionGame {
        super.usedBakerCard(player1, player2, professor);
    }

    @Override
    public int getCost() {
        return super.getCost();
    }

    @Override
    public int usedChefCard(Wizard wizard, Island island, Color color) throws ExceptionGame {
        return super.usedChefCard(wizard, island, color);
    }

    @Override
    public int usedArcherCard(Wizard wizard, Island island) throws ExceptionGame {
        return super.usedArcherCard(wizard, island);
    }

    @Override
    public int usedKnightCard(Wizard wizard, Archipelago archipelago) throws ExceptionGame {
        return super.usedKnightCard(wizard, archipelago);
    }

    @Override
    public void usedMessengerCard(Wizard wizard, Archipelago archipelago) throws ExceptionGame {
        super.usedMessengerCard(wizard, archipelago);
    }
}
