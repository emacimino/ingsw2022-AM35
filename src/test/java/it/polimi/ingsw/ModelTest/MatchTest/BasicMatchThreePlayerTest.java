package it.polimi.ingsw.ModelTest.MatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
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

/**
 * Class used to test the creation of a 3 players match
 */
public class BasicMatchThreePlayerTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch3Players = factoryMatch.newMatch(3);
    private final Player playerOne = new Player("usernameOne");
    private final Player playerTwo = new Player("usernameTwo");
    private final Player playerThree = new Player("usernameThree");


    public void gameSetter() {
        List<Player> players = new ArrayList<>();
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        Assertions.assertDoesNotThrow(() ->
                basicMatch3Players.setGame(players)
        );
    }

    public int getSteps(Player player) throws ExceptionGame{
        Wizard wizard = basicMatch3Players.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }

    public void printGame(){
        System.out.println("\nSTART OF PRINT ");
        System.out.println("professor in game: "+ basicMatch3Players.getGame().getProfessors());
        System.out.println("action order in round: " + basicMatch3Players.getActionPhaseOrderOfPlayers());
        System.out.println("Archipelagos "+ basicMatch3Players.getGame().getArchipelagos().size());
        System.out.println(basicMatch3Players.getGame().getWizards());
        for( Wizard w : basicMatch3Players.getGame().getWizards()){
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
        Assertions.assertEquals(3, basicMatch3Players.getNumberOfPlayers());
        Assertions.assertEquals(4, basicMatch3Players.getNumberOfMovableStudents());
        Assertions.assertEquals(3, basicMatch3Players.getNumberOfClouds());
        Assertions.assertEquals(6, basicMatch3Players.getNumberOfTowers());
        Assertions.assertEquals(9, basicMatch3Players.getNumberOfStudentInEntrance());
        Assertions.assertEquals(4, basicMatch3Players.getNumberOfStudentsOnCLoud());
        for (Wizard wizard : basicMatch3Players.getGame().getWizards()) {
            Assertions.assertDoesNotThrow(() ->
                    Assertions.assertTrue(basicMatch3Players.getPlayers().contains(basicMatch3Players.getPlayerFromWizard(wizard)))
            );
        }
        List<Player> playersTest = new ArrayList<>();
        playersTest.add(playerThree);
        playersTest.add(playerOne);
        playersTest.add(playerThree);
        Assertions.assertThrows(ExceptionGame.class, () -> basicMatch3Players.setTeams(playersTest));

        Assertions.assertThrows(ExceptionGame.class, basicMatch3Players::getTeams);

    }

    @Test
    void playAssistantsCard_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {

            List<AssistantsCards> assistantsCards_1 = basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(1));
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(1)));
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch3Players.playAssistantsCard(playerThree, assistantsCards_3.get(1)));

            basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(0));
            basicMatch3Players.playAssistantsCard(playerThree, assistantsCards_3.get(2));
            Assertions.assertEquals(9, assistantsCards_1.size());
            Assertions.assertEquals(9, assistantsCards_2.size());
            Assertions.assertEquals(9, assistantsCards_3.size());
            Assertions.assertEquals(playerOne, basicMatch3Players.getActionPhaseOrderOfPlayers().get(1));
            Assertions.assertEquals(playerTwo, basicMatch3Players.getActionPhaseOrderOfPlayers().get(0));
            Assertions.assertEquals(playerThree, basicMatch3Players.getActionPhaseOrderOfPlayers().get(2));
            //verify that playerOne can't play the card already played by playerTwo, without resetting the round (assistant's cards played in the round)
            Assertions.assertThrows(ExceptionGame.class, ()->
                            basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(1))
                    );
                }
        );
    }
    @RepeatedTest(24)
    void moveMotherNature_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            basicMatch3Players.playAssistantsCard(playerThree, assistantsCards_3.get(9)); //5 step

            int oldPositionMotherNature = basicMatch3Players.getPositionOfMotherNature();
            //Try to move MN with more steps that are allowed for the playerThree
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch3Players.moveMotherNature(playerThree, basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerThree) +1)% basicMatch3Players.getGame().getArchipelagos().size())));
            //retry to move MN with a number of steps allowed for playerThree
            basicMatch3Players.moveMotherNature(playerThree, basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerThree))% basicMatch3Players.getGame().getArchipelagos().size()));
            basicMatch3Players.getActionPhaseOrderOfPlayers().add(playerThree);
            int oldPositionMotherNature_1 = basicMatch3Players.getPositionOfMotherNature();
            //try to move MN on the same island
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch3Players.moveMotherNature(playerThree, basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature_1 )% basicMatch3Players.getGame().getArchipelagos().size())));
            //sets the game in order to put a professor on the board of player three and have influence on the isle where MN will be in the next move (oldPositionMotherNature +2)
            for(Student s : basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature_1 + getSteps(playerThree))% basicMatch3Players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                for(int i = 0; i< basicMatch3Players.getGame().getProfessors().size(); i++){
                    if (basicMatch3Players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(basicMatch3Players.getGame().getProfessors().get(i));
                        basicMatch3Players.getGame().getProfessors().remove(basicMatch3Players.getGame().getProfessors().get(i));
                    }
                }
            }
            //try to move MN to the next archipelago where playerThree has the most influence
            basicMatch3Players.moveMotherNature(playerThree, basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature_1 + getSteps(playerThree))% basicMatch3Players.getGame().getArchipelagos().size()));
            basicMatch3Players.getActionPhaseOrderOfPlayers().add(playerThree);
            int oldPositionMotherNature_2 = basicMatch3Players.getPositionOfMotherNature();
            //check if the tower is build correctly
            Assertions.assertEquals(basicMatch3Players.getGame().getWizardFromPlayer(playerThree), basicMatch3Players.getGame().getArchipelagos().get(basicMatch3Players.getPositionOfMotherNature()).getIsle().get(0).getTower().getProperty());
            //sets again the game in order to put a professor on the board of playerThee and have influence on the next isle where MN will be in the next move
            for(Student s : basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature_2 + 1)% basicMatch3Players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                for(int i = 0; i< basicMatch3Players.getGame().getProfessors().size(); i++){
                    if (basicMatch3Players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(basicMatch3Players.getGame().getProfessors().get(i));
                        basicMatch3Players.getGame().getProfessors().remove(basicMatch3Players.getGame().getProfessors().get(i));

                    }
                }
            }
            Archipelago archipelagoMerged = basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature_2 + 1)% basicMatch3Players.getGame().getArchipelagos().size());
            Archipelago previousArchipelagoMerged = basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature_2)% basicMatch3Players.getGame().getArchipelagos().size());
            Assertions.assertTrue(basicMatch3Players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            basicMatch3Players.moveMotherNature(playerThree, archipelagoMerged);
            basicMatch3Players.getActionPhaseOrderOfPlayers().add(playerThree);
            //check if the tower is build correctly and the merge is done
            Assertions.assertEquals(11, basicMatch3Players.getGame().getArchipelagos().size()); //size is decremented
            Assertions.assertTrue(basicMatch3Players.getGame().getArchipelagos().contains(archipelagoMerged));
            Assertions.assertFalse(basicMatch3Players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            Assertions.assertEquals(basicMatch3Players.getGame().getWizardFromPlayer(playerThree), basicMatch3Players.getGame().getArchipelagos().get(basicMatch3Players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().get(0).getTower().getProperty());
            Assertions.assertEquals(2, basicMatch3Players.getGame().getArchipelagos().get(basicMatch3Players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().size());

            //sets playerTwo the most influence on the ArchipelagoMerged

            for(Student s : archipelagoMerged.getStudentFromArchipelago()){
                boolean isInGame = false;
                for(int i = 0; i< basicMatch3Players.getGame().getProfessors().size(); i++){
                    if (basicMatch3Players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(basicMatch3Players.getGame().getProfessors().get(i));
                        basicMatch3Players.getGame().getProfessors().remove(basicMatch3Players.getGame().getProfessors().get(i));
                        isInGame = true;
                    }
                }
                if(!isInGame){ //check if it's not put in player 2 after the first student of the island
                    for(int i = 0; i< basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getBoard().getProfessorInTable().size(); i++){
                        if(basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getBoard().getProfessorInTable().get(i).getColor() == s.getColor()){
                            Professor professor = basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getBoard().removeProfessorFromTable(s.getColor());
                            basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(professor);
                        }
                    }

                }
            }
            archipelagoMerged.addStudentInArchipelago(new Student(basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getBoard().getProfessorInTable().get(0).getColor()));
            int oldPositionMotherNature_3 = basicMatch3Players.getPositionOfMotherNature();
            //move mother nature for 10 steps, in order to arrive to the archipelago before the archipelagoMerged
            //and replace playerTwo in the actionPhaseOrder list
            basicMatch3Players.moveMotherNature(playerThree, basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature_3+ basicMatch3Players.getGame().getArchipelagos().size() + getSteps(playerThree))% basicMatch3Players.getGame().getArchipelagos().size()));
            basicMatch3Players.getActionPhaseOrderOfPlayers().add(playerThree);
            basicMatch3Players.moveMotherNature(playerThree, basicMatch3Players.getGame().getArchipelagos().get((basicMatch3Players.getGame().getArchipelagos().indexOf(archipelagoMerged)+ basicMatch3Players.getGame().getArchipelagos().size() -1 )% basicMatch3Players.getGame().getArchipelagos().size()));
            basicMatch3Players.getActionPhaseOrderOfPlayers().add(playerThree);
            basicMatch3Players.getActionPhaseOrderOfPlayers().add(playerTwo);
            //place MN on archipelagoMerged and check if the replacement of the tower is correct
            basicMatch3Players.moveMotherNature(playerTwo, archipelagoMerged);
            Assertions.assertEquals(basicMatch3Players.getGame().getWizardFromPlayer(playerTwo), archipelagoMerged.getIsle().get(0).getTower().getProperty());
        });
    }

    @Test
    void checkVictory_NoTowers_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            basicMatch3Players.playAssistantsCard(playerThree, assistantsCards_3.get(9)); //5 step

            //remove all the tower from playerThree and call CheckVictory from moveMotherNature
            basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getBoard().getTowersInBoard().removeAll(basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getBoard().getTowersInBoard());
            Assertions.assertEquals(basicMatch3Players.getGame().getWizardFromPlayer(playerThree), basicMatch3Players.getGame().getWizardsWithLeastTowers().get(0));
            basicMatch3Players.getPositionOfMotherNature();

        });
    }

    @Test
    void checkVictory_NoStudents_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            basicMatch3Players.playAssistantsCard(playerThree, assistantsCards_3.get(9)); //5 step
            //remove all student from student bag
            basicMatch3Players.getGame().getStudentBag().getStudentsInBag().removeAll(basicMatch3Players.getGame().getStudentBag().getStudentsInBag());
            //call moveMotherNature
            Assertions.assertEquals(basicMatch3Players.getGame().getWizards(), basicMatch3Players.getGame().getWizardsWithLeastTowers());

            basicMatch3Players.getPositionOfMotherNature();

        });
    }

    @RepeatedTest(24)
    void checkVictory_LessThenThreeArchipelagos_Test() {
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            basicMatch3Players.playAssistantsCard(playerThree, assistantsCards_3.get(9)); //5 step

            //remove 9 archipelagos from the initial 12
            basicMatch3Players.getGame().getArchipelagos().removeAll(basicMatch3Players.getGame().getArchipelagos().subList(0, 9));
            //call motherNature
            Assertions.assertEquals(basicMatch3Players.getGame().getWizards(), basicMatch3Players.getGame().getWizardsWithLeastTowers());

            for(Archipelago a : basicMatch3Players.getGame().getArchipelagos()){
                a.setMotherNaturePresence(false);
            }
            //sets MN on the first archipelago
            basicMatch3Players.getGame().getArchipelagos().get(0).setMotherNaturePresence(true);
            basicMatch3Players.getGame().getMotherNature().setPosition(0);
            //check if the match will finish
            basicMatch3Players.getPositionOfMotherNature();

        });
    }
}

