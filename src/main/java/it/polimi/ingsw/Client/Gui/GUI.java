package it.polimi.ingsw.Client.Gui;

import it.polimi.ingsw.Client.Gui.Scene.ActionSceneController;
import it.polimi.ingsw.Client.UserView;
import it.polimi.ingsw.Client.Gui.Scene.SceneController;
import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.*;
import it.polimi.ingsw.Observer.Observable;
import javafx.application.Platform;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GUI class  that implements the UserView interface
 */
public class GUI extends Observable implements UserView {
    private RemoteModel remoteModel;

    /**
     * ask view to log in
     */
    @Override
    public void askLogin() {
        Platform.runLater(()-> SceneController.setScene(getObservers(), "login.fxml"));
    }

    /**
     * tell view if the login is correct
     * @param success true if log has success
     */
    @Override
    public void showLogin(boolean success) {
        if (!success) {
            Platform.runLater(() -> {
                SceneController.showAlert("Error", "Nickname already taken.");
                SceneController.setScene(getObservers(), "login.fxml"); //rifai login
            });
        }else
            Platform.runLater(() -> SceneController.setScene(getObservers(), "setupMatch.fxml"));

    }

    /**
     * ask view to play an assistant card
     * @param assistantsCards assistant card to be picked
     */
    @Override
    public void askToPlayAssistantCard(List<AssistantsCards> assistantsCards) {
        Platform.runLater(()->SceneController.showAssistantsCardOption(assistantsCards));
    }

    /**
     * ask view to move a student
     */
    @Override
    public void askToMoveStudent() {
        Platform.runLater(()-> {
            if(!(SceneController.getActiveController() instanceof ActionSceneController)){
                System.out.println("istanzio actionScene in GUI");
                SceneController.setActionScene(getObservers(), "actionScene.fxml");

            }else{
                SceneController.updateArchipelagosOnMoveStudent();
                SceneController.updateBoardOnMoveStudent();
            }
            if(!remoteModel.getCharacterCardMap().isEmpty())
                SceneController.setActualSceneExpert();
        });
    }

    /**
     * ask view to move mother nature
     * @param message step of mother nature
     */
    @Override
    public void askMoveMotherNature(String message) {
        Platform.runLater(()->{
            SceneController.updateArchipelagosOnMoveStudent();
            SceneController.updateBoardOnMoveStudent();
            SceneController.letMoveMotherNature();

            if(!remoteModel.getCharacterCardMap().isEmpty())
                SceneController.setActualSceneExpert();
        });
    }

    /**
     * ask view to pick a cloud
     * @param cloud cloud to pick
     */
    @Override
    public void askChooseCloud(CloudInGame cloud) {
        Platform.runLater(()-> { //rifare la scena prendendo info da remote model
                SceneController.enableClouds(cloud, remoteModel.getGame());
        });

    }

    /**
     * Help to understand how the current match is going
     * @param currentGameMessage send a copy of the match with its information
     */
    @Override
    public void showGameState(CurrentGameMessage currentGameMessage){
        Platform.runLater(()-> {
            SceneController.showGame(remoteModel.getGame());
            if(!remoteModel.getCharacterCardMap().isEmpty()){
                SceneController.loadCharacterCards(remoteModel.getCharacterCardMap());
            }
        });
    }

    /**
     * Show the Character Cards for this game
     * @param characterCardInGameMessage shows the deck of this match
     */
    @Override
    public void showCharactersCards(CharacterCardInGameMessage characterCardInGameMessage) {
        Platform.runLater(()-> SceneController.loadCharacterCards(characterCardInGameMessage.getCharacterCard()));
    }

    /**
     * update the remote model
     * @param remoteModel remote model updated
     */
    @Override
    public void setRemoteModel(RemoteModel remoteModel) {
        this.remoteModel = remoteModel;
    }

    /**
     * Show the action to perform regarding the characterCard chosen by the player
     */
    @Override
    public void showChosenCharacterCard() {
        switch (remoteModel.getActiveCharacterCard()){
            case "Archer", "Magician", "Knight", "Baker" -> {
                notifyObserver(new PlayCharacterMessage(remoteModel.getActiveCharacterCard(), 13, null, null, null));
                }
            case "Princess", "Friar", "Jester" -> {
                Platform.runLater(() ->SceneController.setCharacterScene(getObservers(), "expertScenes/studentCardScene.fxml"));
            }
            case "Messenger", "Herbalist" ->{
                Platform.runLater(() ->SceneController.setCharacterScene(getObservers(), "expertScenes/ArchipelagoEffectedScene.fxml"));
            }
            case "Minstrel", "Chef", "Banker" ->{
                Platform.runLater(() ->SceneController.setCharacterScene(getObservers(), "expertScenes/StudentAndColorEffectedScene.fxml"));
            }
        }
    }

    @Override
    public void showDisconnection() {
        Platform.runLater(() -> SceneController.showAlert("Message for you!", "Disconnection of the server or a player"));

    }

    @Override
    public void confirmMoveStudent() {
        SceneController.updateArchipelagosOnMoveStudent();
        SceneController.updateBoardOnMoveStudent();
    }


    /**
     * Show the view a generic message
     * @param genericMessage could be a phrase that help the client
     */
    @Override
    public void showGenericMessage(String genericMessage) {
        Platform.runLater(() -> SceneController.showAlert("Message for you!", genericMessage));
    }
    /**
     * Show the view an error message
     * @param error could be a phrase that help the client doing the right choice
     */
    @Override
    public void showError(String error) {
        Platform.runLater(() -> {
            SceneController.showAlert("ERROR", error);
        });
    }

    /**
     * Communicate if someone won the game
     * @param message endOfMatch
     * @param isWinner tell if the match has been won
     */
    @Override
    public void showWinMessage(EndMatchMessage message, Boolean isWinner) {
        List<String> winners = message.getWinners().stream().map(Player::getUsername).collect(Collectors.toList());
        Platform.runLater(()-> SceneController.setEndingScene(winners, isWinner)
        );
    }

}
