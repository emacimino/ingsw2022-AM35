package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Baker extends InfluenceEffectCard{


    public Baker(Game game) {
        super(game);
        this.cost = 2;
    }

    /**
     * Implements the Baker effect on influence
     * @param wizardWithoutProfessor is the wizard that use the Card
     * @param wizardWithProfessor is the wizard that control the Professor
     * @param professor the professor that the wizard want to control
     * @throws ExceptionGame
     */
    public void useCharacterCard(Wizard wizardWithoutProfessor, Wizard wizardWithProfessor, Professor professor) throws ExceptionGame {

        if (wizardWithoutProfessor.getBoard().getStudentsFromTable(professor.getColor()).size() >= wizardWithProfessor.getBoard().getStudentsFromTable(professor.getColor()).size()) {
            wizardWithProfessor.getBoard().removeProfessorFromTable(professor.getColor());
            wizardWithoutProfessor.getBoard().setProfessorInTable(professor);
        }
        else throw new ExceptionGame("you can't use the effect of the card");
    }

    @Override
    public void usedBakerCard(Player player1,Player player2,Professor professor) throws ExceptionGame {
        this.useCharacterCard(game.getWizardFromPlayer(player1), game.getWizardFromPlayer(player2), professor );
    }
}
