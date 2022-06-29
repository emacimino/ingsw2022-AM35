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

/**
 * A class that handles the scenes
 */
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

    /**
     * This method is used to load resources and set controller and remote model
     * @param observers list of game observers
     * @param scene the scene to load
     * @param fxml fxml string
     */
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

    /**
     * This method calls the changeRootPane method
     * @param observers a list of game observers
     * @param event user input
     * @param fxml fxml string
     */
    public static void changeRootPane(List<Observer> observers, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        changeRootPane(observers, scene, fxml);
    }

    /**
     * This method is used to display an alert
     * @param title title of the alert
     * @param message message of the alert
     */
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

    /**
     * This method is used to load a game on screen
     * @param game the current game
     */
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

    /**
     * This method is used to load character cards on screen
     * @param characterCard the map of the character cards
     */
    public static void loadCharacterCards(Map<String, CharacterCard> characterCard) {
        if (activeController instanceof GameSceneController) {
            ((GameSceneController) activeController).loadCharacterCards(characterCard);
        }
    }

    /**
     * This method returns the active scene
     * @return a scene
     */
    public static Scene getActiveScene() {
        return activeScene;
    }

    /**
     * This method is sued to switch scene
     * @param observers a list of game observers
     * @param fxml fxml string
     */
    public static void setScene(List<Observer> observers, String fxml) {
        changeRootPane(observers, activeScene, fxml);
    }

    /**
     * This method is used to load assistant cards on screen
     * @param assistantsCards a list of assistant cards
     */
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

    /**
     * This method is used to open the wizard board on screen
     * @param observers a list of game observers
     */
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

    /**
     * This method is used to show the character cards related option
     * @param observers a list of game observers
     */
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

    /**
     * This method is used to enbale the movement of mother nature
     */
    public static void letMoveMotherNature() {
        //setActionScene(activeController.getObservers(), "actionScene.fxml");
        ((ActionSceneController) activeController).setMoveMN(true);
    }

    /**
     * This method is used to enable the cloud selection
     * @param cloud the cloud to be selected
     * @param game the current game
     */
    public static void enableClouds(CloudInGame cloud, Game game) {
        if (!(activeController instanceof GameSceneController)) {
            activeController = gameSceneController;
            showGame(game);
        }
        ((GameSceneController) activeController).enableCloud();
    }

    /**
     * This method is used to set an ending scene
     * @param winners list of winners
     * @param isWinner true if a player is a winner
     */
    public static void setEndingScene(List<String> winners, Boolean isWinner) {
        setScene(activeController.getObservers(), "endScene.fxml");
        if (isWinner)
            ((EndSceneController) activeController).setWinMessage(winners);
        else
            ((EndSceneController) activeController).setLoseMessage(winners);
    }

    /**
     * This method returns the client controller
     * @param observers a list of game observers
     * @return a client controller or null
     */
    public static ClientController getClientController(List<Observer> observers) {
        if (observers.get(0) instanceof ClientController)
            return (ClientController) observers.get(0);
        else return null;
    }

    /**
     * This method is used to set the scene on expert mode
     */
    public static void setActualSceneExpert() {
        if (activeController instanceof ActionSceneController) {
            ((ActionSceneController) activeController).setExpert();
        }
    }

    /**
     * This method is used to set the character cards scene
     * @param observers a list of game observers
     * @param fxml fxml string
     */
    public static void setCharacterScene(List<Observer> observers, String fxml) {
        changeRootPane(observers, activeScene, fxml);
    }

    /**
     * This method is used to set the action scene
     * @param observers a list of game observers
     * @param actionFxml fxml string
     */
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
