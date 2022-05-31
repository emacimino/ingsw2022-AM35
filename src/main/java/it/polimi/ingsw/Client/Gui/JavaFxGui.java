package it.polimi.ingsw.Client.Gui;

import it.polimi.ingsw.Client.CLIENT2.ClientController;
import it.polimi.ingsw.Client.Gui.Scene.MenuSceneController;
import it.polimi.ingsw.Client.Gui.Scene.ServerInfoSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

public class JavaFxGui extends Application {
    public Thread asyncReadFromSocket(ObjectInputStream socketInput) {
        return null;
    }

    public Thread asyncWriteToSocket() {
        return null;
    }


   //lo start verrà chiamato in modo indiretto, attraverso il metodo launch() che sara invocato al momento di una creazione di GUI
    //dal main
    @Override
    public void start(Stage primaryStage){
        GUI gui = new GUI();
        ClientController clientController = new ClientController(gui);
        gui.addObserver(clientController);

        primaryStage.setTitle("Eriantys");
        Image iconCranio = new Image(new File("/Users/cami2/Documents/ingsw2022-AM35/src/main/java/resources/LOGO_CRANIO_CREATIONS.png").toURI().toString());
        primaryStage.getIcons().add(iconCranio);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/menuScene.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

        MenuSceneController actualController = loader.getController();
        actualController.addObserver(clientController);
        //create the relation between the controller first controller and the guiHandler and gui
        //to update everytime there is a change of sceneController
        //quando il controller viene modificato/invocato mandera un notify a guiHandler
        //la quale manderà una notify alla gui!!! che mander il messaggio

        Scene menuScene = new Scene(root);
        primaryStage.setScene(menuScene);

        primaryStage.setHeight(900d);
        primaryStage.setWidth(1400d);
        primaryStage.setResizable(false);

        primaryStage.show(); //used it to show the stage NB it needs to be at the end of start()
    }

    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
