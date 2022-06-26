package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.Friar;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.ExpertMatch.FactoryCharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
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

public class ControllerTest {

    private Set<String> usernameBasicMatch2Players;
    private Set<String> usernameBasicMatch4Players;
    private Controller controllerBasicMatch2Players;
    private Controller controllerExpertMatch2Players;
    private Controller controllerBasicMatch4Players;
    private TurnController turnControllerBasicMatch2Players;
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final ExpertMatch expertMatch2Players = new ExpertMatch(basicMatch2Players);
    private final FactoryMatch factoryMatch4 = new FactoryMatch();
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



    private void setControllerInTest(){
        setListsOfPlayers(); //aggiunge al set di stringhe gli username dei players
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

    private void setControllerInTestExpert(){
        setListsOfPlayers(); //aggiunge al set di stringhe gli username dei players
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


    private void setControllerInTest4players(){
        setListsOfPlayers4(); //aggiunge al set di stringhe gli username dei players
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



    private void setListsOfPlayers(){
        usernameBasicMatch2Players = new HashSet<>();
        usernameBasicMatch2Players.add(playerOne.getUsername());
        usernameBasicMatch2Players.add(playerTwo.getUsername());
    }

    private void setListsOfPlayers4(){
        usernameBasicMatch4Players = new HashSet<>();
        usernameBasicMatch4Players.add(playerOne.getUsername());
        usernameBasicMatch4Players.add(playerTwo.getUsername());
        usernameBasicMatch4Players.add(playerThree.getUsername());
        usernameBasicMatch4Players.add(playerFour.getUsername());
    }



    @Test
    void getMatch4Players(){
        setControllerInTest4players();
        Assertions.assertEquals(controllerBasicMatch4Players.getMatch(), basicMatch4Players);
    }

    @Test
    void getMatch_Test() {
        setControllerInTest();
        Assertions.assertEquals(controllerBasicMatch2Players.getMatch(), basicMatch2Players);
    }

    @Test
    void initGame_Test(){
        setControllerInTest();
        Assertions.assertTrue(controllerBasicMatch2Players.getMatch().getPlayers().stream().map(Player::getUsername).toList().containsAll(usernameBasicMatch2Players));
        Assertions.assertEquals(GameState.PLANNING_PHASE, controllerBasicMatch2Players.getGameState());
        Assertions.assertNotNull(controllerBasicMatch2Players.getMatch().getGame());
    }

    @Test
    void initGame4Players_Test(){
        setControllerInTest4players();
        Assertions.assertTrue(controllerBasicMatch4Players.getMatch().getPlayers().stream().map(Player::getUsername).toList().containsAll(usernameBasicMatch4Players));
        Assertions.assertEquals(GameState.PLANNING_PHASE, controllerBasicMatch4Players.getGameState());
        Assertions.assertNotNull(controllerBasicMatch4Players.getMatch().getGame());
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch4Players.getMatch().getTeams());
    }


    @Test
    void onMessageReceived_Test() {
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

    }

    @Test
    void onMessageReceivedExpertMatch_Test() throws ExceptionGame {
        setControllerInTestExpert();
        controllerExpertMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow(()-> expertMatch2Players.playAssistantsCard(playerOne, AssistantsCards.CardNine));
        Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertThrows(Exception.class, () -> controllerExpertMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertFalse(expertMatch2Players.getGame().getAssistantsCardsPlayedInRound().isEmpty());
        String card1=expertMatch2Players.getCharactersForThisGame().keySet().stream().toList().get(0);
        String card2=expertMatch2Players.getCharactersForThisGame().keySet().stream().toList().get(1);
        String card3=expertMatch2Players.getCharactersForThisGame().keySet().stream().toList().get(2);
        Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage(expertMatch2Players.getCharactersForThisGame().get(card1).getName())));
        Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage(expertMatch2Players.getCharactersForThisGame().get(card2).getName())));
        Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage(expertMatch2Players.getCharactersForThisGame().get(card3).getName())));
        controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().addStudentInTable(new Student(Color.GREEN));
        controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().addStudentInTable(new Student(Color.GREEN));
        //Aggiungo studente verde numero 3 alla table e controllo se ho coin
        Collection <Student> s = controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getStudentsInEntrance();
        int i=0;
        for (Student stud: s) {
            if(stud.getColor()==Color.GREEN) break;
            i++;}
        final int j=i;
        Assertions.assertDoesNotThrow(()-> controllerExpertMatch2Players.onMessageReceived(new MoveStudentMessage(j, null)));
        Assertions.assertEquals(1, expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getCoins());
        //Assertions.assertTrue(controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().isProfessorPresent(Color.GREEN));
        //Assertions.assertThrows(ExceptionGame.class, () -> controllerExpertMatch2Players.onMessageReceived()));
    }

    @RepeatedTest(100)
    void characterTest1of12() throws ExceptionGame {
        setControllerInTestExpert();
        controllerExpertMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow(()-> expertMatch2Players.playAssistantsCard(playerOne, AssistantsCards.CardNine));
        Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertThrows(Exception.class, () -> controllerExpertMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertFalse(expertMatch2Players.getGame().getAssistantsCardsPlayedInRound().isEmpty());
        CharacterCard card1=expertMatch2Players.getCharactersForThisGame().get(expertMatch2Players.getCharactersForThisGame().keySet().stream().toList().get(0));
        CharacterCard card2=expertMatch2Players.getCharactersForThisGame().get(expertMatch2Players.getCharactersForThisGame().keySet().stream().toList().get(1));
        CharacterCard card3=expertMatch2Players.getCharactersForThisGame().get(expertMatch2Players.getCharactersForThisGame().keySet().stream().toList().get(2));
        //Aggiungo molti coins per player
        for(int i=0;i<8;i++){
            controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(playerOne).addACoin();
        }
        for(int i=0;i<8;i++){
            controllerExpertMatch2Players.getMatch().getGame().getWizardFromPlayer(playerTwo).addACoin();
        }
        cardSwitch(card1);
        cardSwitch(card2);
        cardSwitch(card3);
    }


    void cardSwitch(CharacterCard card1) throws ExceptionGame {
        List <Integer> integersList = new ArrayList<>();
        integersList.add(1);
        List <Integer> integersList2 = new ArrayList<>();
        integersList2.add(0);
        if(card1.getName()=="Friar"){
            final int i= expertMatch2Players.getGame().getArchipelagos().get(1).getStudentFromArchipelago().size();
            Assertions.assertDoesNotThrow(()-> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage("Friar")));
            Assertions.assertDoesNotThrow(()->controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage("Friar", 2, null, integersList, null)));
            Assertions.assertEquals(i+1, expertMatch2Players.getGame().getArchipelagos().get(1).getStudentFromArchipelago().size());
        }

       /*if(card1.getName()=="Baker"){
            List <Color> colorsList = new ArrayList<>();
            colorsList.add(Color.GREEN);
            expertMatch2Players.getGame().getWizardFromPlayer(playerTwo).getBoard().addStudentInTable(new Student(Color.GREEN));
            if(controllerExpertMatch2Players.getTurnController().getActivePlayer()==playerTwo)expertMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(new Professor(Color.GREEN));else expertMatch2Players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(new Professor(Color.GREEN));
            expertMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().addStudentInTable(new Student(Color.GREEN));
            Assertions.assertDoesNotThrow(()->controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage("Baker", -1, null, null, colorsList)));
            Assertions.assertTrue(expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().isProfessorPresent(Color.GREEN));
        }*/

        if(card1.getName()=="Messenger"){
            System.out.println(controllerExpertMatch2Players.getTurnController().getActivePlayer());
            expertMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().getProfessorInTable().clear();
            expertMatch2Players.getGame().getWizardFromPlayer(playerTwo).getBoard().getProfessorInTable().clear();
            expertMatch2Players.getGame().getWizardFromPlayer(playerOne).getBoard().setProfessorInTable(new Professor(Color.RED));
            expertMatch2Players.getGame().getWizardFromPlayer(playerTwo).getBoard().setProfessorInTable(new Professor(Color.GREEN));
            for(Student s :expertMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago() )
                expertMatch2Players.getGame().getArchipelagos().get(2).addStudentInArchipelago(new Student(Color.RED));

            expertMatch2Players.getGame().getArchipelagos().get(2).addStudentInArchipelago(new Student(Color.RED));
            System.out.println(expertMatch2Players.getGame().getArchipelagos().get(2).getIsle());
            System.out.println(expertMatch2Players.getGame().getArchipelagos().get(2).getIsle().get(0));
            Assertions.assertDoesNotThrow(()-> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage("Messenger")));
            Assertions.assertDoesNotThrow(()->controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage("Messenger", 3, null, null, null)));
           System.out.println(expertMatch2Players.getGame().getArchipelagos().get(2).getIsle().get(0));
            Assertions.assertTrue(expertMatch2Players.getGame().getArchipelagos().get(2).getIsle().get(0).isThereTower());
            Assertions.assertEquals(expertMatch2Players.getGame().getArchipelagos().get(2).getIsle().get(0).getTower().getProperty(), expertMatch2Players.getGame().getWizardFromPlayer(playerTwo));
        }

        if (card1.getName()=="Magician"){
            Assertions.assertDoesNotThrow(()-> controllerExpertMatch2Players.onMessageReceived(new AskCharacterCardMessage("Magician")));
            Assertions.assertDoesNotThrow(()->controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage("Magician", -1, null, null, null)));
            Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new MoveStudentMessage(1, 3)));
            Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new MoveStudentMessage(4, 3)));
            Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new MoveStudentMessage(2, 5)));
            Assertions.assertEquals(TurnPhase.MOVE_MOTHER_NATURE, controllerExpertMatch2Players.getTurnController().getTurnPhase());
            if(expertMatch2Players.getPositionOfMotherNature() != 2 && expertMatch2Players.getPositionOfMotherNature()!=8)Assertions.assertEquals(3,  expertMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
            else Assertions.assertEquals(2, expertMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
            //Muove madre natura
            int i = expertMatch2Players.getPositionOfMotherNature();
            Assertions.assertDoesNotThrow(() -> controllerExpertMatch2Players.onMessageReceived(new MoveMotherNatureMessage((i+5) % expertMatch2Players.getGame().getArchipelagos().size())));
            int j;
            if(i+5 >= 12) j=(i+5)%expertMatch2Players.getGame().getArchipelagos().size() - 1;
            else j=i+4;
            Assertions.assertEquals(j, expertMatch2Players.getPositionOfMotherNature());
        }

        /*if(card1.getName()=="Jester"){
            Collection <Student> student = expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getStudentsInEntrance();
            Assertions.assertDoesNotThrow(()->controllerExpertMatch2Players.onMessageReceived(new PlayCharacterMessage("Jester", -1, integersList, integersList2, null)));
            Assertions.assertNotEquals(student, expertMatch2Players.getGame().getWizardFromPlayer(controllerExpertMatch2Players.getTurnController().getActivePlayer()).getBoard().getStudentsInEntrance());
        } The Jester Card does not contains all the students selected */

        if(card1.getName()=="Chef"){
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

    }

    @RepeatedTest(1)
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
        //Inizia il round player che gioca card Five e muove 2 studenti
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(1, 3)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(4, 3)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(2, 5)));
        Assertions.assertEquals(TurnPhase.MOVE_MOTHER_NATURE, controllerBasicMatch2Players.getTurnController().getTurnPhase());


        if(basicMatch2Players.getPositionOfMotherNature() != 2 && basicMatch2Players.getPositionOfMotherNature()!=8)
            Assertions.assertEquals(3,  basicMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
        else
            Assertions.assertEquals(2, basicMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
        //Muove madre natura
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
        //move_students because only the first player has playes
        Assertions.assertEquals(TurnPhase.MOVE_STUDENTS, controllerBasicMatch2Players.getTurnController().getTurnPhase());

        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(1, 4)));
        if(i != 3 && i!= 9)
            Assertions.assertEquals(2,  basicMatch2Players.getGame().getArchipelagos().get(3).getStudentFromArchipelago().size());

    }

    @Test
    void addView_Test() {
        setControllerInTest();
        Assertions.assertNotNull(controllerBasicMatch2Players);
    }

    @Test
    void update_Test(){
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow( () -> basicMatch2Players.playAssistantsCard(playerOne, AssistantsCards.CardFive));
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertThrows(ExceptionGame.class, () ->basicMatch2Players.playAssistantsCard(playerTwo, AssistantsCards.CardFive));
        //basicMatch2Players.getGame().getClouds().add(new Cloud(3));
        //Assertions.assertDoesNotThrow(()->controllerBasicMatch2Players.update(new CloudMessage(0)));
    }



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
        Map<String, ViewInterface> viewmap = new HashMap<>();
        viewmap.put(playerOne.getUsername(), view1);
        viewmap.put(playerTwo.getUsername(), view2);
        controllerBasicMatch2Players.setViewMap(viewmap);
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.initGame());
        Assertions.assertNotNull(controllerBasicMatch2Players);
    }

    @Test
    void setGameState_Test() {
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.PLANNING_PHASE);
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.ACTION_PHASE);
    }

    @Test
    void getGameState_Test() {
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.PLANNING_PHASE);
    }

    @Test
    void isMatchOnGoing_test(){
        setControllerInTest();
        controllerBasicMatch2Players.setMatchOnGoing(true);
        Assertions.assertTrue(controllerBasicMatch2Players.isMatchOnGoing());
    }

    @Test
    void setMatchOnGoing_test(){
        setControllerInTest();
        controllerBasicMatch2Players.setMatchOnGoing(false);
        Assertions.assertFalse(controllerBasicMatch2Players.isMatchOnGoing());
    }
}