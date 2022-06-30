package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.Minstrel;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.ExpertMatch.FactoryCharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.*;
import it.polimi.ingsw.Server.SocketClientConnection;
import it.polimi.ingsw.View.RemoteView;
import it.polimi.ingsw.View.ViewInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Class that contains the tests for the game controller when two or more players are playing
 */
public class ControllerTest {

    private Set<String> usernameBasicMatch2Players;
    private Set<String> usernameBasicMatch4Players;
    private Controller controllerBasicMatch2Players;
    private Controller controllerExpertMatch2Players;
    private Controller controllerBasicMatch4Players;
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final ExpertMatch expertMatch2Players = new ExpertMatch(basicMatch2Players);
    private final BasicMatch basicMatch4Players = factoryMatch.newMatch(4);
    private final Player playerOne = new Player("One");
    private final Player playerTwo = new Player("Two");
    private final Player playerThree = new Player("Three");
    private final Player playerFour = new Player("Four");
    private  ViewInterface view1;
    private SocketClientConnection clientConnection1;
    private  ViewInterface view2;
    private  SocketClientConnection clientConnection2;
    private  ViewInterface view3;
    private  SocketClientConnection clientConnection3;
    private  ViewInterface view4;
    private  SocketClientConnection clientConnection4;


    /**
     * Method used to set the controller for the tests
     */
    private void setControllerInTest(){
        setListsOfPlayers(); //adding players username
        Assertions.assertDoesNotThrow(()->{
            view1 = new RemoteView(this.clientConnection1);
            view2 = new RemoteView(this.clientConnection2);
        });
        if(basicMatch2Players.getNumberOfPlayers() == 2) {
            try {
                controllerBasicMatch2Players = new Controller(basicMatch2Players, usernameBasicMatch2Players);
                controllerBasicMatch2Players.setMatchOnGoing(false);
                controllerBasicMatch2Players.addView(playerOne.getUsername(), view1);
                controllerBasicMatch2Players.addView(playerTwo.getUsername(), view2);
                controllerBasicMatch2Players.initGame();
            } catch (ExceptionGame | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Method used to set the controller for the tests in expert mode
     */
    private void setControllerInTestExpert(){

        setListsOfPlayers(); //adding players username
        Assertions.assertDoesNotThrow(()->{

            view1 = new RemoteView(this.clientConnection1);
            view2 = new RemoteView(this.clientConnection2);
        });
        if(expertMatch2Players.getNumberOfPlayers() == 2) {
            try {
                controllerExpertMatch2Players = new Controller(expertMatch2Players, usernameBasicMatch2Players);
                controllerExpertMatch2Players.setMatchOnGoing(false);
                controllerExpertMatch2Players.addView(playerOne.getUsername(), view1);
                controllerExpertMatch2Players.addView(playerTwo.getUsername(), view2);
                controllerExpertMatch2Players.initGame();
            } catch (ExceptionGame | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Method used to set the controller for the tests in a 4 player match
     */
    private void setControllerInTest4players(){
        setListsOfPlayers4(); //adding players usernames
        Assertions.assertDoesNotThrow(()->{
            view1 = new RemoteView(this.clientConnection1);
            view2 = new RemoteView(this.clientConnection2);
            view3 = new RemoteView(this.clientConnection3);
            view4 =new RemoteView(this.clientConnection4);
        });
        if(basicMatch4Players.getNumberOfPlayers() == 4) {
            try {
                controllerBasicMatch4Players = new Controller(basicMatch4Players, usernameBasicMatch4Players);
                controllerBasicMatch4Players.setMatchOnGoing(false);
                controllerBasicMatch4Players.addView(playerOne.getUsername(), view1);
                controllerBasicMatch4Players.addView(playerTwo.getUsername(), view2);
                controllerBasicMatch4Players.addView(playerFour.getUsername(), view3);
                controllerBasicMatch4Players.addView(playerFour.getUsername(), view4);
                controllerBasicMatch4Players.initGame();
            } catch (ExceptionGame | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * Method used to set the list of players in the 2 player match
     */
    private void setListsOfPlayers(){
        usernameBasicMatch2Players = new HashSet<>();
        usernameBasicMatch2Players.add(playerOne.getUsername());
        usernameBasicMatch2Players.add(playerTwo.getUsername());
    }

    /**
     * Method used to set the list of players in 4 players match
     */
    private void setListsOfPlayers4(){
        usernameBasicMatch4Players = new HashSet<>();
        usernameBasicMatch4Players.add(playerOne.getUsername());
        usernameBasicMatch4Players.add(playerTwo.getUsername());
        usernameBasicMatch4Players.add(playerThree.getUsername());
        usernameBasicMatch4Players.add(playerFour.getUsername());
    }


    /**
     * Method that tests the getMatch method in a 4 player match
     */
    @Test
    void getMatch4Players(){
        setControllerInTest4players();
        Assertions.assertEquals(controllerBasicMatch4Players.getMatch(), basicMatch4Players);
    }


    /**
     * Method that tests the getMatch method in a 2 player match
     */
    @Test
    void getMatch_Test() {
        setControllerInTest();
        Assertions.assertEquals(controllerBasicMatch2Players.getMatch(), basicMatch2Players);
    }

    /**
     * Method that tests the initGame method in a 2 player match
     */
    @RepeatedTest(12)
    void initGame_Test(){
        setControllerInTest();
        Assertions.assertTrue(controllerBasicMatch2Players.getMatch().getPlayers().stream().map(Player::getUsername).toList().containsAll(usernameBasicMatch2Players));
        Assertions.assertEquals(GameState.PLANNING_PHASE, controllerBasicMatch2Players.getGameState());
        Assertions.assertNotNull(controllerBasicMatch2Players.getMatch().getGame());
        Assertions.assertNotNull(controllerBasicMatch2Players.getTurnController().getMessageHandler());
    }

    /**
     * Method that tests the initGame method in a 4 player match
     */
    @RepeatedTest(12)
    void initGame4Players_Test(){
        setControllerInTest4players();
        Assertions.assertTrue(controllerBasicMatch4Players.getMatch().getPlayers().stream().map(Player::getUsername).toList().containsAll(usernameBasicMatch4Players));
        Assertions.assertEquals(GameState.PLANNING_PHASE, controllerBasicMatch4Players.getGameState());
        Assertions.assertNotNull(controllerBasicMatch4Players.getMatch().getGame());
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch4Players.getMatch().getTeams());
    }

    /**
     * Method that tests how the controllers handles messages sent to it
     */
    @RepeatedTest(12)
    void onMessageReceived_Test(){
        setControllerInTest();
        controllerBasicMatch2Players.setMatchOnGoing(false);
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.ACTION_PHASE);
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow(()-> basicMatch2Players.playAssistantsCard(playerOne, AssistantsCards.CardNine));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(1,1)));
       // if(basicMatch2Players.getPositionOfMotherNature()!=1)
        //    Assertions.assertEquals(1, basicMatch2Players.getGame().getArchipelagos().get(1).getStudentFromArchipelago().size());
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertThrows(Exception.class, () -> controllerBasicMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertFalse(basicMatch2Players.getGame().getAssistantsCardsPlayedInRound().isEmpty());
        controllerBasicMatch2Players.setGameState(GameState.GAME_ENDED);

    }

    /**
     * Method that tests how the controllers handles messages sent to it in an expert match
     */
    @RepeatedTest(12)
    void onMessageReceivedExpertMatch_Test() throws ExceptionGame {
        setControllerInTestExpert();
        planningPhaseCheck();
        Student testStudent = new Student(Color.GREEN);
        int indexOfStudent = 0;
        controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().addStudentInTable(testStudent);
        controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().addStudentInTable(testStudent);
        //this phase to be checked
        //adding student number three to the table and check if the wizard have enough coins
        /*while (controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getStudentsInEntrance().stream().iterator().next().getColor()!=testStudent.getColor()){
            indexOfStudent++;
        }
        int finalIndexOfStudent = indexOfStudent;
        Assertions.assertDoesNotThrow(()-> controllerExpertMatch2Players.onMessageReceived(new MoveStudentMessage(finalIndexOfStudent, null)));
        */
        Assertions.assertEquals(1, expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getCoins());
        //Assertions.assertTrue(controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().isProfessorPresent(Color.GREEN));
        //Assertions.assertThrows(ExceptionGame.class, () -> controllerExpertMatch2Players.onMessageReceived()));
        String card1 = expertMatch2Players.getCharactersForThisGame().keySet().stream().toList().get(0);
        Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage(expertMatch2Players.getCharactersForThisGame().get(card1).getName())));

    }

    /**
     * Method that tests how the controllers handles the planning phase
     */
    private void planningPhaseCheck() {
        controllerExpertMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow(()-> expertMatch2Players.playAssistantsCard(playerOne, AssistantsCards.CardNine));
        Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertThrows(Exception.class, () -> controllerExpertMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertDoesNotThrow(()-> expertMatch2Players.playAssistantsCard(playerTwo, AssistantsCards.CardFive));
        Assertions.assertThrows(Exception.class,() -> controllerExpertMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardFive)));
        Assertions.assertThrows(Exception.class, () -> controllerExpertMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertFalse(expertMatch2Players.getGame().getAssistantsCardsPlayedInRound().isEmpty());
    }


    /**
     * Method that tests how the controllers handles the character cards played in the game
     */
    @RepeatedTest(100)
    void characterTest1of12() throws ExceptionGame {
        setControllerInTestExpert();
        controllerExpertMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow(()-> expertMatch2Players.playAssistantsCard(playerOne, AssistantsCards.CardNine));
        Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertThrows(Exception.class, () -> controllerExpertMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertFalse(controllerExpertMatch2Players.getMatch().getGame().getAssistantsCardsPlayedInRound().isEmpty());
        CharacterCard card1 = ((ExpertMatch)controllerExpertMatch2Players.getMatch()).getCharactersForThisGame().get(((ExpertMatch)controllerExpertMatch2Players.getMatch()).getCharactersForThisGame().keySet().stream().toList().get(0));
        //adding coins for testing purpose
        for(int i=0;i<8;i++){
            controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(playerOne).addACoin();
        }
        for(int i=0;i<8;i++){
            controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(playerTwo).addACoin();
        }
        Assertions.assertTrue(((ExpertMatch)controllerExpertMatch2Players.getMatch()).getCharactersForThisGame().containsKey(card1.getName()));
        cardSwitch(card1);
    }

    /**
     * Helper method for the character cards tests
     */
    void cardSwitch(CharacterCard card1) throws ExceptionGame {
        final int notValidArchipelago = 13;
        Player activePlayer = controllerExpertMatch2Players.getTurnController().getActivePlayer();
        Student testStudent;
        List <Integer> integersList = new ArrayList<>();
        integersList.add(1);
        List <Integer> integersList2 = new ArrayList<>();
        integersList2.add(0);
        switch (card1.getName()) {

            case "Friar" -> {//works
                final int i = expertMatch2Players.getGame().getArchipelagos().get(1).getStudentFromArchipelago().size();
                Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage(card1.getName())));
                Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage(card1.getName(), 2, null, integersList, null)));
                Assertions.assertEquals(i + 1, expertMatch2Players.getGame().getArchipelagos().get(1).getStudentFromArchipelago().size());
            }

            case "Messenger" ->{
                Assertions.assertDoesNotThrow(()-> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage(card1.getName())));
                expertMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().getProfessorInTable().clear();
                expertMatch2Players.getGame().getWizardFromPlayer(playerTwo).getBoard().getProfessorInTable().clear();
                System.out.println(expertMatch2Players.getGame().getWizardFromPlayer(playerTwo).getBoard().getProfessorInTable());
                ArrayList<Student> studentsOnEntrance = new ArrayList<>(controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard().getStudentsInEntrance());
                testStudent = studentsOnEntrance.get(0);
                Assertions.assertDoesNotThrow(()->  controllerExpertMatch2Players.onMessageReceived(new MoveStudentMessage(0, 3)));
                expertMatch2Players.getGame().placeProfessor(testStudent.getColor());
                expertMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().add(new Student(testStudent.getColor()));
                expertMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().add(new Student(testStudent.getColor()));
                Assertions.assertDoesNotThrow(()->controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage("Messenger", 3, null, null, null)));
                Assertions.assertTrue(expertMatch2Players.getGame().getArchipelagos().get(3).getIsle().get(0).isThereTower());
                Assertions.assertEquals(expertMatch2Players.getGame().getArchipelagos().get(3).getIsle().get(0).getTower().getProperty(), expertMatch2Players.getGame().getWizardFromPlayer(activePlayer));
            }


            case "Magician" -> { //works
                Assertions.assertDoesNotThrow(()->  controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage("Magician")));
                Assertions.assertDoesNotThrow(()->  controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage("Magician", -1, null, null, null)));
                Assertions.assertDoesNotThrow(()->  controllerExpertMatch2Players.onMessageReceived(new MoveStudentMessage(1, 3)));
                Assertions.assertDoesNotThrow(()->  controllerExpertMatch2Players.onMessageReceived(new MoveStudentMessage(4, 3)));
                Assertions.assertDoesNotThrow(()->  controllerExpertMatch2Players.onMessageReceived(new MoveStudentMessage(2, 5)));
                Assertions.assertEquals(TurnPhase.MOVE_MOTHER_NATURE, controllerExpertMatch2Players.getTurnController().getTurnPhase());
                if(expertMatch2Players.getPositionOfMotherNature() != 2 && expertMatch2Players.getPositionOfMotherNature()!=8)Assertions.assertEquals(3,  expertMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
                else Assertions.assertEquals(2, expertMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
                //move mother nature
                int i = expertMatch2Players.getPositionOfMotherNature();
                Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new MoveMotherNatureMessage((((i+5) % expertMatch2Players.getGame().getArchipelagos().size()) +1))));
                int j;
                if(i+5 >= 12) j=((i+5)%expertMatch2Players.getGame().getArchipelagos().size() );
                else j=i+5;
                Assertions.assertEquals(j, expertMatch2Players.getPositionOfMotherNature());
            }

            case "Chef" ->{
                List <Color> colorsList = new ArrayList<>();
                colorsList.add(Color.GREEN);
                expertMatch2Players.getGame().getArchipelagos().get(0).getStudentFromArchipelago().add(new Student(Color.GREEN));
                expertMatch2Players.getGame().getArchipelagos().get(0).getStudentFromArchipelago().add(new Student(Color.GREEN));
                expertMatch2Players.getGame().getArchipelagos().get(0).getStudentFromArchipelago().add(new Student(Color.RED));
                expertMatch2Players.getGame().getWizardFromPlayer(playerTwo).getBoard().addStudentInTable(new Student(Color.GREEN));
                expertMatch2Players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(new Professor(Color.GREEN));
                expertMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().addStudentInTable(new Student(Color.RED));
                expertMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(new Professor(Color.RED));
                Assertions.assertDoesNotThrow(()-> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage("Chef")));
                Assertions.assertDoesNotThrow(()->controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage("Chef", -1, null, null, colorsList )));
                expertMatch2Players.getGame().getMotherNature().setPosition(0);
                Assertions.assertTrue(expertMatch2Players.getWizardInfluenceInArchipelago(playerOne, expertMatch2Players.getGame().getArchipelagos().get(0)) >= expertMatch2Players.getWizardInfluenceInArchipelago(playerTwo, expertMatch2Players.getGame().getArchipelagos().get(0)));
            }

            case "Jester" ->{

            card1.setActiveWizard(controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()));
            Collection <Student> student = expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getStudentsInEntrance();
            Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage(card1.getName())));
            Assertions.assertDoesNotThrow(()->controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage("Jester", -1, integersList, integersList2, null)));
            Assertions.assertEquals(student.size(), expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getStudentsInEntrance().size());
            for(Student s : student){
                if(!expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getStudentsInEntrance().contains(s)){
                    Assertions.assertTrue(expertMatch2Players.getCharactersForThisGame().get("Jester").getStudentsOnCard().contains(s));
                }
            }


            }
            case "Minstrel" ->{
                card1.setActiveWizard(expertMatch2Players.getGame().getWizardFromPlayer(activePlayer));
                Minstrel minstrel = (Minstrel)card1;
                //setting color list of student that will be traded from table
                Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage(card1.getName())));

                List <Color> colorsList = new ArrayList<>();
                colorsList.add(Color.GREEN);
                colorsList.add(Color.RED);

                //adding the same type of student on the table
                expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().addStudentInTable(new Student(Color.GREEN));
                expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().addStudentInTable(new Student(Color.RED));

                //create a list of two student to be traded
                Student student1 = controllerExpertMatch2Players.getTurnController().getMessageHandler().getStudentsOnEntranceMap().get(1);
                Student student2 = controllerExpertMatch2Players.getTurnController().getMessageHandler().getStudentsOnEntranceMap().get(2);
                List<Integer> tradeFromEntrance = new ArrayList<>();
                tradeFromEntrance.add(1);
                tradeFromEntrance.add(2);

                //and copy them to check later
                Color firstStudentTradedColor = student1.getColor();
                Color secondStudentTradedColor = student2.getColor();
                final int tableOneSize = expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getTableOfStudent(firstStudentTradedColor).getStudentsInTable().size();
                final int tableTwoSize = expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getTableOfStudent(secondStudentTradedColor).getStudentsInTable().size();

                Assertions.assertDoesNotThrow(()->controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage(card1.getName(), notValidArchipelago, tradeFromEntrance, null, colorsList )));

                List<Student> originalStudentInEntrance = new ArrayList<>();
                originalStudentInEntrance.add(student1);
                originalStudentInEntrance.add(student2);
                for(Student s : originalStudentInEntrance){
                    Assertions.assertTrue(controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard().getStudentsFromTable(s.getColor()).contains(s));
                }

                if(!firstStudentTradedColor.equals(secondStudentTradedColor) && !firstStudentTradedColor.equals(Color.GREEN) && !firstStudentTradedColor.equals(Color.RED)) {
                    Assertions.assertEquals(tableOneSize + 1, expertMatch2Players.getGame()
                            .getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer())
                            .getBoard().getTableOfStudent(firstStudentTradedColor).getStudentsInTable().size());

                }
                if(!firstStudentTradedColor.equals(secondStudentTradedColor) && !secondStudentTradedColor.equals(Color.GREEN) && !secondStudentTradedColor.equals(Color.RED)){
                    Assertions.assertEquals(tableTwoSize + 1, expertMatch2Players.getGame()
                            .getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer())
                            .getBoard().getTableOfStudent(secondStudentTradedColor).getStudentsInTable().size());

                }
            }

            case "Knight"-> { //seems to be working
                final Archipelago archipelago = expertMatch2Players.getGame().getArchipelagos().get(1);
                int CardEffectWizardInfluence = controllerExpertMatch2Players.getMatch().getWizardInfluenceInArchipelago((controllerExpertMatch2Players.getTurnController().getActivePlayer()), archipelago);

                Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage(card1.getName(), notValidArchipelago, null, null, null)));
                Assertions.assertEquals(CardEffectWizardInfluence + 2, expertMatch2Players.getWizardInfluenceInArchipelago((controllerExpertMatch2Players.getTurnController().getActivePlayer()), archipelago));
            }
            case "Princess"-> {
                // will be written
            }
        }

    }

    /**
     * Method that tests how the controller handles the next player's action phase and how the turn flows
     */
    @RepeatedTest(12)
    void nextPlayerActionPhaseTest(){
        setControllerInTest();
        List<AssistantsCards> list = new ArrayList<>();
        list.add(AssistantsCards.CardEight);
        list.add(AssistantsCards.CardFive);
        controllerBasicMatch2Players.setMatchOnGoing(false);
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardFive)));
        Assertions.assertEquals(list, basicMatch2Players.getGame().getAssistantsCardsPlayedInRound());
        //Start the round playing card five and moving two student
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(1, 3)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(4, 3)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(2, 5)));
        Assertions.assertEquals(TurnPhase.MOVE_MOTHER_NATURE, controllerBasicMatch2Players.getTurnController().getTurnPhase());


        if(basicMatch2Players.getPositionOfMotherNature() != 2 && basicMatch2Players.getPositionOfMotherNature()!=8)
            Assertions.assertEquals(3,  basicMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
        else
            Assertions.assertEquals(2, basicMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
        //Move mother nature
        int i = basicMatch2Players.getPositionOfMotherNature();

        if(i == 11)
            Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveMotherNatureMessage(((i+1)+1) % basicMatch2Players.getGame().getArchipelagos().size())));
        else
            Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveMotherNatureMessage((i+1)+1 )));
        Assertions.assertEquals(TurnPhase.CHOOSE_CLOUD, controllerBasicMatch2Players.getTurnController().getTurnPhase());


        int j;
        if(i+1 == 12)
            j=0;
        else
            j=i+1;
        Assertions.assertEquals(j, basicMatch2Players.getPositionOfMotherNature());
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new CloudMessage(1)));
        //move_students because only the first player has played
        Assertions.assertEquals(TurnPhase.MOVE_STUDENTS, controllerBasicMatch2Players.getTurnController().getTurnPhase());

        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(1, 4)));
        if(i != 3 && i!= 9)
            Assertions.assertEquals(2,  basicMatch2Players.getGame().getArchipelagos().get(3).getStudentFromArchipelago().size());

    }


    /**
     * Method that tests how the controller responds to a mother nature's move request
     */
    @RepeatedTest(12)
    void onMessageReceived2PLayersMotherNature_Test(){
        setControllerInTest();
        List<AssistantsCards> list = new ArrayList<>();
        list.add(AssistantsCards.CardEight);
        list.add(AssistantsCards.CardFive);
        controllerBasicMatch2Players.setMatchOnGoing(false);
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardFive)));
        Assertions.assertEquals(list, basicMatch2Players.getGame().getAssistantsCardsPlayedInRound());
        //Start the round playing card five and moving two student
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(1, 3)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(4, 3)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(2, 5)));
        Assertions.assertEquals(TurnPhase.MOVE_MOTHER_NATURE, controllerBasicMatch2Players.getTurnController().getTurnPhase());


        if(basicMatch2Players.getPositionOfMotherNature() != 2 && basicMatch2Players.getPositionOfMotherNature()!=8)
            Assertions.assertEquals(3,  basicMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
        else
            Assertions.assertEquals(2, basicMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
        //Move mother nature
        int i = basicMatch2Players.getPositionOfMotherNature();

        if(i == 11)
            Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveMotherNatureMessage(((i+1)+1) % basicMatch2Players.getGame().getArchipelagos().size())));
        else
            Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveMotherNatureMessage((i+1)+1 )));
        Assertions.assertEquals(TurnPhase.CHOOSE_CLOUD, controllerBasicMatch2Players.getTurnController().getTurnPhase());


        int j;
        if(i+1 == 12)
            j=0;
        else
            j=i+1;
        Assertions.assertEquals(j, basicMatch2Players.getPositionOfMotherNature());
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new CloudMessage(1)));
        //move_students because only the first player has played
        Assertions.assertEquals(TurnPhase.MOVE_STUDENTS, controllerBasicMatch2Players.getTurnController().getTurnPhase());

        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(1, 4)));
        if(i != 3 && i!= 9)
            Assertions.assertEquals(2,  basicMatch2Players.getGame().getArchipelagos().get(3).getStudentFromArchipelago().size());

    }

    /**
     * Method used to test the addView method
     */
    @Test
    void addView_Test() {
        setControllerInTest();
        Assertions.assertNotNull(controllerBasicMatch2Players);
    }

    /**
     * Method used to test the update method when messages are passed to it
     */
    @Test
    void update_Test(){
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow( () -> basicMatch2Players.playAssistantsCard(playerOne, AssistantsCards.CardFive));
        Assertions.assertThrows(ExceptionGame.class, () ->basicMatch2Players.playAssistantsCard(playerTwo, AssistantsCards.CardFive));
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertThrows(ExceptionGame.class, () ->basicMatch2Players.playAssistantsCard(playerTwo, AssistantsCards.CardFive));
        //basicMatch2Players.getGame().getClouds().add(new Cloud(3));
        //Assertions.assertDoesNotThrow(()->controllerBasicMatch2Players.update(new CloudMessage(0)));
    }


    /**
     * Method used to test the setViewMap method
     */
    @Test
    void setViewMap_Test() {
        setListsOfPlayers();
        Assertions.assertDoesNotThrow(()->{
            view1 = new RemoteView(this.clientConnection1);
            view2 = new RemoteView(this.clientConnection2);
        });
        if(basicMatch2Players.getNumberOfPlayers() == 2) {
            try {
                controllerBasicMatch2Players = new Controller(basicMatch2Players, usernameBasicMatch2Players);
                controllerBasicMatch2Players.setMatchOnGoing(false);
            } catch (ExceptionGame | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        Map<String, ViewInterface> viewMap = new HashMap<>();
        viewMap.put(playerOne.getUsername(), view1);
        viewMap.put(playerTwo.getUsername(), view2);
        controllerBasicMatch2Players.setViewMap(viewMap);
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.initGame());
        Assertions.assertNotNull(controllerBasicMatch2Players);
    }

    /**
     * Method used to test the setGameState method
     */
    @Test
    void setGameState_Test() {
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.PLANNING_PHASE);
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.ACTION_PHASE);
    }

    /**
     * Method used to test the setGameState method
     */
    @Test
    void getGameState_Test() {
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.PLANNING_PHASE);
    }

    /**
     * Method used to test the isMatchOnGoing method
     */
    @Test
    void isMatchOnGoing_test(){
        setControllerInTest();
        controllerBasicMatch2Players.setMatchOnGoing(true);
        Assertions.assertTrue(controllerBasicMatch2Players.isMatchOnGoing());
    }

    /**
     * Method used to test the setMatchOnGoing method
     */
    @Test
    void setMatchOnGoing_test(){
        setControllerInTest();
        controllerBasicMatch2Players.setMatchOnGoing(false);
        Assertions.assertFalse(controllerBasicMatch2Players.isMatchOnGoing());
    }
}