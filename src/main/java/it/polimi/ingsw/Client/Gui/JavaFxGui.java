package it.polimi.ingsw.Client.Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.paint.*;

import java.io.File;
import java.io.ObjectInputStream;
import java.net.URL;

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
        Image iconCranio = new Image(new File("/Users/cami2/Documents/ingsw2022-AM35/src/main/java/resources/LOGO_CRANIO_CREATIONS.png").toURI().toString());
        primaryStage.getIcons().add(iconCranio);

        Text welcomeText = new Text();
        welcomeText.setText("Welcome, are you ready to play with Eriantys???");
        welcomeText.setX(50);
        welcomeText.setY(50);
        welcomeText.setFont(Font.font("Bradley Hand", 50));
        welcomeText.setFill(Color.DARKBLUE);
        root.getChildren().add(welcomeText);
        primaryStage.setMaximized(true);
        Image image = new Image(new File("/Users/cami2/Documents/ingsw2022-AM35/src/main/java/resources/EriantysMenu.jpeg").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setX(100);
        imageView.setY(100);
        root.getChildren().add(imageView);
        //primaryStage.setFullScreen(true);

        System.out.println(getClass().getResource("/fxml/menu.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        Scene buttonScene = new Scene(root2);
        primaryStage.setScene(buttonScene);
        primaryStage.show(); //used it to show the stage NB it needs to be at the end of start()
    }


}