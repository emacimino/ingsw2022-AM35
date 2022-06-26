package it.polimi.ingsw.Client.Gui;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.Gui.Scene.MenuSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main class for GUI
 */
public class JavaFxGui extends Application{
    /**
     * Start method for the GUI
     * @param primaryStage the first stage of the UI
     */
    @Override
    public void start(Stage primaryStage){
        GUI gui = new GUI();
        ClientController clientController = new ClientController(gui);
        gui.addObserver(clientController);

        primaryStage.setTitle("Eriantys");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/menuScene.fxml"));



        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        MenuSceneController actualController = loader.getController();
        actualController.addObserver(clientController);
        //create the relation between the controller first controller and the guiHandler and gui
        //to update everytime there is a change of sceneController
        //quando il controller viene modificato/invocato manderà un notify a guiHandler
        //la quale manderà una notify alla gui!!! che manderà il messaggio

        Scene menuScene = new Scene(root);
        primaryStage.setScene(menuScene);

        primaryStage.setHeight(900d);
        primaryStage.setWidth(1400d);
        primaryStage.setResizable(false);

        primaryStage.show(); //used it to show the stage NB it needs to be at the end of start()
    }

    /**
     * Method used to stop the GUI
     */
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
