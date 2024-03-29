package it.polimi.ingsw.ModelTest.MatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

/**
 * GameTest is the class that tests the method of the Game Class
 */
public class GameTest {

    /**
     * This method create a Game object with the passed parameters
     * @param limitStudInEntrance is the number of student allowed in the entrance of the wizard's board of the game
     * @param numOfStudentMovable is the number movable by a wizard in one round
     * @return the game object created
     */
    public Game createGame(int limitStudInEntrance, int numOfStudentMovable){
        return new Game(limitStudInEntrance, numOfStudentMovable);
    }

    /**
     * This method returns a List of Player which size is equal to the passed parameter
     * @param numPlayers the number of players that will be in the list
     * @return the list of players
     */
    public List<Player> createPlayers(int numPlayers){
        List<Player> players = new ArrayList<>();
        for(int i = 0; i< numPlayers; i++)
            players.add(new Player("username_" +i));
        return players;
    }

    /**
     * This methodTest tests the correct function of setWizards and getWizardFromPlayer methods
     * @param num of players in the game
     */
    @ParameterizedTest
    @ValueSource( ints ={1,2,3})
    void setWizards_getWizardFromPlayer_Test(int num){
        List<Player> players = new ArrayList<>();
        for(int i = 0; i< num; i++)
            players.add(new Player("username_" +i));
        Game game = new Game(9, 4);
        game.setWizards(players);
            Assertions.assertDoesNotThrow(() -> {
                for(int i = 0; i< num; i++)
                    Assertions.assertEquals(players.get(i).getUsername(),game.getWizardFromPlayer(players.get(i)).getUsername());
            });
    }

    /**
     * This methodTest tests the correct function of setTowers method
     * @param num is the number of tower on the board at the start of the match
     */
    @ParameterizedTest
    @ValueSource( ints ={6,8})
    void setTowers_Test(int num) {
        Game game = createGame(7, 3);
        game.setTowers(num);
        for(Wizard w : game.getWizards()){
            Assertions.assertEquals(num, w.getBoard().getTowersInBoard().size());
        }
    }

    /**
     * This methodTest tests the setClouds and setStudentsObCloud methods

     */
    @Test
    void setClouds_setStudentsOnCloud_Test(){
        int[] nums = {9, 4};
        int numClouds = 3;
        Game game = createGame(nums[0], nums[1]);
        Assertions.assertDoesNotThrow(()->game.setClouds(numClouds, nums[1]));
        Assertions.assertEquals(numClouds, game.getClouds().size());
        Assertions.assertDoesNotThrow(()-> {
                    for (Cloud c : game.getClouds()) {
                        Assertions.assertEquals(nums[1], c.getStudentOnCloud().size());
                    }
                    Assertions.assertThrows(ExceptionGame.class, game::setRandomStudentsOnCloud);
                });

    }

    /**
     * This methodTest tests the setProfessor method
     */
    @Test
    void setProfessors_Test(){
        int[] nums = {9, 4};
        Game game = createGame(nums[0], nums[1]);
        game.setProfessors();
        int numProf = 0;
        for(Color c : Color.values()){
            for(Professor p : game.getProfessors()){
                if(p.getColor().equals(c))
                    numProf++;
            }
        }
        Assertions.assertEquals(Color.values().length, numProf);
        Assertions.assertEquals(game.getProfessors().size(), numProf);
    }

    /**
     * This methodTest tests the setArchipelagos method
     */
    @Test
    void setArchipelagos_Test(){
        int[] nums = {7,3};
        Game game = createGame(nums[0], nums[1]);
        Assertions.assertDoesNotThrow(()->{
            game.setArchipelagos();
            int positionMotherNature = game.getMotherNature().getPosition();
            Assertions.assertTrue(game.getArchipelagos().get(positionMotherNature).getStudentFromArchipelago().isEmpty(), "Mother Nature island has students");
            Assertions.assertTrue(game.getArchipelagos().get((positionMotherNature + 6)%12).getStudentFromArchipelago().isEmpty(), "Opposite island of mother nature has students");

           for(Archipelago a : game.getArchipelagos()){
               int index = game.getArchipelagos().indexOf(a);
               if( index != positionMotherNature && index != (positionMotherNature + 6)%12)
                   Assertions.assertEquals(1, game.getArchipelagos().get(index).getStudentFromArchipelago().size());
           }
        });
    }

