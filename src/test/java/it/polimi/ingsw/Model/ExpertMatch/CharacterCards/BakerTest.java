package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BakerTest {

    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final ExpertMatch expertMatch = new ExpertMatch(basicMatch2Players);
    Wizard wizard1, wizard2;
    private final Player player1 = new Player("username1");
    private final Player player2 = new Player("username2");

    Professor greenProfessor = new Professor(Color.GREEN);

    private List<Player> setPlayers(Player player1, Player player2){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return players;
    }
    public int getSteps(Player player) throws ExceptionGame{
        Wizard wizard = expertMatch.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }

    private void setATestMatch() throws ExceptionGame {
        expertMatch.setGame(setPlayers(player1, player2));
    }
    public void printGame(){
        System.out.println("\n PRINTING STATE OF GAME: ");
        System.out.println("numbero of archipelagos " + expertMatch.getGame().getArchipelagos().size());

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

    @RepeatedTest(10)
    public void setTestMatch() {
        Assertions.assertDoesNotThrow(() -> {
            this.setATestMatch();
            wizard1 = expertMatch.getGame().getWizardFromPlayer(player1);
            wizard2 = expertMatch.getGame().getWizardFromPlayer(player2);
            expertMatch.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(0));
            expertMatch.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(3));
            //sets a Baker card in the game in position 0
            CharacterCard baker = new Baker( basicMatch2Players,"Baker");
            expertMatch.getCharactersForThisGame().put(0, baker);
            Assertions.assertEquals(2, baker.getCost());
            Assertions.assertEquals(1, wizard1.getCoins());
            Assertions.assertEquals(1, wizard2.getCoins());
            int cost = baker.getCost();

            Collection<Student> studentsPlayer1 = expertMatch.getGame().getWizardFromPlayer(player1).getBoard().getStudentsInEntrance();
            Collection<Student> studentsPlayer2 = expertMatch.getGame().getWizardFromPlayer(player2).getBoard().getStudentsInEntrance();
            Student[] students1 = new Student[studentsPlayer1.size()];
            Student[] students2 = new Student[studentsPlayer2.size()];
            studentsPlayer1.toArray(students1);
            studentsPlayer2.toArray(students2);
            expertMatch.moveStudentOnBoard(player1, students1[0]);
            expertMatch.moveStudentOnBoard(player1, students1[1]);
            expertMatch.moveStudentOnBoard(player2, students2[0]);
            expertMatch.moveStudentOnBoard(player2, students2[1]);
            int positionMN = expertMatch.getPositionOfMotherNature();
            Archipelago interestArchipelago = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player1)) % expertMatch.getGame().getArchipelagos().size());

            wizard1.addACoin(); //wizard has 2 coin now
            Assertions.assertEquals(2, wizard1.getCoins());
            Assertions.assertEquals(2, baker.getCost());
            baker.setActiveWizard(wizard1);
            baker.useCard(expertMatch);

            Assertions.assertEquals(0, wizard1.getCoins());
            Assertions.assertEquals(cost + 1, baker.getCost());


            expertMatch.moveMotherNature(player1, interestArchipelago);

            Assertions.assertNull(expertMatch.getActiveInfluenceCard());
            Assertions.assertEquals(7, wizard1.getBoard().getTowersInBoard().size());


            positionMN = expertMatch.getPositionOfMotherNature();
            interestArchipelago = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player2)) % expertMatch.getGame().getArchipelagos().size());

            baker.setActiveWizard(wizard2);
            Assertions.assertThrows(ExceptionGame.class, ()->baker.useCard(expertMatch));
            wizard2.addACoin();
            wizard2.addACoin();//wizard2 now has 3 coins
            baker.useCard(expertMatch);
            Assertions.assertEquals(cost+2, baker.getCost());
            Assertions.assertEquals(0, wizard2.getCoins());
            expertMatch.moveMotherNature(player2, interestArchipelago);
            Assertions.assertEquals(7, wizard2.getBoard().getTowersInBoard().size());
            expertMatch.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(7));
            expertMatch.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(5));

            positionMN = expertMatch.getPositionOfMotherNature();
            interestArchipelago = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player2)) % expertMatch.getGame().getArchipelagos().size());
            expertMatch.moveMotherNature(player2, interestArchipelago); //muove player 2

            Wizard wizardMostInfluence;

            if (interestArchipelago.calculateInfluenceInArchipelago(wizard1) > interestArchipelago.calculateInfluenceInArchipelago(wizard2)) {
                Assertions.assertEquals(7, wizard2.getBoard().getTowersInBoard().size());
                wizardMostInfluence = wizard1;
            } else if (interestArchipelago.calculateInfluenceInArchipelago(wizard1) < interestArchipelago.calculateInfluenceInArchipelago(wizard2)) {
                Assertions.assertEquals(6, wizard2.getBoard().getTowersInBoard().size());
                wizardMostInfluence = wizard2;
            } else {
                Assertions.assertEquals(7, wizard1.getBoard().getTowersInBoard().size());
                Assertions.assertEquals(7, wizard2.getBoard().getTowersInBoard().size());
                wizardMostInfluence = null;
            }
            positionMN = expertMatch.getPositionOfMotherNature();
            interestArchipelago = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player2)) % expertMatch.getGame().getArchipelagos().size());

            baker.setActiveWizard(wizard1);
            Assertions.assertThrows(ExceptionGame.class, ()-> baker.useCard(expertMatch));
            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            baker.useCard(expertMatch);
            expertMatch.moveMotherNature(player1, interestArchipelago);
            Assertions.assertEquals(6, wizard1.getBoard().getTowersInBoard().size());



        });
    }

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
            CharacterCard baker = new Baker(  match4players,"baker");
            expertMatch4Players.getCharactersForThisGame().put(0, baker);
            assertEquals(2, baker.getCost());
            assertEquals(1, wizard1.getCoins());
            assertEquals(1, wizard2.getCoins());
            assertEquals(1, wizard3.getCoins());
            assertEquals(1, wizard4.getCoins());
            int cost = baker.getCost();
            Collection<Student> studentsPlayer3 = expertMatch4Players.getGame().getWizardFromPlayer(player3).getBoard().getStudentsInEntrance();
            Collection<Student> studentsPlayer4 = expertMatch4Players.getGame().getWizardFromPlayer(player4).getBoard().getStudentsInEntrance();
            List<Student> students3 = new ArrayList<>(studentsPlayer3);
            List<Student> students4 = new ArrayList<>(studentsPlayer4);
            expertMatch4Players.moveStudentOnBoard(player3, students3.get(0));
            expertMatch4Players.moveStudentOnBoard(player3, students3.get(1));
            expertMatch4Players.moveStudentOnBoard(player4, students4.get(0));
            expertMatch4Players.moveStudentOnBoard(player4, students4.get(1));
            Collection<Student> studentsPlayer1 = expertMatch4Players.getGame().getWizardFromPlayer(player1).getBoard().getStudentsInEntrance();
            Collection<Student> studentsPlayer2 = expertMatch4Players.getGame().getWizardFromPlayer(player2).getBoard().getStudentsInEntrance();
            Student[] students1 = new Student[studentsPlayer1.size()];
            Student[] students2 = new Student[studentsPlayer2.size()];
            studentsPlayer1.toArray(students1);
            studentsPlayer2.toArray(students2);
            expertMatch4Players.moveStudentOnBoard(player1, students1[0]);
            expertMatch4Players.moveStudentOnBoard(player1, students1[1]);
            expertMatch4Players.moveStudentOnBoard(player2, students2[0]);
            expertMatch4Players.moveStudentOnBoard(player2, students2[1]);

            int positionMN = expertMatch4Players.getPositionOfMotherNature();
            positionMN = expertMatch4Players.getPositionOfMotherNature();
            Archipelago interestArchipelago = expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player4)) % expertMatch4Players.getGame().getArchipelagos().size());
            Student[] s = new Student[2];
            Student student = interestArchipelago.getStudentFromArchipelago().toArray(s)[0];
            wizard4.addACoin(); //wizard has 3 coin now
            assertEquals(2, wizard4.getCoins());
            assertEquals(2, baker.getCost());

            int influenceBeforeEffectTeam1 = expertMatch4Players.getWizardInfluenceInArchipelago(player1, interestArchipelago);
            int influenceBeforeEffectTeam2 = expertMatch4Players.getWizardInfluenceInArchipelago(player3, interestArchipelago);

            baker.setActiveWizard(wizard4);
            baker.useCard(expertMatch4Players);
            assertEquals(0, wizard4.getCoins());
            assertEquals(cost + 1, baker.getCost());

            int influenceAfterEffectTeam1 = expertMatch4Players.getWizardInfluenceInArchipelago(player1, interestArchipelago) ;
            int influenceAfterEffectTeam2 = expertMatch4Players.getWizardInfluenceInArchipelago(player3, interestArchipelago) ;

            expertMatch4Players.moveMotherNature(player4, interestArchipelago); //NB MN MOVED BY PLAYER4

            if (influenceAfterEffectTeam1 > influenceAfterEffectTeam2) {
                Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
                Assertions.assertEquals(8, wizard3.getBoard().getTowersInBoard().size());
                Assertions.assertEquals(0, wizard2.getBoard().getTowersInBoard().size());
                Assertions.assertEquals(0, wizard4.getBoard().getTowersInBoard().size());
            }else if (influenceAfterEffectTeam1 < influenceAfterEffectTeam2){
                Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
                Assertions.assertEquals(7, wizard3.getBoard().getTowersInBoard().size());
                Assertions.assertEquals(0, wizard2.getBoard().getTowersInBoard().size());
                Assertions.assertEquals(0, wizard4.getBoard().getTowersInBoard().size());
            }else{
                Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
                Assertions.assertEquals(8, wizard3.getBoard().getTowersInBoard().size());
                Assertions.assertEquals(0, wizard2.getBoard().getTowersInBoard().size());
                Assertions.assertEquals(0, wizard4.getBoard().getTowersInBoard().size());
            }
        });

    }
    public int getSteps(ExpertMatch expertMatch, Player player) throws ExceptionGame{
        Wizard wizard = expertMatch.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }
}