package it.polimi.ingsw.Client.Gui.Scene;


import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Observer.Observer;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

public class SceneController {
    private static Scene activeScene ;
    private static GenericSceneController activeController;


    public static void changeRootPane(List<Observer> observers, Scene scene, String fxml) {
        GenericSceneController controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController(); //mi serve per aggiungere gli observer alla nuova scena

            //prendo il controller della nuova scena, ma mi aggiunge 2 observer, perche
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
            System.out.println(e.getMessage());
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
        GameSceneController controller = null;
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

    public static Scene getActiveScene() {
        return activeScene;
    }

    public static void setScene(List<Observer> observers, String fxml) {
        changeRootPane(observers, activeScene, fxml);
    }
}
