package it.polimi.ingsw.ModelTest.MatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.*;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.EndMatchMessage;
import it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;


public class BasicMatchAndFactoryTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch3Players = factoryMatch.newMatch(3);
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final Player playerOne = new Player("usernameOne");
    private final Player playerTwo = new Player("usernameTwo");
    private final Player playerThree = new Player("usernameThree");


    public void gameSetter(){
        List<Player> players = new ArrayList<>();
        players.add(playerOne);
        players.add(playerTwo);
        Assertions.assertDoesNotThrow(() ->
                basicMatch2Players.setGame(players)
        );
        players.add(playerThree);
        if(basicMatch3Players.getNumberOfPlayers() == players.size())
            Assertions.assertDoesNotThrow(() ->
                 basicMatch3Players.setGame(players)
            );
        else
            Assertions.assertThrows(ExceptionGame.class, ()->basicMatch3Players.setGame(players));
    }
    public void printGame(){
        System.out.println("Archipelagos "+ basicMatch3Players.getGame().getArchipelagos().size());
        System.out.println(basicMatch3Players.getGame().getWizards() + "\n");
        for( Wizard w : basicMatch3Players.getGame().getWizards()){
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

        List<Player> playersTest = new ArrayList<>();
        playersTest.add(playerThree);
        playersTest.add(playerOne);
        playersTest.add(playerThree);
        Assertions.assertThrows(ExceptionGame.class, () -> basicMatch3Players.setTeams(playersTest));
        Assertions.assertThrows(ExceptionGame.class, basicMatch2Players::getTeams);
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
        for(Wizard wizard : basicMatch3Players.getGame().getWizards()) {
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
                    basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(1));
                    Assertions.assertThrows(ExceptionGame.class, ()->
                            basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(1))
                    );
                    basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(0));
                    Assertions.assertEquals(9, assistantsCards_1.size());
                    Assertions.assertEquals(9, assistantsCards_2.size());
                    Assertions.assertEquals(playerOne, basicMatch3Players.getActionPhaseOrderOfPlayers().get(1));
                    Assertions.assertEquals(playerTwo, basicMatch3Players.getActionPhaseOrderOfPlayers().get(0));
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

            basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            int oldPositionMotherNature = basicMatch3Players.getPositionOfMotherNature();
            //Try to move MN with more steps that are allowed for the playerOne
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch3Players.moveMotherNature(playerOne, basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)% basicMatch3Players.getGame().getArchipelagos().size())));
            //retry to move MN with a number of steps allowed for playerOne

            basicMatch3Players.moveMotherNature(playerOne, basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)% basicMatch3Players.getGame().getArchipelagos().size()));
            //try to move MN on the same island
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch3Players.moveMotherNature(playerOne, basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)% basicMatch3Players.getGame().getArchipelagos().size())));
            //sets the game in order to put a professor on the board of playerOne and have influence on the isle where MN will be in the next move (oldPositionMotherNature +2)
            for(Student s : basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)% basicMatch3Players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                boolean professorNotTaken = basicMatch3Players.getGame().getProfessors().stream().anyMatch(p -> p.getColor()==s.getColor());
                if(professorNotTaken) {
                    Professor professor = basicMatch3Players.getGame().getProfessors().stream().filter(p -> p.getColor()==s.getColor()).findAny().get();
                    basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(professor);
                }
            }
            basicMatch3Players.moveMotherNature(playerOne, basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)% basicMatch3Players.getGame().getArchipelagos().size()));
            //check if the tower is build correctly
            Assertions.assertEquals(basicMatch3Players.getGame().getWizardFromPlayer(playerOne), basicMatch3Players.getGame().getArchipelagos().get(basicMatch3Players.getPositionOfMotherNature()).getIsle().get(0).getTower().getProperty());
            //sets again the game in order to put a professor on the board of playerOne and have influence on the isle where MN will be in the next move (oldPositionMotherNature +3)
            for(Student s : basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + 3)% basicMatch3Players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                boolean professorNotTaken = basicMatch3Players.getGame().getProfessors().stream().anyMatch(p -> p.getColor()==s.getColor());
                if(professorNotTaken) {
                    Professor professor = basicMatch3Players.getGame().getProfessors().stream().filter(p -> p.getColor()==s.getColor()).findAny().get();
                    basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(professor);
                }
            }
            Archipelago archipelagoMerged = basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + 3)% basicMatch3Players.getGame().getArchipelagos().size());
            Archipelago previousArchipelagoMerged = basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + 2)% basicMatch3Players.getGame().getArchipelagos().size());
            Assertions.assertTrue(basicMatch3Players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            basicMatch3Players.moveMotherNature(playerOne, archipelagoMerged);
            //check if the tower is build correctly and the merge is done

            Assertions.assertEquals(11, basicMatch3Players.getGame().getArchipelagos().size()); //size is decremented
            Assertions.assertTrue(basicMatch3Players.getGame().getArchipelagos().contains(archipelagoMerged));
            Assertions.assertFalse(basicMatch3Players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            Assertions.assertEquals(basicMatch3Players.getGame().getWizardFromPlayer(playerOne), basicMatch3Players.getGame().getArchipelagos().get(basicMatch3Players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().get(0).getTower().getProperty());
            Assertions.assertEquals(2, basicMatch3Players.getGame().getArchipelagos().get(basicMatch3Players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().size());
        });
    }

    @Test
    void lookupArchipelago_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            int MNPosition= basicMatch2Players.getGame().getMotherNature().getPosition();
            Archipelago archipelagoToLook = basicMatch2Players.getGame().getArchipelagos().get((MNPosition + 3)%basicMatch2Players.getGame().getArchipelagos().size());
            Archipelago previousArchipelago = basicMatch2Players.getGame().getArchipelagos().get((MNPosition + 2)%basicMatch2Players.getGame().getArchipelagos().size());
            Archipelago nextArchipelago = basicMatch2Players.getGame().getArchipelagos().get((MNPosition + 4)%basicMatch2Players.getGame().getArchipelagos().size());

            Wizard wizard = basicMatch2Players.getGame().getWizards().get(0);
            basicMatch2Players.playAssistantsCard(basicMatch2Players.getPlayerFromWizard(wizard), AssistantsCards.CardTen);

            previousArchipelago.placeWizardsTower(wizard);
            nextArchipelago.placeWizardsTower(wizard);

            Assertions.assertEquals(12, basicMatch2Players.getGame().getArchipelagos().size());
            Assertions.assertEquals(0, archipelagoToLook.calculateInfluenceInArchipelago(wizard));

            basicMatch2Players.getGame().buildTower(wizard, archipelagoToLook);
            basicMatch2Players.lookUpArchipelago(archipelagoToLook);

            Assertions.assertEquals(10, basicMatch2Players.getGame().getArchipelagos().size());
            Assertions.assertEquals(3, archipelagoToLook.calculateInfluenceTowers(wizard));

            Assertions.assertFalse(basicMatch2Players.getGame().getArchipelagos().contains(previousArchipelago));
            Assertions.assertFalse(basicMatch2Players.getGame().getArchipelagos().contains(nextArchipelago));

        });
    }

    @Test
    void checkVictory_NoTowers_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            //remove all the tower from playerOne and call CheckVictory from moveMotherNature
            basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getBoard().getTowersInBoard().removeAll(basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getBoard().getTowersInBoard());
            Assertions.assertEquals(basicMatch3Players.getGame().getWizardFromPlayer(playerOne), basicMatch3Players.getGame().getWizardsWithLeastTowers().get(0));
            int oldPositionMotherNature = basicMatch3Players.getPositionOfMotherNature();
        });
    }

    @RepeatedTest(15)
    void checkVictory_NoStudents_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {

            class EndMatch implements Observer{
                public boolean endMatch = false;
                @Override
                public void update(Message message) {
                    if(message instanceof EndMatchMessage){
                        endMatch = true;
                    }
                }
            }

            EndMatch obs = new EndMatch();
            basicMatch3Players.addObserver(obs);
            List<AssistantsCards> assistantsCards_1 = basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            //remove all student from student bag
            basicMatch3Players.getGame().getStudentBag().getStudentsInBag().clear();
            //add a professors
            basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(new Professor(Color.BLUE));
            basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(new Professor(Color.RED));
            basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(new Professor(Color.GREEN));
            basicMatch3Players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(new Professor(Color.PINK));
            int oldPositionMotherNature = basicMatch3Players.getPositionOfMotherNature();
            Archipelago archipelagoToMove = basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)%basicMatch3Players.getGame().getArchipelagos().size());

            //call moveMotherNature
            List<Player> orderPlayer = basicMatch3Players.getActionPhaseOrderOfPlayers();
            Player lastPlayer = orderPlayer.get(orderPlayer.size()-1);
            basicMatch3Players.moveMotherNature(orderPlayer.get(0), archipelagoToMove);
            Assertions.assertFalse(obs.endMatch);

            oldPositionMotherNature = basicMatch3Players.getPositionOfMotherNature();
            archipelagoToMove = basicMatch3Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)%basicMatch3Players.getGame().getArchipelagos().size());
            basicMatch3Players.moveMotherNature(lastPlayer, archipelagoToMove);
            Assertions.assertTrue(obs.endMatch);



        });
    }

    @RepeatedTest(30)
    void checkVictory_LessThenThreeArchipelagos_Test() {
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch3Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch3Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();

            basicMatch3Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch3Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step

            //remove 9 archipelagos from the initial 12
            basicMatch3Players.getGame().getArchipelagos().removeAll(basicMatch3Players.getGame().getArchipelagos().subList(0, 9));
             //call motherNature
            Assertions.assertEquals(basicMatch3Players.getGame().getWizards(), basicMatch3Players.getGame().getWizardsWithLeastTowers());

            for(Archipelago a : basicMatch3Players.getGame().getArchipelagos()){
                a.setMotherNaturePresence(false);
            }
            basicMatch3Players.getGame().getArchipelagos().get(0).setMotherNaturePresence(true);
            basicMatch3Players.getGame().getMotherNature().setPosition(0);
            int oldPositionMotherNature = basicMatch3Players.getPositionOfMotherNature();


        });
    }
}