    /**
     * This methodTest tests the getWizardInfluenceInArchipelago method
     * @param numPlayers is the number of players
     */
    @ParameterizedTest
    @ValueSource( ints = {2,3,4})
    void getWizardInfluenceInArchipelago_Test(int numPlayers){
        int[] nums = {9, 4};
        Game game = createGame(nums[0], nums[1]);
        List<Player> players = createPlayers(numPlayers);
        game.setWizards(players);
        Archipelago archipelago = new Archipelago();
        Student student1  = new Student(Color.BLUE);
        Student student2 = new Student(Color.BLUE);
        Student student3 = new Student(Color.GREEN);
        archipelago.addStudentInArchipelago(student1);
        archipelago.addStudentInArchipelago(student2);
        archipelago.addStudentInArchipelago(student3);
        Assertions.assertDoesNotThrow(()->{
            game.getWizardFromPlayer(players.get(0)).getBoard().setProfessorInTable(new Professor(Color.BLUE));
            game.getWizardFromPlayer(players.get(1)).getBoard().setProfessorInTable(new Professor(Color.GREEN));
            int influenceWizard_0 = game.getWizardInfluenceInArchipelago(game.getWizardFromPlayer(players.get(0)), archipelago);
            int influenceWizard_1 = game.getWizardInfluenceInArchipelago(game.getWizardFromPlayer(players.get(1)), archipelago);
            Assertions.assertEquals(2, influenceWizard_0);
            Assertions.assertEquals(1, influenceWizard_1);
        });

    }

    /**
     * This methodTest tests the selectWizardWithMostStudent method
     */
    @Test
    void selectWizardWithMostStudent_Test(){
        int[] nums = {7,3};
        int numPlayers = 4;
        Game game = createGame(nums[0], nums[1]);
        List<Player> players = createPlayers(numPlayers);
        game.setWizards(players);
        Student student1  = new Student(Color.BLUE);
        Student student2 = new Student(Color.BLUE);
        Student student3 = new Student(Color.BLUE);
        Student student4 = new Student(Color.GREEN);
        Student student5 = new Student(Color.GREEN);
        Assertions.assertDoesNotThrow(()-> {
            game.getWizardFromPlayer(players.get(0)).getBoard().addStudentInTable(student1);
            game.getWizardFromPlayer(players.get(0)).getBoard().addStudentInTable(student2);
            game.getWizardFromPlayer(players.get(1)).getBoard().addStudentInTable(student3);
            game.getWizardFromPlayer(players.get(1)).getBoard().addStudentInTable(student4);
            Assertions.assertEquals(game.getWizardFromPlayer(players.get(0)), game.selectWizardWithMostStudents(Color.BLUE));
            Assertions.assertEquals(game.getWizardFromPlayer(players.get(1)), game.selectWizardWithMostStudents(Color.GREEN));
            Assertions.assertNotEquals(game.getWizardFromPlayer(players.get(1)), game.selectWizardWithMostStudents(Color.BLUE));
            game.getWizardFromPlayer(players.get(0)).getBoard().addStudentInTable(student5);
            Assertions.assertThrows(ExceptionGame.class, () -> game.selectWizardWithMostStudents(Color.GREEN));
        });

    }

    /**
     * This methodTest tests the placeProfessor method
     */
    @Test
    void placeProfessor_Test(){
        int[] nums = {7,3};
        int numPlayers = 4;
        Game game = createGame(nums[0], nums[1]);
        game.setProfessors();
        List<Player> players = createPlayers(numPlayers);
        game.setWizards(players);
        Student student1  = new Student(Color.BLUE);
        Student student2 = new Student(Color.BLUE);
        Student student3 = new Student(Color.BLUE);
        Student student4 = new Student(Color.GREEN);
        Student student5 = new Student(Color.GREEN);
        Student student6 = new Student(Color.GREEN);
        Assertions.assertDoesNotThrow(()-> {
            game.getWizardFromPlayer(players.get(0)).getBoard().addStudentInTable(student1);
            game.getWizardFromPlayer(players.get(0)).getBoard().addStudentInTable(student2);
            game.getWizardFromPlayer(players.get(1)).getBoard().addStudentInTable(student3);
            game.getWizardFromPlayer(players.get(1)).getBoard().addStudentInTable(student4);
            game.placeProfessor(Color.BLUE);
            game.placeProfessor(Color.GREEN);

            Assertions.assertTrue(game.getWizardFromPlayer(players.get(0)).getBoard().isProfessorPresent(Color.BLUE));
            Assertions.assertTrue(game.getWizardFromPlayer(players.get(1)).getBoard().isProfessorPresent(Color.GREEN));

            game.getWizardFromPlayer(players.get(0)).getBoard().addStudentInTable(student5);
            Assertions.assertThrows(ExceptionGame.class, ()-> game.placeProfessor(Color.GREEN));

            Assertions.assertTrue(game.getWizardFromPlayer(players.get(1)).getBoard().isProfessorPresent(Color.GREEN), "the professor is not moved from player 0");
            Assertions.assertFalse(game.getWizardFromPlayer(players.get(0)).getBoard().isProfessorPresent(Color.GREEN), "the professor is not moved to player 1");

            game.getWizardFromPlayer(players.get(0)).getBoard().addStudentInTable(student6);
            game.placeProfessor(Color.GREEN);

            Assertions.assertFalse(game.getWizardFromPlayer(players.get(1)).getBoard().isProfessorPresent(Color.GREEN), "the professor is moved from player 1");
            Assertions.assertTrue(game.getWizardFromPlayer(players.get(0)).getBoard().isProfessorPresent(Color.GREEN), "the professor is moved to player 0");
        });
    }

