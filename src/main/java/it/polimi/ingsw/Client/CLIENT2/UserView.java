package it.polimi.ingsw.Client.CLIENT2;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.NetworkUtilities.Message.CurrentGameMessage;

import java.util.List;

public interface UserView {

    void askLogin();

    void askPlayAssistantCard(List<AssistantsCards> assistantsCards);

    void askMoveStudent(List<Student> students);

    void askMoveMotherNature(List<Archipelago> archipelagos);

    void askChooseCloud(List<Cloud> cloud);

    void showLogin(boolean success);

    void showGenericMessage(String genericMessage);

    void showDisconnectionMessage(String usernameDisconnected, String text);

    void showError(String error);

    void showBoard(Board board);

    void showLobby(List<String> usernames);

    void showMatchInfo();

    void showWinMessage(String winner);

    void showGameState(CurrentGameMessage currentGameMessage);
}
