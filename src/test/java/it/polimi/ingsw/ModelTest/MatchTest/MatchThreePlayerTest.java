package it.polimi.ingsw.ModelTest.MatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MatchThreePlayerTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final Match match3players = factoryMatch.newMatch(3);
    private final Player playerOne = new Player("nameOne", "usernameOne");
    private final Player playerTwo = new Player("nameTwo", "usernameTwo");
    private final Player playerThree = new Player("nameThree", "usernameThree");

    public void gameSetter() {
        List<Player> players = new ArrayList<>();
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        Assertions.assertDoesNotThrow(() ->
                match3players.setGame(players)
        );
    }

    public int getSteps(Player player) throws ExceptionGame{
        Wizard wizard = match3players.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }

    public void printGame(){
        System.out.println("\nSTART OF PRINT ");
        System.out.println("professor in game: "+match3players.getGame().getProfessors());
        System.out.println("action order in round: " + match3players.getActionPhaseOrderOfPlayers());
        System.out.println("Archipelagos "+ match3players.getGame().getArchipelagos().size());
        System.out.println(match3players.getGame().getWizards());
        for( Wizard w : match3players.getGame().getWizards()){
            System.out.println("--->"+w);
            System.out.println("towers " + w.getBoard().getTowersInBoard().size());
            System.out.println("assistantCard " + w.getRoundAssistantsCard());
            System.out.println("number of students in entrance " + w.getBoard().getStudentsInEntrance().size());
            System.out.println("professors in table : " +w.getBoard().getProfessorInTable());
        }
        System.out.println("END OF PRINT \n");
    }
    @Test
    void verifyMatch3Players() {
        gameSetter();
        Assertions.assertEquals(3, match3players.getNumberOfPlayers());
        Assertions.assertEquals(4, match3players.getNumberOfMovableStudents());
        Assertions.assertEquals(3, match3players.getNumberOfClouds());
        Assertions.assertEquals(6, match3players.getNumberOfTowers());
        Assertions.assertEquals(9, match3players.getNumberOfStudentInEntrance());
        Assertions.assertEquals(4, match3players.getNumberOfStudentsOnCLoud());
        for (Wizard wizard : match3players.getGame().getWizards()) {
            Assertions.assertDoesNotThrow(() ->
                    Assertions.assertTrue(match3players.getPlayers().contains(match3players.getPlayerFromWizard(wizard)))
            );
        }

        Assertions.assertThrows(ExceptionGame.class, () -> match3players.setTeamsOne(playerOne, playerTwo));
        Assertions.assertThrows(ExceptionGame.class, () -> match3players.setTeamsTwo(playerOne, playerTwo));
        Assertions.assertThrows(ExceptionGame.class, match3players::getTeams);

    }

    @Test
    void playAssistantsCard_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {

            List<AssistantsCards> assistantsCards_1 = match3players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match3players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = match3players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            match3players.playAssistantsCard(playerOne, assistantsCards_1.get(1));
            Assertions.assertThrows(ExceptionGame.class, ()-> match3players.playAssistantsCard(playerTwo, assistantsCards_2.get(1)));
            Assertions.assertThrows(ExceptionGame.class, ()-> match3players.playAssistantsCard(playerThree, assistantsCards_3.get(1)));

            match3players.playAssistantsCard(playerTwo, assistantsCards_2.get(0));
            match3players.playAssistantsCard(playerThree, assistantsCards_3.get(2));
            Assertions.assertEquals(9, assistantsCards_1.size());
            Assertions.assertEquals(9, assistantsCards_2.size());
            Assertions.assertEquals(9, assistantsCards_3.size());
            Assertions.assertEquals(playerOne, match3players.getActionPhaseOrderOfPlayers().get(1));
            Assertions.assertEquals(playerTwo, match3players.getActionPhaseOrderOfPlayers().get(0));
            Assertions.assertEquals(playerThree, match3players.getActionPhaseOrderOfPlayers().get(2));
            //verify that playerOne can't play the card already played by playerTwo, without resetting the round (assistant's cards played in the round)
            Assertions.assertThrows(ExceptionGame.class, ()->
                            match3players.playAssistantsCard(playerOne, assistantsCards_1.get(1))
                    );
                }
        );
    }
    @RepeatedTest(24)
    void moveMotherNature_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match3players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match3players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = match3players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            match3players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match3players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            match3players.playAssistantsCard(playerThree, assistantsCards_3.get(9)); //5 step

            int oldPositionMotherNature = match3players.getPositionOfMotherNature();
            //Try to move MN with more steps that are allowed for the playerThree
            Assertions.assertThrows(ExceptionGame.class, ()-> match3players.moveMotherNature(playerThree, match3players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerThree) +1)%match3players.getGame().getArchipelagos().size())));
            //retry to move MN with a number of steps allowed for playerThree
            match3players.moveMotherNature(playerThree, match3players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerThree))%match3players.getGame().getArchipelagos().size()));
            match3players.getActionPhaseOrderOfPlayers().add(playerThree);
            int oldPositionMotherNature_1 = match3players.getPositionOfMotherNature();
            //try to move MN on the same island
            Assertions.assertThrows(ExceptionGame.class, ()->match3players.moveMotherNature(playerThree, match3players.getGame().getArchipelagos().get((oldPositionMotherNature_1 )%match3players.getGame().getArchipelagos().size())));
            //sets the game in order to put a professor on the board of playerOne and have influence on the isle where MN will be in the next move (oldPositionMotherNature +2)
            for(Student s : match3players.getGame().getArchipelagos().get((oldPositionMotherNature_1 + getSteps(playerThree))%match3players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                for(int i = 0; i<match3players.getGame().getProfessors().size(); i++){
                    if (match3players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        match3players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(match3players.getGame().getProfessors().get(i));
                        match3players.getGame().getProfessors().remove(match3players.getGame().getProfessors().get(i));
                    }
                }
            }
            //try to move MN to the next archipelago where playerThree has the most influence
            match3players.moveMotherNature(playerThree, match3players.getGame().getArchipelagos().get((oldPositionMotherNature_1 + getSteps(playerThree))%match3players.getGame().getArchipelagos().size()));
            match3players.getActionPhaseOrderOfPlayers().add(playerThree);
            int oldPositionMotherNature_2 = match3players.getPositionOfMotherNature();
            //check if the tower is build correctly
            Assertions.assertEquals(match3players.getGame().getWizardFromPlayer(playerThree), match3players.getGame().getArchipelagos().get(match3players.getPositionOfMotherNature()).getIsle().get(0).getTower().getProperty());
            //sets again the game in order to put a professor on the board of playerThee and have influence on the next isle where MN will be in the next move
            for(Student s : match3players.getGame().getArchipelagos().get((oldPositionMotherNature_2 + 1)%match3players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                for(int i = 0; i<match3players.getGame().getProfessors().size(); i++){
                    if (match3players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        match3players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(match3players.getGame().getProfessors().get(i));
                        match3players.getGame().getProfessors().remove(match3players.getGame().getProfessors().get(i));

                    }
                }
            }
            Archipelago archipelagoMerged = match3players.getGame().getArchipelagos().get((oldPositionMotherNature_2 + 1)%match3players.getGame().getArchipelagos().size());
            Archipelago previousArchipelagoMerged = match3players.getGame().getArchipelagos().get((oldPositionMotherNature_2)%match3players.getGame().getArchipelagos().size());
            Assertions.assertTrue(match3players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            match3players.moveMotherNature(playerThree, archipelagoMerged);
            match3players.getActionPhaseOrderOfPlayers().add(playerThree);
            //check if the tower is build correctly and the merge is done
            Assertions.assertEquals(11, match3players.getGame().getArchipelagos().size()); //size is decremented
            Assertions.assertTrue(match3players.getGame().getArchipelagos().contains(archipelagoMerged));
            Assertions.assertFalse(match3players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            Assertions.assertEquals(match3players.getGame().getWizardFromPlayer(playerThree), match3players.getGame().getArchipelagos().get(match3players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().get(0).getTower().getProperty());
            Assertions.assertEquals(2, match3players.getGame().getArchipelagos().get(match3players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().size());

            //sets playerTwo the most influence on the ArchipelagoMerged
            for(Student s : archipelagoMerged.getStudentFromArchipelago()){
                boolean isInGame = false;
                for(int i = 0; i<match3players.getGame().getProfessors().size(); i++){
                    if (match3players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        match3players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(match3players.getGame().getProfessors().get(i));
                        match3players.getGame().getProfessors().remove(match3players.getGame().getProfessors().get(i));
                        isInGame = true;
                    }
                }
                if(!isInGame){  //controllare che non ci sia gia messo in player2 dopo il primo studente dell isola
                    for(int i = 0; i<match3players.getGame().getWizardFromPlayer(playerThree).getBoard().getProfessorInTable().size(); i++){
                        if(match3players.getGame().getWizardFromPlayer(playerThree).getBoard().getProfessorInTable().get(i).getColor() == s.getColor()){
                            Professor professor = match3players.getGame().getWizardFromPlayer(playerThree).getBoard().removeProfessorFromTable(s.getColor());
                            match3players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(professor);
                        }
                    }

                }
            }
            int oldPositionMotherNature_3 = match3players.getPositionOfMotherNature();
            //move mother nature for 10 steps, in order to arrive to the archipelago before the archipelagoMerged
            //and replace playerTwo in the actionPhaseOrder list
            match3players.moveMotherNature(playerThree, match3players.getGame().getArchipelagos().get((oldPositionMotherNature_3+match3players.getGame().getArchipelagos().size() + getSteps(playerThree))%match3players.getGame().getArchipelagos().size()));
            match3players.getActionPhaseOrderOfPlayers().add(playerThree);
            match3players.moveMotherNature(playerThree, match3players.getGame().getArchipelagos().get((match3players.getGame().getArchipelagos().indexOf(archipelagoMerged)+match3players.getGame().getArchipelagos().size() -1 )%match3players.getGame().getArchipelagos().size()));
            match3players.getActionPhaseOrderOfPlayers().add(playerThree);
            match3players.getActionPhaseOrderOfPlayers().add(playerTwo);
            //place MN on archipelagoMerged and check if the replacement of the tower is correct
            match3players.moveMotherNature(playerTwo, archipelagoMerged);
            Assertions.assertEquals(match3players.getGame().getWizardFromPlayer(playerTwo), match3players.getGame().getArchipelagos().get(match3players.getPositionOfMotherNature()).getIsle().get(0).getTower().getProperty());
        });
    }

    @Test
    void checkVictory_NoTowers_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match3players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match3players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = match3players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            match3players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match3players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            match3players.playAssistantsCard(playerThree, assistantsCards_3.get(9)); //5 step

            //remove all the tower from playerThree and call CheckVictory from moveMotherNature
            match3players.getGame().getWizardFromPlayer(playerThree).getBoard().getTowersInBoard().removeAll(match3players.getGame().getWizardFromPlayer(playerThree).getBoard().getTowersInBoard());
            Assertions.assertEquals(match3players.getGame().getWizardFromPlayer(playerThree), match3players.getGame().getWizardsWithLeastTowers().get(0));
            int oldPositionMotherNature = match3players.getPositionOfMotherNature();
            match3players.moveMotherNature(playerThree, match3players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerThree))%match3players.getGame().getArchipelagos().size()));
        });
    }

    @Test
    void checkVictory_NoStudents_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match3players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match3players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = match3players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            match3players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match3players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            match3players.playAssistantsCard(playerThree, assistantsCards_3.get(9)); //5 step
            //remove all student from student bag
            match3players.getGame().getStudentBag().getStudentsInBag().removeAll(match3players.getGame().getStudentBag().getStudentsInBag());
            //call moveMotherNature
            Assertions.assertEquals(match3players.getGame().getWizards(), match3players.getGame().getWizardsWithLeastTowers());

            int oldPositionMotherNature = match3players.getPositionOfMotherNature();
            match3players.moveMotherNature(playerThree, match3players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerThree))%match3players.getGame().getArchipelagos().size()));

        });
    }

    @RepeatedTest(24)
    void checkVictory_LessThenThreeArchipelagos_Test() {
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match3players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match3players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = match3players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            match3players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match3players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            match3players.playAssistantsCard(playerThree, assistantsCards_3.get(9)); //5 step

            //remove 9 archipelagos from the initial 12
            match3players.getGame().getArchipelagos().removeAll(match3players.getGame().getArchipelagos().subList(0, 9));
            //call motherNature
            Assertions.assertEquals(match3players.getGame().getWizards(), match3players.getGame().getWizardsWithLeastTowers());

            for(Archipelago a : match3players.getGame().getArchipelagos()){
                a.setMotherNaturePresence(false);
            }
            //sets MN on the first archipelago
            match3players.getGame().getArchipelagos().get(0).setMotherNaturePresence(true);
            match3players.getGame().getMotherNature().setPosition(0);
            //check if the match will finish
            int oldPositionMotherNature = match3players.getPositionOfMotherNature();
            match3players.moveMotherNature(playerThree, match3players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerThree))%match3players.getGame().getArchipelagos().size()));
        });
    }
}

