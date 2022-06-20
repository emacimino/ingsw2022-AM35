package it.polimi.ingsw.Client.Gui.Scene;


import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.NetworkUtilities.Message.CloudInGame;
import it.polimi.ingsw.Observer.Observer;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SceneController {
    private static Game currentGame;
    private static Scene activeScene ;
    private static GenericSceneController activeController;
    private static Parent previousRoot;

    public static void changeRootPane(List<Observer> observers, Scene scene, String fxml) {
        GenericSceneController controller;
        if(activeScene != null) {
            previousRoot = activeScene.getRoot();
        }
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController(); //mi serve per aggiungere gli observer alla nuova scena

            controller.addAllObserver(observers);

            activeController = controller;
            activeScene = scene;
            activeScene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace(); //TO DO
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

    public static void showGame(Game game){
        if(game != null)
             currentGame = game;
        if(activeController instanceof ActionSceneController){
            return;
        }
        GameSceneController controller;
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/gameScene.fxml"));
        List<Observer> observers = activeController.getObservers();
       try {
           Parent root = loader.load();
           controller = loader.getController();
           controller.addAllObserver(observers);
           controller.setGame(game);
           activeController = controller;
           activeScene.setRoot(root);

       }catch (IOException e){
           e.printStackTrace();
       }
    }

    public static void loadCharacterCards(Map<String, CharacterCard> characterCard){
        if(activeController instanceof GameSceneController){
            ((GameSceneController) activeController).loadCharacterCards(characterCard);
        }
    }

    public static Scene getActiveScene() {
        return activeScene;
    }

    public static void setScene(List<Observer> observers, String fxml) {
        changeRootPane(observers, activeScene, fxml);
    }

    public static void backScene(Event event){
        if(activeScene != null) {
            activeScene.setRoot(previousRoot);
        }
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
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/boardsScene.fxml"));
        Parent parent;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        BoardsSceneController boardsSceneController = loader.getController();
        Scene boardScene = new Scene(parent);
        boardsSceneController.setBoards(currentGame.getWizards());
        boardsSceneController.addAllObserver(observers);
        boardsSceneController.setScene(boardScene);
        boardsSceneController.display();
    }

    public static void loadArchipelagos(Map<Integer, Archipelago> archipelago) {
        if(activeController instanceof ActionSceneController){
            ((ActionSceneController) activeController).setArchipelagos(archipelago);
        }
    }

    public static void loadBoard(Board board) {
        if(activeController instanceof ActionSceneController){
            ((ActionSceneController) activeController).setBoard(board);
        }
    }

    public static void loadStudentOnEntrance(Map<Integer, Student> students) {
        if(activeController instanceof ActionSceneController){
            ((ActionSceneController) activeController).loadStudentsMovable(students);
        }
    }

    public static void letMoveMotherNature() {
        if(activeController instanceof ActionSceneController){
            ((ActionSceneController) activeController).setMoveMN(true);
        }
    }

    public static void enableClouds(CloudInGame cloud) {
        if(! (activeController instanceof GameSceneController)){
            setScene(activeController.getObservers(), "gameScene.fxml");
            ((GameSceneController)activeController).setGame(currentGame);
        }
        ((GameSceneController)activeController).enableCloud(cloud.getCloudMap());
    }

    public static void setEndingScene(List<String> winners, Boolean isWinner) {
        setScene(activeController.getObservers(), "endScene.fxml");
        if(isWinner)
            ((EndSceneController)activeController).setWinMessage(winners);
        else
            ((EndSceneController)activeController).setLoseMessage(winners);
    }
}
