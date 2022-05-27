package it.polimi.ingsw.Client.Gui.Scene;


import it.polimi.ingsw.Observer.Observer;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SceneController {
    private static Scene activeScene;
    private static GenericSceneController activeController;
    private static Stage stage;


    public static <T> T changeRootPane(List<Observer> observers, Scene scene, String fxml) {
        T controller = null;
        try {
            stage = (Stage)scene.getWindow();
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController(); //mi serve per aggiungere gli observer alla nuova scena
            ((GenericSceneController)controller).addAllObserver(observers);

            activeController = (GenericSceneController) controller;
            activeScene = scene;
            activeScene.setRoot(root);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); //TO DO
        }
        return controller;
    }

    public static <T> T changeRootPane(List<Observer> observers, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        return changeRootPane(observers, scene, fxml);
    }



}
