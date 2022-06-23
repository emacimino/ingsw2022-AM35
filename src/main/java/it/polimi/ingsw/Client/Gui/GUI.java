package it.polimi.ingsw.Client.Gui;

import it.polimi.ingsw.Client.UserView;
import it.polimi.ingsw.Client.Gui.Scene.SceneController;
import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.NetworkUtilities.*;
import it.polimi.ingsw.Observer.Observable;
import javafx.application.Platform;
import java.util.List;
import java.util.Map;

public class GUI extends Observable implements UserView {
    private RemoteModel remoteModel;

    @Override
    public void askLogin() {
        Platform.runLater(()-> SceneController.setScene(getObservers(), "login.fxml"));
    }

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

    @Override
    public void askToPlayAssistantCard(List<AssistantsCards> assistantsCards) {
        Platform.runLater(()->SceneController.showAssistantsCardOption(assistantsCards));
    }

    @Override
    public void askToMoveStudent() {
        Platform.runLater(()-> {
            SceneController.setActionScene(getObservers(), "actionScene.fxml");
            if(remoteModel.getCharacterCardMap().isEmpty())
                SceneController.setActualSceneExpert();
        });
    }

    @Override
    public void askMoveMotherNature(String message) {
        Platform.runLater(()->{
            SceneController.letMoveMotherNature();
            if(remoteModel.getCharacterCardMap().isEmpty())
                SceneController.setActualSceneExpert();
        });
    }

    @Override
    public void askChooseCloud(CloudInGame cloud) {
        Platform.runLater(()-> { //rifare la scena prendendo info da remote model
                SceneController.enableClouds(cloud, remoteModel.getGame());
        });

    }


    @Override
    public void showGameState(CurrentGameMessage currentGameMessage){
        Platform.runLater(()-> {
            SceneController.showGame(remoteModel.getGame());
            if(!remoteModel.getCharacterCardMap().isEmpty()){
                SceneController.loadCharacterCards(remoteModel.getCharacterCardMap());
            }
        });
    }

    @Override
    public void showCharactersCards(CharacterCardInGameMessage characterCardInGameMessage) {
        Platform.runLater(()-> SceneController.loadCharacterCards(characterCardInGameMessage.getCharacterCard()));
    }



    @Override
    public void loadArchipelagosOption(Map<Integer, Archipelago> archipelago) {
        Platform.runLater(() -> SceneController.loadArchipelagos(archipelago));
    }

    @Override
    public void loadStudentOnEntrance(Map<Integer, Student> students) {
        Platform.runLater(() -> SceneController.loadStudentOnEntrance(students));
    }

    @Override
    public void setRemoteModel(RemoteModel remoteModel) {
        this.remoteModel = remoteModel;
    }

    @Override
    public void showChosenCharacterCard() {
        switch (remoteModel.getActiveCharacterCard()){
            case "Archer", "Magician", "Knight", "Baker" -> {
                notifyObserver(new PlayCharacterMessage(remoteModel.getActiveCharacterCard(), 13, null, null, null));
                }
            case "Princess", "Friar" -> {
                SceneController.setCharacterScene(getObservers(), "expertScenes/studentCardScene.fxml");
            }
        }
    }


    @Override
    public void showGenericMessage(String genericMessage) {
        Platform.runLater(() -> SceneController.showAlert("Message for you!", genericMessage));
    }

    @Override
    public void showError(String error) {
        Platform.runLater(() -> {
            SceneController.showAlert("ERROR", error);
            //SceneController.setScene(getObservers(), "login.fxml");
        });
    }

    @Override
    public void loadBoard(Board board) {
        Platform.runLater(() -> SceneController.loadBoard(board));
    }


    @Override
    public void showWinMessage(EndMatchMessage message, Boolean isWinner) {
        Platform.runLater(()-> SceneController.setEndingScene(message.getWinners().stream().map(Player::getUsername).toList(), isWinner)
        );
    }

    @Override
    public void showDisconnectionMessage(String usernameDisconnected, String text) {

    }
}
