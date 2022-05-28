package it.polimi.ingsw.Client.Gui;

import it.polimi.ingsw.Client.CLIENT2.UserView;
import it.polimi.ingsw.Client.Gui.Scene.SceneController;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.NetworkUtilities.Message.CurrentGameMessage;
import it.polimi.ingsw.Observer.Observable;
import javafx.application.Platform;
import java.util.List;

public class GUI extends Observable implements UserView {


    @Override
    public void askLogin() {
        Platform.runLater(()-> SceneController.setScene(getObservers(), "login.fxml"));
    }

    @Override
    public void askPlayAssistantCard(List<AssistantsCards> assistantsCards) {

    }

    @Override
    public void askMoveStudent(List<Student> students) {

    }

    @Override
    public void askMoveMotherNature(List<Archipelago> archipelagos) {

    }

    @Override
    public void askChooseCloud(List<Cloud> cloud) {

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
    public void showGameState(CurrentGameMessage currentGameMessage){
        Platform.runLater(()->{
            SceneController.showGame(currentGameMessage.getGame());
        });
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        Platform.runLater(() -> {
            SceneController.showAlert("Message for you!", genericMessage);
        });
    }

    @Override
    public void showDisconnectionMessage(String usernameDisconnected, String text) {

    }

    @Override
    public void showError(String error) {
        Platform.runLater(() -> {
            SceneController.showAlert("ERROR", error);
            SceneController.setScene(getObservers(), "login.fxml");
        });
    }

    @Override
    public void showBoard(Board board) {

    }

    @Override
    public void showLobby(List<String> usernames) {

    }

    @Override
    public void showMatchInfo() {

    }

    @Override
    public void showWinMessage(String winner) {

    }

}