    /**
     * This methodTest tests the placeMotherNature method
     */
    @Test
    void placeMotherNature_Test(){
        int[] nums = {7,3};
        Game game = createGame(nums[0], nums[1]);
        List<Player> players = createPlayers(3);
        game.setWizards(players);
        Assertions.assertDoesNotThrow(()->{
            game.setArchipelagos();
            int positionMotherNature = game.getMotherNature().getPosition();
            Wizard wizard_0 = game.getWizardFromPlayer(players.get(0));
            Archipelago archipelago = game.getArchipelagos().get(positionMotherNature); //Mother Nature's archipelago
            Assertions.assertThrows(ExceptionGame.class, ()-> game.placeMotherNature(players.get(0), archipelago));
            Archipelago archipelago1 = game.getArchipelagos().get((positionMotherNature + game.getArchipelagos().size() + 1) %game.getArchipelagos().size()); //archipelago after Mother Nature's archipelago
            Assertions.assertDoesNotThrow(()->{
                //player has not played an assistant's card yet
                Assertions.assertThrows(ExceptionGame.class, () -> game.placeMotherNature(players.get(0), archipelago1)); //sets Mother Nature in the next archipelago
                //player play an assistant's card
                wizard_0.playAssistantsCard(wizard_0.getAssistantsDeck().getPlayableAssistants().get(0), new ArrayList<AssistantsCards>()); //step allowed = 1
                //retry the method
                game.placeMotherNature(players.get(0), archipelago1);
                Assertions.assertFalse(game.getArchipelagos().get(positionMotherNature).isMotherNaturePresence());
                Assertions.assertTrue(game.getArchipelagos().get((positionMotherNature + game.getArchipelagos().size()+ 1)%game.getArchipelagos().size()).isMotherNaturePresence());
                Assertions.assertEquals(game.getMotherNature().getPosition(), (positionMotherNature+1 + game.getArchipelagos().size())%game.getArchipelagos().size());
                //player play another assistant's card
                int newPositionMotherNature = game.getMotherNature().getPosition();
                wizard_0.playAssistantsCard(wizard_0.getAssistantsDeck().getPlayableAssistants().get(4), new ArrayList<AssistantsCards>()); //step allowed 3
                Assertions.assertThrows(ExceptionGame.class, () -> game.placeMotherNature(players.get(0), game.getArchipelagos().get((newPositionMotherNature + game.getArchipelagos().size()+ 4)%game.getArchipelagos().size())));
                game.placeMotherNature(players.get(0), game.getArchipelagos().get((newPositionMotherNature + game.getArchipelagos().size()+ 2)%game.getArchipelagos().size()));
                Assertions.assertFalse(game.getArchipelagos().get(newPositionMotherNature).isMotherNaturePresence());
                Assertions.assertTrue(game.getArchipelagos().get((newPositionMotherNature + game.getArchipelagos().size()+ 2)%game.getArchipelagos().size()).isMotherNaturePresence());
                Assertions.assertEquals(game.getMotherNature().getPosition(), (newPositionMotherNature + game.getArchipelagos().size()+ 2)%game.getArchipelagos().size());
            });

        });

    }



