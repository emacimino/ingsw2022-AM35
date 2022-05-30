package it.polimi.ingsw.Client.Gui;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
        Scene scene = new Scene(root, Color.LIGHTSKYBLUE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Eriantys");
        Image iconCranio = new Image("file:LOGO_CRANIO_CREATIONS.png");
        primaryStage.getIcons().add(iconCranio);

        Text welcomeText = new Text();
        welcomeText.setText("Welcome, are you ready to play with Eriantys???");
        welcomeText.setX(50);
        welcomeText.setY(50);
        root.getChildren().add(welcomeText);
        primaryStage.setMaximized(true);
        // primaryStage.setFullScreen(true);
        primaryStage.show(); //used it to show the stage NB it needs to be at the end of start()
    }


}
