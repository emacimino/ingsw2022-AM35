package it.polimi.ingsw.ModelTest.ExpertMatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.Princess;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that contains the tests for the princess character card
 */
class PrincessTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final ExpertMatch expertMatch = new ExpertMatch(basicMatch2Players);
    Wizard wizard1, wizard2;
    private final Player player1 = new Player("username1");
    private final Player player2 = new Player("username2");


    /**
     * Method used to set the player for the game
     * @param player1 player one
     * @param player2 player two
     * @return a list of players
     */
    private List<Player> setPlayers(Player player1, Player player2){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return players;
    }

    /**
     * Method used to get steps of mother nature
     * @param player player one or two
     * @return the steps you can move mother nature by
     * @throws ExceptionGame is something goes wrong
     */
    public int getSteps(Player player) throws ExceptionGame {
        Wizard wizard = expertMatch.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }

    /**
     * Method used to set a test match
     * @throws ExceptionGame if something goes wrong
     */
    private void setATestMatch() throws ExceptionGame {
        expertMatch.setGame(setPlayers(player1, player2));
    }

    /**
     * Method that prints the game
     */
    public void printGame(){
        System.out.println("\n PRINTING STATE OF GAME: ");
        System.out.println("number of archipelagos " + expertMatch.getGame().getArchipelagos().size());
        System.out.println("professor in game: "+ expertMatch.getGame().getProfessors());
        System.out.println("action order in round: " + expertMatch.getActionPhaseOrderOfPlayers());
        System.out.println("position of MN " + expertMatch.getPositionOfMotherNature());
        for( Wizard w : expertMatch.getGame().getWizards()){
            System.out.println("--->"+w);
            System.out.println("towers " + w.getBoard().getTowersInBoard().size());
            System.out.println("assistantCard " + w.getRoundAssistantsCard());
            System.out.println("number of students in entrance " + w.getBoard().getStudentsInEntrance().size());
            System.out.println("professors in table : " +w.getBoard().getProfessorInTable());
        }
        System.out.println("END OF PRINT \n");
    }

    /**
     * Method that tests 10 times the match and Princess character card interaction
     */
    @RepeatedTest(10)
    public void setTestMatch1() {
        Assertions.assertDoesNotThrow(() -> {
            this.setATestMatch();
            wizard1 = expertMatch.getGame().getWizardFromPlayer(player1);
            wizard2 = expertMatch.getGame().getWizardFromPlayer(player2);
            expertMatch.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(2));
            expertMatch.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(1));
            //sets Archer card in the game in position 0
            CharacterCard princess = new Princess(basicMatch2Players, "Princess");
            expertMatch.getCharactersForThisGame().put(princess.getName(), princess);
            Assertions.assertEquals(2, princess.getCost());
            int cost = princess.getCost();


            for (Color c : Color.values()) {
                Assertions.assertEquals(0, wizard1.getBoard().getTableOfStudent(c).getStudentsInTable().size());
                Assertions.assertEquals(0, wizard2.getBoard().getTableOfStudent(c).getStudentsInTable().size());
            }

            wizard1.addACoin(); //wizard has 2 coin now
            Assertions.assertEquals(2, wizard1.getCoins());
            List<Student> studentsOnCard = princess.getStudentsOnCard();
            Student s = studentsOnCard.get(0);

            princess.setActiveWizard(wizard1);
            princess.setActiveStudents(studentsOnCard.subList(0, 1));
            princess.useCard(expertMatch);

            Assertions.assertFalse(princess.getStudentsOnCard().contains(s));
            Assertions.assertEquals(0, wizard1.getCoins());
            Assertions.assertEquals(3, princess.getCost());

            Assertions.assertEquals(1, wizard1.getBoard().getTableOfStudent(s.getColor()).getStudentsInTable().size());

        });

    }

    /**
     * Method that tests 10 times the match and Princess character card interaction in a 4 player match
     */
        @RepeatedTest(10)
        public void match4player_Test(){
            BasicMatch match4players = factoryMatch.newMatch(4);
            Player player3 = new Player("username3");
            Player player4 = new Player("username4");
            List<Player> players = setPlayers(player1, player2);
            players.add(player3);
            players.add(player4);
            ExpertMatch expertMatch4Players = new ExpertMatch(match4players);

            Assertions.assertThrows(ExceptionGame.class, ()-> expertMatch4Players.setGame(players));

            Assertions.assertDoesNotThrow( ()->{
                expertMatch4Players.setTeams(players);

                expertMatch4Players.setGame(players);
                Wizard wizard1 = expertMatch4Players.getGame().getWizardFromPlayer(player1);
                Wizard wizard2 = expertMatch4Players.getGame().getWizardFromPlayer(player2);
                expertMatch4Players.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(0));
                expertMatch4Players.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(3));
                Wizard wizard3 = expertMatch4Players.getGame().getWizardFromPlayer(player3);
                Wizard wizard4 = expertMatch4Players.getGame().getWizardFromPlayer(player4);
                expertMatch4Players.playAssistantsCard(player3, wizard3.getAssistantsDeck().getPlayableAssistants().get(1));
                expertMatch4Players.playAssistantsCard(player4, wizard4.getAssistantsDeck().getPlayableAssistants().get(5));
                CharacterCard princess = new Princess(match4players, "Princess");
                expertMatch4Players.getCharactersForThisGame().put(princess.getName(), princess);
                assertEquals(2, princess.getCost());
                assertEquals(1, wizard1.getCoins());
                assertEquals(1, wizard2.getCoins());
                assertEquals(1, wizard3.getCoins());
                assertEquals(1, wizard4.getCoins());
                int cost = princess.getCost();

                for (Color c : Color.values()) {
                    Assertions.assertEquals(0, wizard1.getBoard().getTableOfStudent(c).getStudentsInTable().size());
                    Assertions.assertEquals(0, wizard2.getBoard().getTableOfStudent(c).getStudentsInTable().size());
                    Assertions.assertEquals(0, wizard3.getBoard().getTableOfStudent(c).getStudentsInTable().size());
                    Assertions.assertEquals(0, wizard4.getBoard().getTableOfStudent(c).getStudentsInTable().size());
                }

                wizard4.addACoin(); //wizard has 2 coin now
                Assertions.assertEquals(2, wizard4.getCoins());
                List<Student> studentsOnCard = princess.getStudentsOnCard();
                Student s = studentsOnCard.get(0);

                princess.setActiveWizard(wizard4);
                princess.setActiveStudents(studentsOnCard.subList(0, 1));
                princess.useCard(expertMatch4Players);

                Assertions.assertFalse(princess.getStudentsOnCard().contains(s));
                Assertions.assertEquals(0, wizard4.getCoins());
                Assertions.assertEquals(3, princess.getCost());

                Assertions.assertEquals(1, wizard4.getBoard().getTableOfStudent(s.getColor()).getStudentsInTable().size());
                Assertions.assertEquals(0, wizard3.getBoard().getTableOfStudent(s.getColor()).getStudentsInTable().size());
                Assertions.assertEquals(0, wizard2.getBoard().getTableOfStudent(s.getColor()).getStudentsInTable().size());
                Assertions.assertEquals(0, wizard3.getBoard().getTableOfStudent(s.getColor()).getStudentsInTable().size());


            });

        }

}