    /**
     * This methodTest tests takeCareOfMerge method
     */
    @Test
    void takeCareOfMerge_Test(){
        int[] nums = {9,4};
        int numPlayers = 3;
        Game game = createGame(nums[0], nums[1]);
        game.setWizards(createPlayers(numPlayers));
        Assertions.assertDoesNotThrow(()->{
            game.setArchipelagos();
            Wizard wizard1 = game.getWizards().get(0);
            Wizard wizard2 = game.getWizards().get(1);
            game.setTowers(6);
            int actualArchipelagoIndex = 0;
            Archipelago actualArchipelago = game.getArchipelagos().get(actualArchipelagoIndex);

            //try the method without having place a tower in the archipelago
            game.takeCareOfTheMerge(actualArchipelago);

            actualArchipelago.placeWizardsTower(wizard1);

            //put the towers on the previous and next archipelago
            game.getArchipelagos().get((actualArchipelagoIndex + game.getArchipelagos().size()-1)% game.getArchipelagos().size()).placeWizardsTower(wizard1);
            game.getArchipelagos().get((actualArchipelagoIndex + game.getArchipelagos().size() +1 )% game.getArchipelagos().size()).placeWizardsTower(wizard1);
            Assertions.assertEquals(12, game.getArchipelagos().size());
            game.takeCareOfTheMerge(actualArchipelago);
            Assertions.assertEquals(10, game.getArchipelagos().size());

            game.getArchipelagos().get((actualArchipelagoIndex + game.getArchipelagos().size() -1 )% game.getArchipelagos().size()).placeWizardsTower(wizard1);
            game.takeCareOfTheMerge(actualArchipelago);
            Assertions.assertEquals(9, game.getArchipelagos().size());

            game.getArchipelagos().get((actualArchipelagoIndex + game.getArchipelagos().size() +1 )% game.getArchipelagos().size()).placeWizardsTower(wizard2);
            game.getArchipelagos().get((actualArchipelagoIndex + game.getArchipelagos().size() -1 )% game.getArchipelagos().size()).placeWizardsTower(wizard2);
            game.takeCareOfTheMerge(actualArchipelago);
            Assertions.assertEquals(9, game.getArchipelagos().size());
            actualArchipelago.placeWizardsTower(wizard2);
            game.takeCareOfTheMerge(actualArchipelago);
            Assertions.assertEquals(7, game.getArchipelagos().size());
        });
    }

    /**
     * This method tests that the wizard with the least tower is correct
     */
    @Test
    void getWizardWithLeastTowers_Test(){
        int[] nums = {9,4};
        int numPlayers = 3;
        Game game = createGame(nums[0], nums[1]);
        game.setWizards(createPlayers(numPlayers));
        Assertions.assertDoesNotThrow(()->{
            game.setArchipelagos();
            Wizard wizard1 = game.getWizards().get(0);
            Wizard wizard2 = game.getWizards().get(1);
            game.setTowers(6);
            int actualArchipelagoIndex = 0;
            Archipelago actualArchipelago = game.getArchipelagos().get(actualArchipelagoIndex);
            //place 2 tower from the board of wizard1
            actualArchipelago.placeWizardsTower(wizard1);
            game.getArchipelagos().get((actualArchipelagoIndex + game.getArchipelagos().size()-1)% game.getArchipelagos().size()).placeWizardsTower(wizard1);
            //place 1 tower from the board of wizard2
            game.getArchipelagos().get((actualArchipelagoIndex + game.getArchipelagos().size()+1)% game.getArchipelagos().size()).placeWizardsTower(wizard2);


            //check if wizard1 is the wizard with the least towers
            Assertions.assertEquals(wizard1, game.getWizardsWithLeastTowers().get(0));

            //place another tower from the board of wizard2 (wizard1 and wizard2 have the same number of towers on its boards)
            game.getArchipelagos().get((actualArchipelagoIndex + game.getArchipelagos().size()+2)% game.getArchipelagos().size()).placeWizardsTower(wizard2);

            //check if the both wizard1 and wizard2 are the wizards with the least towers
            Assertions.assertEquals(game.getWizards().subList(0,2), game.getWizardsWithLeastTowers());

            //place another tower from the board of wizard2 (wizard2 is now the wizard with the least towers)
            game.getArchipelagos().get((actualArchipelagoIndex + game.getArchipelagos().size()+3)% game.getArchipelagos().size()).placeWizardsTower(wizard2);

            //check if wizard2 is the wizard with the least towers
            Assertions.assertEquals(wizard2, game.getWizardsWithLeastTowers().get(0));

        });
    }

}
