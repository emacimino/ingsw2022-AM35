package it.polimi.ingsw.Client.CLIENT2;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.NetworkUtilities.CharacterCardInGameMessage;
import it.polimi.ingsw.NetworkUtilities.CloudInGame;
import it.polimi.ingsw.NetworkUtilities.CurrentGameMessage;
import it.polimi.ingsw.NetworkUtilities.EndMatchMessage;

import java.util.List;
import java.util.Map;

public interface UserView {

    void askLogin();

    void askToPlayAssistantCard(List<AssistantsCards> assistantsCards);

    void askMoveMotherNature(String message);

    void askChooseCloud(CloudInGame clouds);

    void showLogin(boolean success);

    void showGenericMessage(String genericMessage);

    void showDisconnectionMessage(String usernameDisconnected, String text);

    void showError(String error);

    void loadBoard(Board board);

    void showWinMessage(EndMatchMessage message, Boolean isWinner);

    void showGameState(CurrentGameMessage currentGameMessage);

    void showCharactersCards(CharacterCardInGameMessage characterCardInGameMessage);



    void askToMoveStudent();

    void loadArchipelagosOption(Map<Integer, Archipelago> archipelago);

    void loadStudentOnEntrance(Map<Integer, Student> students);


}
