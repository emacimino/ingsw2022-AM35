package it.polimi.ingsw.ModelTest.MatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.*;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;


public class BasicMatchAndFactoryTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final Player playerOne = new Player("nameOne", "usernameOne");
    private final Player playerTwo = new Player("nameTwo", "usernameTwo");
    private final Player playerThree = new Player("nameThree", "usernameThree");


    public void gameSetter(){
        List<Player> players = new ArrayList<>();
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        Assertions.assertDoesNotThrow(() ->
                basicMatch2Players.setGame(players)
        );
    }
    public void printGame(){
        System.out.println("Archipelagos "+ basicMatch2Players.getGame().getArchipelagos().size());
        System.out.println(basicMatch2Players.getGame().getWizards() + "\n");
        for( Wizard w : basicMatch2Players.getGame().getWizards()){
            System.out.println(w);
            System.out.println("towers " + w.getBoard().getTowersInBoard().size());
            System.out.println("assistantCard " + w.getRoundAssistantsCard());
            System.out.println("number of students in entrance " + w.getBoard().getStudentsInEntrance().size());

        }
    }
    @Test
    void verifyMatch2Players() {
        gameSetter();
        Assertions.assertEquals(2, basicMatch2Players.getNumberOfPlayers());
        Assertions.assertEquals(3, basicMatch2Players.getNumberOfMovableStudents());
        Assertions.assertEquals(2, basicMatch2Players.getNumberOfClouds());
        Assertions.assertEquals(8, basicMatch2Players.getNumberOfTowers());
        Assertions.assertEquals(7, basicMatch2Players.getNumberOfStudentInEntrance());
        Assertions.assertEquals(3, basicMatch2Players.getNumberOfStudentsOnCLoud());
        for(Wizard wizard : basicMatch2Players.getGame().getWizards()) {
            Assertions.assertDoesNotThrow(() ->
                Assertions.assertTrue(basicMatch2Players.getPlayers().contains(basicMatch2Players.getPlayerFromWizard(wizard)))
            );
        }

        Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch2Players.setTeamsOne(playerOne,playerTwo));
        Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch2Players.setTeamsTwo(playerOne,playerTwo));
        Assertions.assertThrows(ExceptionGame.class, basicMatch2Players::getTeams);
    }

    @Test
    void playAssistantsCard_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {

                    List<AssistantsCards> assistantsCards_1 = basicMatch2Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
                    List<AssistantsCards> assistantsCards_2 = basicMatch2Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
                    basicMatch2Players.playAssistantsCard(playerOne, assistantsCards_1.get(1));
                    Assertions.assertThrows(ExceptionGame.class, ()->
                            basicMatch2Players.playAssistantsCard(playerTwo, assistantsCards_2.get(1))
                    );
                    basicMatch2Players.playAssistantsCard(playerTwo, assistantsCards_2.get(0));
                    Assertions.assertEquals(9, assistantsCards_1.size());
                    Assertions.assertEquals(9, assistantsCards_2.size());
                    Assertions.assertEquals(playerOne, basicMatch2Players.getActionPhaseOrderOfPlayers().get(1));
                    Assertions.assertEquals(playerTwo, basicMatch2Players.getActionPhaseOrderOfPlayers().get(0));
                    Assertions.assertThrows(ExceptionGame.class, ()->
                            basicMatch2Players.playAssistantsCard(playerOne, assistantsCards_1.get(1))
                    );
                }
        );
    }

    @RepeatedTest(24)
    void moveMotherNature_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch2Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch2Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            basicMatch2Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch2Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            int oldPositionMotherNature = basicMatch2Players.getPositionOfMotherNature();
            //Try to move MN with more steps that are allowed for the playerOne
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch2Players.moveMotherNature(playerOne, basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)% basicMatch2Players.getGame().getArchipelagos().size())));
            //retry to move MN with a number of steps allowed for playerOne

            basicMatch2Players.moveMotherNature(playerOne, basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)% basicMatch2Players.getGame().getArchipelagos().size()));
            //try to move MN on the same island
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch2Players.moveMotherNature(playerOne, basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)% basicMatch2Players.getGame().getArchipelagos().size())));
            //sets the game in order to put a professor on the board of playerOne and have influence on the isle where MN will be in the next move (oldPositionMotherNature +2)
            for(Student s : basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)% basicMatch2Players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                boolean professorNotTaken = basicMatch2Players.getGame().getProfessors().stream().anyMatch(p -> p.getColor()==s.getColor());
                if(professorNotTaken) {
                    Professor professor = basicMatch2Players.getGame().getProfessors().stream().filter(p -> p.getColor()==s.getColor()).findAny().get();
                    basicMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(professor);
                }
            }
            basicMatch2Players.moveMotherNature(playerOne, basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)% basicMatch2Players.getGame().getArchipelagos().size()));
            //check if the tower is build correctly
            Assertions.assertEquals(basicMatch2Players.getGame().getWizardFromPlayer(playerOne), basicMatch2Players.getGame().getArchipelagos().get(basicMatch2Players.getPositionOfMotherNature()).getIsle().get(0).getTower().getProperty());
            //sets again the game in order to put a professor on the board of playerOne and have influence on the isle where MN will be in the next move (oldPositionMotherNature +3)
            for(Student s : basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 3)% basicMatch2Players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                boolean professorNotTaken = basicMatch2Players.getGame().getProfessors().stream().anyMatch(p -> p.getColor()==s.getColor());
                if(professorNotTaken) {
                    Professor professor = basicMatch2Players.getGame().getProfessors().stream().filter(p -> p.getColor()==s.getColor()).findAny().get();
                    basicMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(professor);
                }
            }
            Archipelago archipelagoMerged = basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 3)% basicMatch2Players.getGame().getArchipelagos().size());
            Archipelago previousArchipelagoMerged = basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)% basicMatch2Players.getGame().getArchipelagos().size());
            Assertions.assertTrue(basicMatch2Players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            basicMatch2Players.moveMotherNature(playerOne, archipelagoMerged);
            //check if the tower is build correctly and the merge is done

            Assertions.assertEquals(11, basicMatch2Players.getGame().getArchipelagos().size()); //size is decremented
            Assertions.assertTrue(basicMatch2Players.getGame().getArchipelagos().contains(archipelagoMerged));
            Assertions.assertFalse(basicMatch2Players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            Assertions.assertEquals(basicMatch2Players.getGame().getWizardFromPlayer(playerOne), basicMatch2Players.getGame().getArchipelagos().get(basicMatch2Players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().get(0).getTower().getProperty());
            Assertions.assertEquals(2, basicMatch2Players.getGame().getArchipelagos().get(basicMatch2Players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().size());
        });
    }

    @Test
    void checkVictory_NoTowers_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch2Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch2Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            basicMatch2Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch2Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            //remove all the tower from playerOne and call CheckVictory from moveMotherNature
            basicMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().getTowersInBoard().removeAll(basicMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().getTowersInBoard());
            Assertions.assertEquals(basicMatch2Players.getGame().getWizardFromPlayer(playerOne), basicMatch2Players.getGame().getWizardsWithLeastTowers().get(0));
            int oldPositionMotherNature = basicMatch2Players.getPositionOfMotherNature();
            basicMatch2Players.moveMotherNature(playerOne, basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)% basicMatch2Players.getGame().getArchipelagos().size()));
        });
    }

    @Test
    void checkVictory_NoStudents_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch2Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch2Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            basicMatch2Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch2Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            //remove all student from student bag
            basicMatch2Players.getGame().getStudentBag().getStudentsInBag().removeAll(basicMatch2Players.getGame().getStudentBag().getStudentsInBag());
            //call moveMotherNature
            Assertions.assertEquals(basicMatch2Players.getGame().getWizards(), basicMatch2Players.getGame().getWizardsWithLeastTowers());

            int oldPositionMotherNature = basicMatch2Players.getPositionOfMotherNature();
            basicMatch2Players.moveMotherNature(playerOne, basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)% basicMatch2Players.getGame().getArchipelagos().size()));

        });
    }

    @RepeatedTest(30)
    void checkVictory_LessThenThreeArchipelagos_Test() {
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch2Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch2Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            basicMatch2Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch2Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            //remove 9 archipelagos from the initial 12
            basicMatch2Players.getGame().getArchipelagos().removeAll(basicMatch2Players.getGame().getArchipelagos().subList(0, 9));
             //call motherNature
            Assertions.assertEquals(basicMatch2Players.getGame().getWizards(), basicMatch2Players.getGame().getWizardsWithLeastTowers());

            for(Archipelago a : basicMatch2Players.getGame().getArchipelagos()){
                a.setMotherNaturePresence(false);
            }
            basicMatch2Players.getGame().getArchipelagos().get(0).setMotherNaturePresence(true);
            basicMatch2Players.getGame().getMotherNature().setPosition(0);
            int oldPositionMotherNature = basicMatch2Players.getPositionOfMotherNature();
            basicMatch2Players.moveMotherNature(playerOne, basicMatch2Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)% basicMatch2Players.getGame().getArchipelagos().size()));
        });
    }
}