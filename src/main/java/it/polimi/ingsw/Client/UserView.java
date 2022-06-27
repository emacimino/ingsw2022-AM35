package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.NetworkUtilities.*;

import java.util.List;
import java.util.Map;

public interface UserView {
    /**
     * ask view to log in
     */
    void askLogin();
    /**
     * ask view to play an assistant card
     * @param assistantsCards assistant card to be picked
     */
    void askToPlayAssistantCard(List<AssistantsCards> assistantsCards);

    /**
     * ask view to move mother nature
     * @param message step of mother nature
     */
    void askMoveMotherNature(String message);

    /**
     * ask view to pick a cloud
     * @param clouds cloud to pick
     */
    void askChooseCloud(CloudInGame clouds);

    /**
     * tell view if the login is correct
     * @param success true if log has success
     */
    void showLogin(boolean success);

    /**
     * Show the view a generic message
     * @param genericMessage could be a phrase that help the client
     */
    void showGenericMessage(String genericMessage);


    /**
     * Show the view an error message
     * @param error could be a phrase that help the client doing the right choice
     */
    void showError(String error);


    /**
     * Communicate if someone won the game
     * @param message endOfMatch
     * @param isWinner tell if the match has been won
     */
    void showWinMessage(EndMatchMessage message, Boolean isWinner);

    /**
     * Help to understand how the current match is going
     * @param currentGameMessage send a copy of the match with its information
     */
    void showGameState(CurrentGameMessage currentGameMessage);

    /**
     * Show the Character Cards for this game
     * @param characterCardInGameMessage shows the deck of this match
     */
    void showCharactersCards(CharacterCardInGameMessage characterCardInGameMessage);


    /**
     * ask view to move a student
     */
    void askToMoveStudent();


    /**
     * update the remote model
     * @param remoteModel remote model updated
     */
    void setRemoteModel(RemoteModel remoteModel);

    /**
     * Show the characterCard chosen by the player
     */
    void showChosenCharacterCard();

    void showDisconnection();

    void confirmMoveStudent();
}
