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


public class MatchAndFactoryTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final Match match2players = factoryMatch.newMatch(2);
    private final Player playerOne = new Player("nameOne", "usernameOne");
    private final Player playerTwo = new Player("nameTwo", "usernameTwo");
    private final Player playerThree = new Player("nameThree", "usernameThree");


    public void gameSetter(){
        List<Player> players = new ArrayList<>();
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        Assertions.assertDoesNotThrow(() ->
                match2players.setGame(players)
        );
    }
    public void printGame(){
        System.out.println("Archipelagos "+ match2players.getGame().getArchipelagos().size());
        System.out.println(match2players.getGame().getWizards() + "\n");
        for( Wizard w : match2players.getGame().getWizards()){
            System.out.println(w);
            System.out.println("towers " + w.getBoard().getTowersInBoard().size());
            System.out.println("assistantCard " + w.getRoundAssistantsCard());
            System.out.println("number of students in entrance " + w.getBoard().getStudentsInEntrance().size());

        }
    }
    @Test
    void verifyMatch2Players() {
        gameSetter();
        Assertions.assertEquals(2, match2players.getNumberOfPlayers());
        Assertions.assertEquals(3, match2players.getNumberOfMovableStudents());
        Assertions.assertEquals(2, match2players.getNumberOfClouds());
        Assertions.assertEquals(8, match2players.getNumberOfTowers());
        Assertions.assertEquals(7, match2players.getNumberOfStudentInEntrance());
        Assertions.assertEquals(3, match2players.getNumberOfStudentsOnCLoud());
        for(Wizard wizard : match2players.getGame().getWizards()) {
            Assertions.assertDoesNotThrow(() ->
                Assertions.assertTrue(match2players.getPlayers().contains(match2players.getPlayerFromWizard(wizard)))
            );
        }

        Assertions.assertThrows(ExceptionGame.class, ()->match2players.setTeamsOne(playerOne,playerTwo));
        Assertions.assertThrows(ExceptionGame.class, ()->match2players.setTeamsTwo(playerOne,playerTwo));
        Assertions.assertThrows(ExceptionGame.class, match2players::getTeams);
    }

    @Test
    void playAssistantsCard_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {

                    List<AssistantsCards> assistantsCards_1 = match2players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
                    List<AssistantsCards> assistantsCards_2 = match2players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
                    match2players.playAssistantsCard(playerOne, assistantsCards_1.get(1));
                    Assertions.assertThrows(ExceptionGame.class, ()->
                            match2players.playAssistantsCard(playerTwo, assistantsCards_2.get(1))
                    );
                    match2players.playAssistantsCard(playerTwo, assistantsCards_2.get(0));
                    Assertions.assertEquals(9, assistantsCards_1.size());
                    Assertions.assertEquals(9, assistantsCards_2.size());
                    Assertions.assertEquals(playerOne, match2players.getActionPhaseOrderOfPlayers().get(1));
                    Assertions.assertEquals(playerTwo, match2players.getActionPhaseOrderOfPlayers().get(0));
                    Assertions.assertThrows(ExceptionGame.class, ()->
                            match2players.playAssistantsCard(playerOne, assistantsCards_1.get(1))
                    );
                }
        );
    }

    @RepeatedTest(24)
    void moveMotherNature_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match2players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match2players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            match2players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match2players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            int oldPositionMotherNature = match2players.getPositionOfMotherNature();
            //Try to move MN with more steps that are allowed for the playerOne
            Assertions.assertThrows(ExceptionGame.class, ()-> match2players.moveMotherNature(playerOne, match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)%match2players.getGame().getArchipelagos().size())));
            //retry to move MN with a number of steps allowed for playerOne

            match2players.moveMotherNature(playerOne, match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)%match2players.getGame().getArchipelagos().size()));
            //try to move MN on the same island
            Assertions.assertThrows(ExceptionGame.class, ()->match2players.moveMotherNature(playerOne, match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)%match2players.getGame().getArchipelagos().size())));
            //sets the game in order to put a professor on the board of playerOne and have influence on the isle where MN will be in the next move (oldPositionMotherNature +2)
            for(Student s : match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)%match2players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                boolean professorNotTaken = match2players.getGame().getProfessors().stream().anyMatch(p -> p.getColor()==s.getColor());
                if(professorNotTaken) {
                    Professor professor = match2players.getGame().getProfessors().stream().filter(p -> p.getColor()==s.getColor()).findAny().get();
                    match2players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(professor);
                }
            }
            match2players.moveMotherNature(playerOne, match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)%match2players.getGame().getArchipelagos().size()));
            //check if the tower is build correctly
            Assertions.assertEquals(match2players.getGame().getWizardFromPlayer(playerOne), match2players.getGame().getArchipelagos().get(match2players.getPositionOfMotherNature()).getIsle().get(0).getTower().getProperty());
            //sets again the game in order to put a professor on the board of playerOne and have influence on the isle where MN will be in the next move (oldPositionMotherNature +3)
            for(Student s : match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 3)%match2players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                boolean professorNotTaken = match2players.getGame().getProfessors().stream().anyMatch(p -> p.getColor()==s.getColor());
                if(professorNotTaken) {
                    Professor professor = match2players.getGame().getProfessors().stream().filter(p -> p.getColor()==s.getColor()).findAny().get();
                    match2players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(professor);
                }
            }
            Archipelago archipelagoMerged = match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 3)%match2players.getGame().getArchipelagos().size());
            Archipelago previousArchipelagoMerged = match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)%match2players.getGame().getArchipelagos().size());
            Assertions.assertTrue(match2players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            match2players.moveMotherNature(playerOne, archipelagoMerged);
            //check if the tower is build correctly and the merge is done

            Assertions.assertEquals(11, match2players.getGame().getArchipelagos().size()); //size is decremented
            Assertions.assertTrue(match2players.getGame().getArchipelagos().contains(archipelagoMerged));
            Assertions.assertFalse(match2players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            Assertions.assertEquals(match2players.getGame().getWizardFromPlayer(playerOne), match2players.getGame().getArchipelagos().get(match2players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().get(0).getTower().getProperty());
            Assertions.assertEquals(2, match2players.getGame().getArchipelagos().get(match2players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().size());
        });
    }

    @Test
    void checkVictory_NoTowers_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match2players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match2players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            match2players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match2players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            //remove all the tower from playerOne and call CheckVictory from moveMotherNature
            match2players.getGame().getWizardFromPlayer(playerOne).getBoard().getTowersInBoard().removeAll(match2players.getGame().getWizardFromPlayer(playerOne).getBoard().getTowersInBoard());
            Assertions.assertEquals(match2players.getGame().getWizardFromPlayer(playerOne), match2players.getGame().getWizardsWithLeastTowers().get(0));
            int oldPositionMotherNature = match2players.getPositionOfMotherNature();
            match2players.moveMotherNature(playerOne, match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)%match2players.getGame().getArchipelagos().size()));
        });
    }

    @Test
    void checkVictory_NoStudents_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match2players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match2players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            match2players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match2players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            //remove all student from student bag
            match2players.getGame().getStudentBag().getStudentsInBag().removeAll(match2players.getGame().getStudentBag().getStudentsInBag());
            //call moveMotherNature
            Assertions.assertEquals(match2players.getGame().getWizards(), match2players.getGame().getWizardsWithLeastTowers());

            int oldPositionMotherNature = match2players.getPositionOfMotherNature();
            match2players.moveMotherNature(playerOne, match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)%match2players.getGame().getArchipelagos().size()));

        });
    }

    @RepeatedTest(30)
    void checkVictory_LessThenThreeArchipelagos_Test() {
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match2players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match2players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            match2players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match2players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            //remove 9 archipelagos from the initial 12
            match2players.getGame().getArchipelagos().removeAll(match2players.getGame().getArchipelagos().subList(0, 9));
             //call motherNature
            Assertions.assertEquals(match2players.getGame().getWizards(), match2players.getGame().getWizardsWithLeastTowers());

            for(Archipelago a : match2players.getGame().getArchipelagos()){
                a.setMotherNaturePresence(false);
            }
            match2players.getGame().getArchipelagos().get(0).setMotherNaturePresence(true);
            match2players.getGame().getMotherNature().setPosition(0);
            int oldPositionMotherNature = match2players.getPositionOfMotherNature();
            match2players.moveMotherNature(playerOne, match2players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)%match2players.getGame().getArchipelagos().size()));
        });
    }
}
