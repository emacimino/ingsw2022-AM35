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

public class MatchFourPlayerTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final Match match4players = factoryMatch.newMatch(4);
    private final List<Player> players = new ArrayList<>();
    private final Player playerOne = new Player("nameOne", "usernameOne");
    private final Player playerTwo = new Player("nameTwo", "usernameTwo");
    private final Player playerThree = new Player("nameThree", "usernameThree");
    private final Player playerFour = new Player("nameFour", "usernameFour");

    public int getSteps(Player player) throws ExceptionGame{
        Wizard wizard = match4players.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }
    public void gameSetter(){
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        players.add(playerFour);
        Assertions.assertThrows(ExceptionGame.class, ()->match4players.setGame(players));
        Assertions.assertDoesNotThrow(()->match4players.setTeamsOne(playerOne,playerTwo));
        Assertions.assertThrows(ExceptionGame.class, ()->match4players.setTeamsTwo(playerOne,playerTwo));
        Assertions.assertDoesNotThrow(()->match4players.setTeamsTwo(playerThree,playerFour));
        Assertions.assertDoesNotThrow(()->match4players.setGame(players));
    }
    public void printGame(){
        System.out.println("\n PRINTING STATE OF GAME: ");
        System.out.println("professor in game: "+match4players.getGame().getProfessors());
        System.out.println("action order in round: " + match4players.getActionPhaseOrderOfPlayers());
        System.out.println("Archipelagos "+ match4players.getGame().getArchipelagos().size());
        System.out.println("position of MN " + match4players.getPositionOfMotherNature());
        System.out.println(match4players.getGame().getWizards());
        for( Wizard w : match4players.getGame().getWizards()){
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
        Assertions.assertEquals(4, match4players.getNumberOfPlayers());
        Assertions.assertEquals(3, match4players.getNumberOfMovableStudents());
        Assertions.assertEquals(4, match4players.getNumberOfClouds());
        Assertions.assertEquals(8, match4players.getNumberOfTowers());
        Assertions.assertEquals(7, match4players.getNumberOfStudentInEntrance());
        Assertions.assertEquals(3, match4players.getNumberOfStudentsOnCLoud());
        for(Wizard wizard : match4players.getGame().getWizards()) {
            Assertions.assertDoesNotThrow(() ->
                    Assertions.assertTrue(match4players.getPlayers().contains(match4players.getPlayerFromWizard(wizard)))
            );
        }

    }

    @Test
    void playAssistantsCard_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match4players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match4players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_4 = match4players.getGame().getWizardFromPlayer(playerFour).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = match4players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();
            match4players.playAssistantsCard(playerOne, assistantsCards_1.get(1));
            Assertions.assertThrows(ExceptionGame.class, ()-> match4players.playAssistantsCard(playerTwo, assistantsCards_2.get(1)));
            Assertions.assertThrows(ExceptionGame.class, ()-> match4players.playAssistantsCard(playerThree, assistantsCards_3.get(1)));
            Assertions.assertThrows(ExceptionGame.class, ()-> match4players.playAssistantsCard(playerFour, assistantsCards_4.get(1)));
            match4players.playAssistantsCard(playerTwo, assistantsCards_2.get(5));
            match4players.playAssistantsCard(playerThree, assistantsCards_3.get(0));
            match4players.playAssistantsCard(playerFour, assistantsCards_4.get(3));
            Assertions.assertEquals(9, assistantsCards_1.size());
            Assertions.assertEquals(9, assistantsCards_2.size());
            Assertions.assertEquals(9, assistantsCards_3.size());
            Assertions.assertEquals(9, assistantsCards_4.size());
            Assertions.assertEquals(playerOne, match4players.getActionPhaseOrderOfPlayers().get(1));
            Assertions.assertEquals(playerTwo, match4players.getActionPhaseOrderOfPlayers().get(3));
            Assertions.assertEquals(playerThree, match4players.getActionPhaseOrderOfPlayers().get(0));
            Assertions.assertEquals(playerFour, match4players.getActionPhaseOrderOfPlayers().get(2));
            Assertions.assertThrows(ExceptionGame.class, ()-> match4players.playAssistantsCard(playerOne, assistantsCards_1.get(1)));
            Assertions.assertThrows(ExceptionGame.class, ()-> match4players.playAssistantsCard(playerFour, assistantsCards_4.get(5)));
        });
    }

    @RepeatedTest(24)
    void moveMotherNature_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match4players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match4players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_4 = match4players.getGame().getWizardFromPlayer(playerFour).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = match4players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            match4players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match4players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            match4players.playAssistantsCard(playerThree, assistantsCards_3.get(3)); //2 steps
            match4players.playAssistantsCard(playerFour, assistantsCards_4.get(9));//5 steps

            int oldPositionMotherNature = match4players.getPositionOfMotherNature();
            //Try to move MN with more steps that are allowed for the playerFour
            Assertions.assertThrows(ExceptionGame.class, ()-> match4players.moveMotherNature(playerFour, match4players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerFour) +1)%match4players.getGame().getArchipelagos().size())));
            Assertions.assertEquals(oldPositionMotherNature, match4players.getPositionOfMotherNature());
            //retry to move MN with a number of steps allowed for playerFour
            match4players.moveMotherNature(playerFour, match4players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerFour))%match4players.getGame().getArchipelagos().size()));
            match4players.getActionPhaseOrderOfPlayers().add(playerFour);
            int oldPositionMotherNature_1 = match4players.getPositionOfMotherNature();
            //try to move MN on the same island
            Assertions.assertThrows(ExceptionGame.class, ()->match4players.moveMotherNature(playerFour, match4players.getGame().getArchipelagos().get((oldPositionMotherNature_1)%match4players.getGame().getArchipelagos().size())));
            Assertions.assertEquals(oldPositionMotherNature_1 , match4players.getPositionOfMotherNature());
            //sets the game in order to put a professor on the board of playerFour and have influence on the isle where MN will be in the next move (NB THE SUCCESSIVE ISLE IS THE OPPOSITE OF THE STARTING ISLE OF MN)
            int nextPositionMN = oldPositionMotherNature_1 + 2;
            for(Student s : match4players.getGame().getArchipelagos().get((nextPositionMN)%match4players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                for(int i = 0; i<match4players.getGame().getProfessors().size(); i++){
                    if (match4players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        match4players.getGame().getWizardFromPlayer(playerFour).getBoard().setProfessorInTable(match4players.getGame().getProfessors().get(i));
                        match4players.getGame().getProfessors().remove(match4players.getGame().getProfessors().get(i));
                    }
                }
            }

            match4players.moveMotherNature(playerFour, match4players.getGame().getArchipelagos().get((nextPositionMN)%match4players.getGame().getArchipelagos().size()));
            match4players.getActionPhaseOrderOfPlayers().add(playerFour);

            //check if the tower is build correctly, the towers are property of the captains of the team of playerFour (playerThree)
            Assertions.assertEquals(match4players.getGame().getWizardFromPlayer(playerThree), match4players.getGame().getArchipelagos().get(match4players.getPositionOfMotherNature()).getIsle().get(0).getTower().getProperty());
            int oldPositionMotherNature_2 = match4players.getPositionOfMotherNature();
            //sets again the game in order to put a professor on the board of playerThree and have influence on the isle where MN will be in the next move (oldPositionMotherNature_2 +1)
            for(Student s : match4players.getGame().getArchipelagos().get((oldPositionMotherNature_2 + 1)%match4players.getGame().getArchipelagos().size()).getStudentFromArchipelago()){
                boolean isInGame = false;
                for(int i = 0; i<match4players.getGame().getProfessors().size(); i++){
                    if (match4players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        match4players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(match4players.getGame().getProfessors().get(i));
                        match4players.getGame().getProfessors().remove(match4players.getGame().getProfessors().get(i));
                        isInGame = true;
                    }
                }
                if(!isInGame){  //controllare che non ci sia gia messo in player4
                    for(int i = 0; i<match4players.getGame().getWizardFromPlayer(playerFour).getBoard().getProfessorInTable().size(); i++){
                        if(match4players.getGame().getWizardFromPlayer(playerFour).getBoard().getProfessorInTable().get(i).getColor() == s.getColor()){
                            Professor professor = match4players.getGame().getWizardFromPlayer(playerFour).getBoard().removeProfessorFromTable(s.getColor());
                            match4players.getGame().getWizardFromPlayer(playerThree).getBoard().setProfessorInTable(professor);
                        }
                    }

                }
            }

            Archipelago archipelagoMerged = match4players.getGame().getArchipelagos().get((oldPositionMotherNature_2 + 1)%match4players.getGame().getArchipelagos().size());
            Archipelago previousArchipelagoMerged = match4players.getGame().getArchipelagos().get((oldPositionMotherNature_2)%match4players.getGame().getArchipelagos().size());
            Assertions.assertTrue(match4players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            match4players.moveMotherNature(playerThree, archipelagoMerged);
            match4players.getActionPhaseOrderOfPlayers().add(playerThree);

            //check if the tower is build correctly and the merge is done
            Assertions.assertEquals(11, match4players.getGame().getArchipelagos().size()); //size is decremented
            Assertions.assertTrue(match4players.getGame().getArchipelagos().contains(archipelagoMerged));
            Assertions.assertFalse(match4players.getGame().getArchipelagos().contains(previousArchipelagoMerged));
            Assertions.assertEquals(match4players.getGame().getWizardFromPlayer(playerThree), match4players.getGame().getArchipelagos().get(match4players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().get(0).getTower().getProperty());
            Assertions.assertEquals(6,match4players.getGame().getWizardFromPlayer(playerThree).getBoard().getTowersInBoard().size());
            Assertions.assertEquals(2, match4players.getGame().getArchipelagos().get(match4players.getGame().getArchipelagos().indexOf(archipelagoMerged)).getIsle().size());
            int oldPositionMotherNature_3 = match4players.getPositionOfMotherNature();

            //sets playerTwo the most influence on the ArchipelagoMerged
            for(Student s : archipelagoMerged.getStudentFromArchipelago()){
                boolean isInGame = false;
                for(int i = 0; i<match4players.getGame().getProfessors().size(); i++){
                    if (match4players.getGame().getProfessors().get(i).getColor() == s.getColor()){
                        match4players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(match4players.getGame().getProfessors().get(i));
                        match4players.getGame().getProfessors().remove(match4players.getGame().getProfessors().get(i));
                        isInGame = true;
                    }
                }
                if(!isInGame){
                    for(int i = 0; i<match4players.getGame().getWizardFromPlayer(playerThree).getBoard().getProfessorInTable().size(); i++){
                        if(match4players.getGame().getWizardFromPlayer(playerThree).getBoard().getProfessorInTable().get(i).getColor() == s.getColor()){
                            Professor professor = match4players.getGame().getWizardFromPlayer(playerThree).getBoard().removeProfessorFromTable(s.getColor());
                            match4players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(professor);
                        }
                    }for(int i = 0; i<match4players.getGame().getWizardFromPlayer(playerFour).getBoard().getProfessorInTable().size(); i++){
                        if(match4players.getGame().getWizardFromPlayer(playerFour).getBoard().getProfessorInTable().get(i).getColor() == s.getColor()){
                            Professor professor = match4players.getGame().getWizardFromPlayer(playerFour).getBoard().removeProfessorFromTable(s.getColor());
                            match4players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(professor);
                        }
                    }

                }
            }
            //move mother nature for 10 steps, in order to arrive to the archipelago before the archipelagoMerged
            //and replace playerTwo in the actionPhaseOrder list

            match4players.moveMotherNature(playerFour, match4players.getGame().getArchipelagos().get((oldPositionMotherNature_3+match4players.getGame().getArchipelagos().size() + getSteps(playerFour))%match4players.getGame().getArchipelagos().size()));
            match4players.getActionPhaseOrderOfPlayers().add(playerFour);
            match4players.moveMotherNature(playerFour, match4players.getGame().getArchipelagos().get((match4players.getGame().getArchipelagos().indexOf(archipelagoMerged)+match4players.getGame().getArchipelagos().size() -1 )%match4players.getGame().getArchipelagos().size()));
            match4players.getActionPhaseOrderOfPlayers().add(playerFour);
            match4players.getActionPhaseOrderOfPlayers().add(playerTwo);
            //place MN on archipelagoMerged and check if the replacement of the tower is correct
            match4players.moveMotherNature(playerTwo, archipelagoMerged);
            Assertions.assertEquals(match4players.getGame().getWizardFromPlayer(playerOne), archipelagoMerged.getIsle().get(0).getTower().getProperty());

        });
    }

    @Test
    void checkVictory_NoTowers_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match4players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match4players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_4 = match4players.getGame().getWizardFromPlayer(playerFour).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = match4players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            match4players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match4players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            match4players.playAssistantsCard(playerThree, assistantsCards_3.get(3)); //2 steps
            match4players.playAssistantsCard(playerFour, assistantsCards_4.get(9));//5 steps

            //remove all the tower from playerThree and call CheckVictory from moveMotherNature
            match4players.getGame().getWizardFromPlayer(playerThree).getBoard().getTowersInBoard().removeAll(match4players.getGame().getWizardFromPlayer(playerThree).getBoard().getTowersInBoard());
            int oldPositionMotherNature = match4players.getPositionOfMotherNature();
            match4players.moveMotherNature(playerFour, match4players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerFour))%match4players.getGame().getArchipelagos().size()));
        });
    }

    @Test
    void checkVictory_NoStudents_Test(){
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match4players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match4players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_4 = match4players.getGame().getWizardFromPlayer(playerFour).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = match4players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            match4players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match4players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            match4players.playAssistantsCard(playerThree, assistantsCards_3.get(3)); //2 steps
            match4players.playAssistantsCard(playerFour, assistantsCards_4.get(9));//5 steps
            //remove all student from student bag
            match4players.getGame().getStudentBag().getStudentsInBag().removeAll(match4players.getGame().getStudentBag().getStudentsInBag());
            //call moveMotherNature
            int oldPositionMotherNature = match4players.getPositionOfMotherNature();
            match4players.moveMotherNature(playerFour, match4players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerThree))%match4players.getGame().getArchipelagos().size()));

        });
    }

    @RepeatedTest(24)
    void checkVictory_LessThenThreeArchipelagos_Test() {
        gameSetter();
        Assertions.assertDoesNotThrow(() -> {
            List<AssistantsCards> assistantsCards_1 = match4players.getGame().getWizardFromPlayer(playerOne).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_2 = match4players.getGame().getWizardFromPlayer(playerTwo).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_4 = match4players.getGame().getWizardFromPlayer(playerFour).getAssistantsDeck().getPlayableAssistants();
            List<AssistantsCards> assistantsCards_3 = match4players.getGame().getWizardFromPlayer(playerThree).getAssistantsDeck().getPlayableAssistants();

            match4players.playAssistantsCard(playerOne, assistantsCards_1.get(0)); //1 step
            match4players.playAssistantsCard(playerTwo, assistantsCards_2.get(4)); //3 step
            match4players.playAssistantsCard(playerThree, assistantsCards_3.get(3)); //2 steps
            match4players.playAssistantsCard(playerFour, assistantsCards_4.get(9));//5 steps

            //remove 9 archipelagos from the initial 12
            match4players.getGame().getArchipelagos().removeAll(match4players.getGame().getArchipelagos().subList(0, 9));
            //call motherNature
            for(Archipelago a : match4players.getGame().getArchipelagos()){
                a.setMotherNaturePresence(false);
            }
            //sets MN on the first archipelago
            match4players.getGame().getArchipelagos().get(0).setMotherNaturePresence(true);
            match4players.getGame().getMotherNature().setPosition(0);
            //check if the match will finish
            int oldPositionMotherNature = match4players.getPositionOfMotherNature();
            match4players.moveMotherNature(playerFour, match4players.getGame().getArchipelagos().get((oldPositionMotherNature + getSteps(playerFour))%match4players.getGame().getArchipelagos().size()));
        });
    }
}
