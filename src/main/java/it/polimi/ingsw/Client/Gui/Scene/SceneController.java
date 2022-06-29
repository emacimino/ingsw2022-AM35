package it.polimi.ingsw.Client.Gui.Scene;


import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.Gui.Scene.ExpertScenes.ChooseCharacterCardSceneController;
import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.CloudInGame;
import it.polimi.ingsw.Observer.Observer;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SceneController {
    private static Scene activeScene;
    private static GenericSceneController activeController;

    private static Parent wizardBoardParent;
    private static BoardsSceneController wizardBoardController;
    private static Parent gameSceneParent;
    private static GameSceneController gameSceneController;

    public static GenericSceneController getActiveController() {
        return activeController;
    }

    public static void changeRootPane(List<Observer> observers, Scene scene, String fxml) {
        GenericSceneController controller;
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController(); //mi serve per aggiungere gli observer alla nuova scena
            controller.addAllObserver(observers);

            activeController = controller;
            activeScene = scene;
            activeController.setRemoteModel((Objects.requireNonNull(getClientController(observers))).getRemoteModel());
            activeScene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeRootPane(List<Observer> observers, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        changeRootPane(observers, scene, fxml);
    }

    public static void showAlert(String title, String message) {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/alertScene.fxml"));
        Parent parent;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        AlertSceneController alertSceneController = loader.getController();
        Scene alertScene = new Scene(parent);
        alertSceneController.setScene(alertScene);
        alertSceneController.setAlertTitle(title);
        alertSceneController.setAlertMessage(message);
        alertSceneController.displayAlert();
    }

    public static void showGame(Game game) {
        if (activeController instanceof ActionSceneController) {
            return;
        }
        if(!(activeController instanceof GameSceneController)) {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/gameScene.fxml"));
            List<Observer> observers = activeController.getObservers();
            try {
                gameSceneParent = loader.load();
                gameSceneController = loader.getController();
                gameSceneController.addAllObserver(observers);
                gameSceneController.setGame(game);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            gameSceneController.updateArchipelagoOnInfoGame(game.getArchipelagos());
            gameSceneController.updateCloudsOnInfoGame(game.getClouds());
            gameSceneController.updateAssistantCardOnInfoGame(game.getAssistantsCardsPlayedInRound());
        }
        activeController = gameSceneController;
        activeScene.setRoot(gameSceneParent);
    }

    public static void loadCharacterCards(Map<String, CharacterCard> characterCard) {
        if (activeController instanceof GameSceneController) {
            ((GameSceneController) activeController).loadCharacterCards(characterCard);
        }
    }

    public static Scene getActiveScene() {
        return activeScene;
    }

    public static void setScene(List<Observer> observers, String fxml) {
        changeRootPane(observers, activeScene, fxml);
    }


    public static void showAssistantsCardOption(List<AssistantsCards> assistantsCards) {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/assistantScene.fxml"));
        Parent parent;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        AssistantSceneController assistantSceneController = loader.getController();
        Scene assistantScene = new Scene(parent);
        assistantSceneController.setAssistants(assistantsCards);
        List<Observer> observers = activeController.getObservers();
        assistantSceneController.addAllObserver(observers);
        assistantSceneController.setScene(assistantScene);
        assistantSceneController.displayOptions();
    }

    public static void showWizardsBoards(List<Observer> observers) {
        if(wizardBoardController == null) {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/boardsScene.fxml"));
            try {
                wizardBoardParent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            wizardBoardController = loader.getController();
            Scene boardScene = new Scene(wizardBoardParent);
            wizardBoardController.setRemoteModel(Objects.requireNonNull(getClientController(observers)).getRemoteModel());
            wizardBoardController.addAllObserver(observers);
            wizardBoardController.setScene(boardScene);
        }else{
            List<Wizard> wizards = Objects.requireNonNull(getClientController(observers)).getRemoteModel().getGame().getWizards();
            wizardBoardController.updateBoards(wizards);
        }

        wizardBoardController.display();
    }

    public static void showCharacterCardsOption(List<Observer> observers) {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/chooseCharacterCardScene.fxml"));
        Parent parent;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        ChooseCharacterCardSceneController chooseCharacterCardSceneController = loader.getController();
        Scene assistantScene = new Scene(parent);
        chooseCharacterCardSceneController.setCharacters(Objects.requireNonNull(getClientController(observers)).getRemoteModel().getCharacterCardMap());
        chooseCharacterCardSceneController.addAllObserver(observers);
        chooseCharacterCardSceneController.setScene(assistantScene);
        chooseCharacterCardSceneController.displayOptions();

    }


    public static void letMoveMotherNature() {
        //setActionScene(activeController.getObservers(), "actionScene.fxml");
        ((ActionSceneController) activeController).setMoveMN(true);
    }

    public static void enableClouds(CloudInGame cloud, Game game) {
        if (!(activeController instanceof GameSceneController)) {
            activeController = gameSceneController;
            showGame(game);
        }
        ((GameSceneController) activeController).enableCloud();
    }

    public static void setEndingScene(List<String> winners, Boolean isWinner) {
        setScene(activeController.getObservers(), "endScene.fxml");
        if (isWinner)
            ((EndSceneController) activeController).setWinMessage(winners);
        else
            ((EndSceneController) activeController).setLoseMessage(winners);
    }

    public static ClientController getClientController(List<Observer> observers) {
        if (observers.get(0) instanceof ClientController)
            return (ClientController) observers.get(0);
        else return null;
    }

    public static void setActualSceneExpert() {
        if (activeController instanceof ActionSceneController) {
            ((ActionSceneController) activeController).setExpert();
        }
    }

    public static void setCharacterScene(List<Observer> observers, String fxml) {
        changeRootPane(observers, activeScene, fxml);
    }

    public static void setActionScene(List<Observer> observers, String actionFxml) {
        changeRootPane(observers, activeScene, actionFxml);
    }

    public static void updateBoardOnMoveStudent() {
        RemoteModel remoteModel = Objects.requireNonNull(getClientController(activeController.getObservers())).getRemoteModel();
        ((ActionSceneController)activeController).getBoardPanelController().updateBoardOnMoveStudent(remoteModel.getCurrentBoard());
    }

    public static void updateArchipelagosOnMoveStudent() {
        RemoteModel remoteModel = Objects.requireNonNull(getClientController(activeController.getObservers())).getRemoteModel();
        ((ActionSceneController)activeController).updateArchipelagoOnMoveStudent(remoteModel.getArchipelagosMap());
    }
}
