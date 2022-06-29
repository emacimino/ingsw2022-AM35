package it.polimi.ingsw.Client.Gui.Scene.ExpertScenes;

import it.polimi.ingsw.Client.Gui.Scene.CardMap;
import it.polimi.ingsw.Client.Gui.Scene.GenericSceneController;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that handles character cards on the GUI
 */
public class CardController extends GenericSceneController {
    @FXML
    Circle s1, s2, s3, s4, s5, s6;
    @FXML
    ImageView card, p1, p2, p3, p4;

    private final Stage stage;
    private CharacterCard characterCard;
    private final List<ImageView> prohibitions = new ArrayList<>();

    /**
     * Constructor of the class
     */
    public CardController() {
        this.stage = new Stage();
        stage.setAlwaysOnTop(false);
    }

    /**
     * This method is used to set the character cards
     * @param characterCard a character card
     */
    public void setCharacterCard(CharacterCard characterCard){
        this.characterCard = characterCard;
        setImageOnCard(CardMap.characterCardImageMap.get(characterCard.getName()), card);
        setStudentsOnCard();
        setProhibitions();
    }

    /**
     * This method is used to set prohibition effect
     */
    private void setProhibitions() {
        prohibitions.add(p1);
        prohibitions.add(p2);
        prohibitions.add(p3);
        prohibitions.add(p4);
        System.out.println(characterCard.getProhibitionPass());
        for(int i = 0; i < characterCard.getProhibitionPass(); i++){

            prohibitions.get(i).setVisible(true);
        }
    }

    /**
     * This method is used to set the color of the students after card usage
     * @param node element of the scene
     * @param student target student
     */
    private void setRightColorStudent(Node node, Student student) {
        Color color = student.getColor();
        switch (color) {
            case GREEN -> ((Circle) node).setFill(Paint.valueOf("#3cc945"));
            case RED -> ((Circle) node).setFill(Paint.valueOf("#cd1a1a"));
            case YELLOW -> ((Circle) node).setFill(Paint.valueOf("#e3ff0c"));
            case BLUE -> ((Circle) node).setFill(Paint.valueOf("#1e90ff"));
            case PINK -> ((Circle) node).setFill(Paint.valueOf("#ff62e5"));
        }
        node.setDisable(false);
        node.setVisible(true);
    }

    /**
     * This method is used to load the image on a card
     * @param name card name
     * @param card card image
     */
    private void setImageOnCard(String name, ImageView card) {
        javafx.scene.image.Image image = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource(name)).toExternalForm());
        card.setImage(image);
        card.setDisable(false);
        card.setVisible(true);
    }

    /**
     * This method is used to set students on cards
     */
    private void setStudentsOnCard() {
        List<Circle> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
        students.add(s6);
        Circle c;
        for (int i = 0; i < characterCard.getStudentsOnCard().size(); i++) {
            c = students.get(i);
            setRightColorStudent(c, characterCard.getStudentsOnCard().get(i));
        }
    }

    /**
     * This method is used to switch scene
     * @param scene new scene
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * This method is used to display options
     */
    public void displayOptions() {
        stage.showAndWait();
    }
}
