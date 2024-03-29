package it.polimi.ingsw.Client.Gui.Scene.ExpertScenes;

import it.polimi.ingsw.Client.Gui.Scene.CardMap;
import it.polimi.ingsw.Client.Gui.Scene.GenericSceneController;
import it.polimi.ingsw.Client.Gui.Scene.SceneController;
import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.NetworkUtilities.PlayCharacterMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.*;

/**
 * A class that handles the students on the cards
 */
public class StudentCardSceneController extends GenericSceneController {

    @FXML
    Circle s1, s2, s3, s4, s5, s6;
    @FXML
    ImageView card;
    @FXML
    Button selectBtn;
    @FXML
    Label selectionLbl, instructionLbl;


    private Set<Integer> studentsSelected = new HashSet<>();
    private CharacterCard characterCard;

    /**
     * This method is used to initialize the class
     */
    private void initialize() {
        setImageOnCard(CardMap.characterCardImageMap.get(characterCard.getName()), card);
        setStudentsOnCard();
    }

    /**
     * This method is used to set the student on the cards
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
        for (Integer i : remoteModel.getStudentsOnCardMap().keySet()) {
            c = students.get(i-1);
            setRightColorStudent(c, remoteModel.getStudentsOnCardMap().get(i));
            c.setOnMouseClicked(MouseEvent -> {
                        if (studentsSelected.contains(i))
                            studentsSelected.remove(i);
                        else
                            studentsSelected.add(i);
                        updateSelectionLabel();
                    }
            );


        }
    }

    /**
     * This method is used to update the selection labels
     */
    private void updateSelectionLabel() {
        List<Color> color = new ArrayList<>();
        for (Integer i : studentsSelected) {
            color.add(remoteModel.getStudentsOnCardMap().get(i).getColor());
        }
        StringBuilder s = new StringBuilder("You have selected the following student \nof color\n");
        for (Color c : color) {
            s.append(c).append(", ");
        }
        selectionLbl.setText(s.toString());
    }

    /**
     * This method is used to set the image on the card
     * @param n element of the scene
     * @param name name of the card
     */
    private void setImageOnCard(String name, Node n) {
        javafx.scene.image.Image image = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource(name)).toExternalForm());
        ((ImageView) n).setImage(image);
        n.setDisable(false);
        n.setVisible(true);
    }

    /**
     * This method is used to set the students
     * @param node element of the scene
     * @param student student target
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
     * This method is used to select a student
     * @param event a user input
     */
    public void selectStudents(ActionEvent event) {
        remoteModel.setStudentSelected(studentsSelected.stream().toList());
        nextMove();
    }

    /**
     * This method is used to go on to the next move
     */
    private void nextMove() {
        System.out.println("student on card: " + remoteModel.getStudentsFromCard());
        for (Integer i : remoteModel.getStudentsFromCard()) {
            System.out.println(remoteModel.getStudentsOnCardMap().get(i).getColor());
        }
        switch (characterCard.getName()) {
            case "Princess" ->{
                SceneController.setActionScene(getObservers());
                notifyObserver(new PlayCharacterMessage(characterCard.getName(), 13, null, studentsSelected.stream().toList(), null));
            }
            case "Friar" -> Platform.runLater(() -> SceneController.setCharacterScene(getObservers(), "expertScenes/ArchipelagoEffectedScene.fxml"));
            case "Jester" -> Platform.runLater(() -> SceneController.setCharacterScene(getObservers(), "expertScenes/StudentAndColorEffectedScene.fxml"));
        }
    }

    /**
     * update the remote model
     * @param remoteModel remote model updated
     */
    @Override
    public void setRemoteModel(RemoteModel remoteModel) {
        this.remoteModel = remoteModel;
        characterCard = remoteModel.getCharacterCardMap().get(remoteModel.getActiveCharacterCard());
        initialize();
    }
}
