package it.polimi.ingsw.ModelTest.MatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.EndMatchMessage;
import it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to test the basic match creation and the 4 players match creation
 */
public class BasicMatchFourPlayerTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch4Players = factoryMatch.newMatch(4);
    private final List<Player> players = new ArrayList<>();
    private final Player playerOne = new Player("usernameOne");
    private final Player playerTwo = new Player("usernameTwo");
    private final Player playerThree = new Player("usernameThree");
    private final Player playerFour = new Player("usernameFour");

    public int getSteps(Player player) throws ExceptionGame{
        Wizard wizard = basicMatch4Players.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }
    public void gameSetter(){
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        players.add(playerFour);
        Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch4Players.setGame(players));
        Assertions.assertDoesNotThrow(()-> basicMatch4Players.setTeams(players));
        Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch4Players.setTeams(players));
        Assertions.assertDoesNotThrow(()-> basicMatch4Players.setGame(players));
    }
    public void printGame(){
        System.out.println("\n PRINTING STATE OF GAME: ");
        System.out.println("professor in game: "+ basicMatch4Players.getGame().getProfessors());
        System.out.println("action order in round: " + basicMatch4Players.getActionPhaseOrderOfPlayers());
        System.out.println("Archipelagos "+ basicMatch4Players.getGame().getArchipelagos().size());
        System.out.println("position of MN " + basicMatch4Players.getPositionOfMotherNature());
        System.out.println(basicMatch4Players.getGame().getWizards());
        for( Wizard w : basicMatch4Players.getGame().getWizards()){
            System.out.println("--->"+w);
            System.out.println("towers " + w.getBoard().getTowersInBoard().size());
            System.out.println("assistantCard " + w.getRoundAssistantsCard());
            System.out.println("number of students in entrance " + w.getBoard().getStudentsInEntrance().size());
            System.out.println("professors in table : " +w.getBoard().getProfessorInTable());
        }
        System.out.println("END OF PRINT \n");
    }


    @Test
    void verifyMatch4Players() {
        gameSetter();
        Assertions.assertEquals(4, basicMatch4Players.getNumberOfPlayers());
        Assertions.assertEquals(3, basicMatch4Players.getNumberOfMovableStudents());
        Assertions.assertEquals(4, basicMatch4Players.getNumberOfClouds());
        Assertions.assertEquals(8, basicMatch4Players.getNumberOfTowers());
        Assertions.assertEquals(7, basicMatch4Players.getNumberOfStudentInEntrance());
        Assertions.assertEquals(3, basicMatch4Players.getNumberOfStudentsOnCLoud());
        for(Wizard wizard : basicMatch4Players.getGame().getWizards()) {
            Assertions.assertDoesNotThrow(() ->
                    Assertions.assertTrue(basicMatch4Players.getPlayers().contains(basicMatch4Players.getPlayerFromWizard(wizard)))
            );
        }

    }

    @Test
    void playAssistantsCard_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch4Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch4Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_4 = basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();
            basicMatch4Players.playAssistantsCard(playerOne, assistantsCards_1.get(1));
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch4Players.playAssistantsCard(playerTwo, assistantsCards_2.get(1)));
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch4Players.playAssistantsCard(playerThree, assistantsCards_3.get(1)));
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch4Players.playAssistantsCard(playerFour, assistantsCards_4.get(1)));
            basicMatch4Players.playAssistantsCard(playerTwo, assistantsCards_2.get(5));
            basicMatch4Players.playAssistantsCard(playerThree, assistantsCards_3.get(0));
            basicMatch4Players.playAssistantsCard(playerFour, assistantsCards_4.get(3));
            Assertions.assertEquals(9, assistantsCards_1.size());
            Assertions.assertEquals(9, assistantsCards_2.size());
            Assertions.assertEquals(9, assistantsCards_3.size());
            Assertions.assertEquals(9, assistantsCards_4.size());
            Assertions.assertEquals(playerOne, basicMatch4Players.getActionPhaseOrderOfPlayers().get(1));
            Assertions.assertEquals(playerTwo, basicMatch4Players.getActionPhaseOrderOfPlayers().get(3));
            Assertions.assertEquals(playerThree, basicMatch4Players.getActionPhaseOrderOfPlayers().get(0));
            Assertions.assertEquals(playerFour, basicMatch4Players.getActionPhaseOrderOfPlayers().get(2));
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch4Players.playAssistantsCard(playerOne, assistantsCards_1.get(1)));
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch4Players.playAssistantsCard(playerFour, assistantsCards_4.get(5)));
        });
    }

    @RepeatedTest(24)
    void moveMotherNature_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch4Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch4Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_4 = basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            basicMatch4Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch4Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            basicMatch4Players.playAssistantsCard(playerThree, assistantsCards_3.get(3)); //2 steps
            basicMatch4Players.playAssistantsCard(playerFour, assistantsCards_4.get(9));//5 steps

            int oldPositionMotherNature = basicMatch4Players.getPositionOfMotherNature();
            //Try to move MN with more steps that are allowed for the playerFour
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch4Players.moveMotherNature(playerFour, basicMatch4Players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerFour) +1)% basicMatch4Players.getGame().getArchipelagos().size())));
            Assertions.assertEquals(oldPositionMotherNature, basicMatch4Players.getPositionOfMotherNature());
            //retry to move MN with a number of steps allowed for playerFour
            basicMatch4Players.moveMotherNature(playerFour, basicMatch4Players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerFour))% basicMatch4Players.getGame().getArchipelagos().size()));
            basicMatch4Players.getActionPhaseOrderOfPlayers().add(playerFour);
            int oldPositionMotherNature_1 = basicMatch4Players.getPositionOfMotherNature();
            //try to move MN on the same island
            Assertions.assertThrows(ExceptionGame.class, ()-> basicMatch4Players.moveMotherNature(playerFour, basicMatch4Players.getGame().getArchipelagos().get((oldPositionMotherNature_1)% basicMatch4Players.getGame().getArchipelagos().size())));
            Assertions.assertEquals(oldPositionMotherNature_1 , basicMatch4Players.getPositionOfMotherNature());
            //sets the game in order to put a professor on the board of playerFour and have influence on the isle where MN will be in the next move (NB THE SUCCESSIVE ISLE IS THE OPPOSITE OF THE STARTING ISLE OF MN)
            int nextPositionMN = oldPositionMotherNature_1 + 2;
            for(Student s : basicMatch4Players.getGame().getArchipelagos().get((nextPositionMN)% basicMatch4Players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                for(int i = 0; i< basicMatch4Players.getGame().getProfessors().size(); i++){
                    if (basicMatch4Players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getBoard().setProfessorInTable(basicMatch4Players.getGame().getProfessors().get(i));
                        basicMatch4Players.getGame().getProfessors().remove(basicMatch4Players.getGame().getProfessors().get(i));
                    }
                }
            }

            basicMatch4Players.moveMotherNature(playerFour, basicMatch4Players.getGame().getArchipelagos().get((nextPositionMN)% basicMatch4Players.getGame().getArchipelagos().size()));
            basicMatch4Players.getActionPhaseOrderOfPlayers().add(playerFour);

            //check if the tower is build correctly, the towers are property of the captains of the team of playerFour (playerThree)
            Assertions.assertEquals(basicMatch4Players.getGame().getWizardFromPlayer(playerThree), basicMatch4Players.getGame().getArchipelagos().get(basicMatch4Players.getPositionOfMotherNature()).getIsle().get(0).getTower().getProperty());
            int oldPositionMotherNature_2 = basicMatch4Players.getPositionOfMotherNature();
            //sets again the game in order to put a professor on the board of playerThree and have influence on the isle where MN will be in the next move (oldPositionMotherNature_2 +1)
            for(Student s : basicMatch4Players.getGame().getArchipelagos().get((oldPositionMotherNature_2 + 1)% basicMatch4Players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                boolean isInGame = false;
                for(int i = 0; i< basicMatch4Players.getGame().getProfessors().size(); i++){
                    if (basicMatch4Players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(basicMatch4Players.getGame().getProfessors().get(i));
                        basicMatch4Players.getGame().getProfessors().remove(basicMatch4Players.getGame().getProfessors().get(i));
                        isInGame = true;
                    }
                }
                if(!isInGame){  //controllare che non ci sia gia messo in player4
                    for(int i = 0; i< basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getBoard().getProfessorInTable().size(); i++){
                        if(basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getBoard().getProfessorInTable().get(i).getColor() == s.getColor()){
                            Professor professor = basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getBoard().removeProfessorFromTable(s.getColor());
                            basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(professor);
                        }
                    }

                }
            }

            Archipelago archipelagoMerged = basicMatch4Players.getGame().getArchipelagos().get((oldPositionMotherNature_2 + 1)% basicMatch4Players.getGame().getArchipelagos().size());
            Archipelago previousArchipelagoMerged = basicMatch4Players.getGame().getArchipelagos().get((oldPositionMotherNature_2)% basicMatch4Players.getGame().getArchipelagos().size());
            Assertions.assertTrue(basicMatch4Players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            basicMatch4Players.moveMotherNature(playerThree, archipelagoMerged);
            basicMatch4Players.getActionPhaseOrderOfPlayers().add(playerThree);

            //check if the tower is build correctly and the merge is done
            Assertions.assertEquals(11, basicMatch4Players.getGame().getArchipelagos().size()); //size is decremented
            Assertions.assertTrue(basicMatch4Players.getGame().getArchipelagos().contains(archipelagoMerged));
            Assertions.assertFalse(basicMatch4Players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            Assertions.assertEquals(basicMatch4Players.getGame().getWizardFromPlayer(playerThree), basicMatch4Players.getGame().getArchipelagos().get(basicMatch4Players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().get(0).getTower().getProperty());
            Assertions.assertEquals(6, basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getBoard().getTowersInBoard().size());
            Assertions.assertEquals(2, basicMatch4Players.getGame().getArchipelagos().get(basicMatch4Players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().size());
            int oldPositionMotherNature_3 = basicMatch4Players.getPositionOfMotherNature();

            //sets playerTwo the most influence on the ArchipelagoMerged
            for(Student s : archipelagoMerged.getStudentFromArchipelago()){
                boolean isInGame = false;
                for(int i = 0; i< basicMatch4Players.getGame().getProfessors().size(); i++){
                    if (basicMatch4Players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        basicMatch4Players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(basicMatch4Players.getGame().getProfessors().get(i));
                        basicMatch4Players.getGame().getProfessors().remove(basicMatch4Players.getGame().getProfessors().get(i));
                        isInGame = true;
                    }
                }
                if(!isInGame){
                    for(int i = 0; i< basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getBoard().getProfessorInTable().size(); i++){
                        if(basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getBoard().getProfessorInTable().get(i).getColor() == s.getColor()){
                            Professor professor = basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getBoard().removeProfessorFromTable(s.getColor());
                            basicMatch4Players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(professor);
                        }
                    }for(int i = 0; i< basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getBoard().getProfessorInTable().size(); i++){
                        if(basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getBoard().getProfessorInTable().get(i).getColor() == s.getColor()){
                            Professor professor = basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getBoard().removeProfessorFromTable(s.getColor());
                            basicMatch4Players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(professor);
                        }
                    }

                }
            }
            //move mother nature for 10 steps, in order to arrive to the archipelago before the archipelagoMerged
            //and replace playerTwo in the actionPhaseOrder list

            archipelagoMerged.addStudentInArchipelago(new Student(basicMatch4Players.getGame().getWizardFromPlayer(playerTwo).getBoard().getProfessorInTable().get(0).getColor()));
            basicMatch4Players.moveMotherNature(playerFour, basicMatch4Players.getGame().getArchipelagos().get((oldPositionMotherNature_3+ basicMatch4Players.getGame().getArchipelagos().size() + getSteps(playerFour))% basicMatch4Players.getGame().getArchipelagos().size()));
            basicMatch4Players.getActionPhaseOrderOfPlayers().add(playerFour);
            basicMatch4Players.moveMotherNature(playerFour, basicMatch4Players.getGame().getArchipelagos().get((basicMatch4Players.getGame().getArchipelagos().indexOf(archipelagoMerged)+ basicMatch4Players.getGame().getArchipelagos().size() -1 )% basicMatch4Players.getGame().getArchipelagos().size()));
            basicMatch4Players.getActionPhaseOrderOfPlayers().add(playerFour);
            basicMatch4Players.getActionPhaseOrderOfPlayers().add(playerTwo);
            //place MN on archipelagoMerged and check if the replacement of the tower is correct
            basicMatch4Players.moveMotherNature(playerTwo, archipelagoMerged);
            Assertions.assertEquals(basicMatch4Players.getGame().getWizardFromPlayer(playerOne), archipelagoMerged.getIsle().get(0).getTower().getProperty());

        });
    }

    @Test
    void checkVictory_NoTowers_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch4Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch4Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_4 = basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            basicMatch4Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch4Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            basicMatch4Players.playAssistantsCard(playerThree, assistantsCards_3.get(3)); //2 steps
            basicMatch4Players.playAssistantsCard(playerFour, assistantsCards_4.get(9));//5 steps

            //remove all the tower from playerThree and call CheckVictory from moveMotherNature
            basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getBoard().getTowersInBoard().removeAll(basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getBoard().getTowersInBoard());
            int oldPositionMotherNature = basicMatch4Players.getPositionOfMotherNature();

        });
    }

    @RepeatedTest(15)
    void checkVictory_NoStudents_Test(){
        System.out.println();
        gameSetter();
        class EndMatch implements Observer {
            public boolean endMatch = false;
            @Override
            public void update(Message message) {
                if(message instanceof EndMatchMessage){
                    endMatch = true;
                }
            }
        }

        EndMatch obs = new EndMatch();
        basicMatch4Players.addObserver(obs);
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch4Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch4Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_4 = basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            basicMatch4Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch4Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            basicMatch4Players.playAssistantsCard(playerThree, assistantsCards_3.get(3)); //2 steps
            basicMatch4Players.playAssistantsCard(playerFour, assistantsCards_4.get(9));//5 steps
            //remove all student from student bag
            basicMatch4Players.getGame().getStudentBag().getStudentsInBag().removeAll(basicMatch4Players.getGame().getStudentBag().getStudentsInBag());
            Assertions.assertTrue(basicMatch4Players.getGame().getStudentBag().getStudentsInBag().isEmpty());
            basicMatch4Players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(new Professor(Color.BLUE));
            basicMatch4Players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(new Professor(Color.RED));
            basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getBoard().setProfessorInTable(new Professor(Color.GREEN));
            basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(new Professor(Color.PINK));
            basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getBoard().setProfessorInTable(new Professor(Color.YELLOW));
            int oldPositionMotherNature = basicMatch4Players.getPositionOfMotherNature();
            Archipelago archipelagoToMove = basicMatch4Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)%basicMatch4Players.getGame().getArchipelagos().size());

            //call moveMotherNature
            List<Player> orderPlayer = basicMatch4Players.getActionPhaseOrderOfPlayers();
            Player lastPlayer = orderPlayer.get(orderPlayer.size()-1);
            basicMatch4Players.moveMotherNature(orderPlayer.get(0), archipelagoToMove);
            Assertions.assertFalse(obs.endMatch);

            oldPositionMotherNature = basicMatch4Players.getPositionOfMotherNature();
            archipelagoToMove = basicMatch4Players.getGame().getArchipelagos().get((oldPositionMotherNature + 1)%basicMatch4Players.getGame().getArchipelagos().size());
            basicMatch4Players.moveMotherNature(lastPlayer, archipelagoToMove);
            Assertions.assertTrue(obs.endMatch);

        });
    }

    @RepeatedTest(24)
    void checkVictory_LessThenThreeArchipelagos_Test() {
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = basicMatch4Players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = basicMatch4Players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_4 = basicMatch4Players.getGame().getWizardFromPlayer(playerFour).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = basicMatch4Players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            basicMatch4Players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            basicMatch4Players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            basicMatch4Players.playAssistantsCard(playerThree, assistantsCards_3.get(3)); //2 steps
            basicMatch4Players.playAssistantsCard(playerFour, assistantsCards_4.get(9));//5 steps

            //remove 9 archipelagos from the initial 12
            basicMatch4Players.getGame().getArchipelagos().removeAll(basicMatch4Players.getGame().getArchipelagos().subList(0, 9));
            //call motherNature
            for(Archipelago a : basicMatch4Players.getGame().getArchipelagos()){
                a.setMotherNaturePresence(false);
            }
            //sets MN on the first archipelago
            basicMatch4Players.getGame().getArchipelagos().get(0).setMotherNaturePresence(true);
            basicMatch4Players.getGame().getMotherNature().setPosition(0);
            //check if the match will finish
            int oldPositionMotherNature = basicMatch4Players.getPositionOfMotherNature();

        });
    }
}
