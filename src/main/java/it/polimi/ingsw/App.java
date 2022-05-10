package it.polimi.ingsw;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application{

    Button button; //button is a class of the javafx.scene  packet

    public static void main( String[] args ){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Title of the window");
        button = new Button();
        button.setText("Click me");

        StackPane layout = new StackPane(); //Stackpane è un layout per indicare come vedere la scene
        layout.getChildren().add(button);  //aggiungo button al layout

        Scene scene = new Scene(layout, 300, 250);
        //creo prima layout e poi lo imposto nella creazione della scene, aggiungo di seguito (opzionale) le dimensioni che voglio
        primaryStage.setScene(scene); //imposto la scena nello stage principale
        primaryStage.show();
    }
}
