package it.polimi.ingsw.Client.Gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.*;

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
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.AQUA);
        primaryStage.setScene(scene);

        primaryStage.show(); //used it to show the stage NB it needs to be at the end of start()
    }


}
