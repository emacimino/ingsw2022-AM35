package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Model.Wizard.TableOfStudents;
import it.polimi.ingsw.Model.Wizard.Tower;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BoardPanelController {
    @FXML
    private VBox entrance1, entrance2;
    @FXML
    private HBox greenTable, pinkTable, redTable, blueTable, yellowTable;
    @FXML
    private StackPane towersPane;
    @FXML
    private ImageView profRed, profPink, profYellow, profBlue, profGreen;
    @FXML
    private Label wizardLbl;


    public void setBoard(Board board, String wizardName) {
        for (TableOfStudents t : board.getTables()) {
            loadStudentsOnTable(getTable(t), t.getStudentsInTable().stream().toList());
        }
        setProfessors(board.getProfessorInTable());
        setTowers(board.getTowersInBoard());
        setStudentsInEntrance(board.getStudentsInEntrance().stream().toList());
        wizardLbl.setText("Board of Wizard: " + wizardName);

    }

    private void setStudentsInEntrance(List<Student> studentsInEntrance) {
        List<Node> nodes1 = entrance1.getChildren();
        List<Node> nodes2 = entrance2.getChildren();
        for (int i = 0; i < studentsInEntrance.size(); i++) {
            if (i < 4) {
                setRightColorStudent(nodes1.get(i), studentsInEntrance.get(i).getColor());
            } else {
                setRightColorStudent(nodes2.get(i-4), studentsInEntrance.get(i-4).getColor());
            }
        }

    }

    private void setRightColorStudent(Node node, Color color) {
        switch (color) {
            case GREEN -> ((Circle) node).setFill(Paint.valueOf("#3cc945"));
            case RED -> ((Circle) node).setFill(Paint.valueOf("#cd1a1a"));
            case YELLOW -> ((Circle) node).setFill(Paint.valueOf("#e3ff0c"));
            case BLUE -> ((Circle) node).setFill(Paint.valueOf("#ff62e5"));
            case PINK -> ((Circle) node).setFill(Paint.valueOf("#1e90ff"));
        }
        node.setVisible(true);
    }

    private void setTowers(Collection<Tower> towersInBoard) {
        List<Node> nodes = towersPane.getChildren();
        for (int i = 0; i < towersInBoard.size(); i++) {
            javafx.scene.image.Image image = takeColorOfTower(towersInBoard.stream().toList().get(0));
            ((ImageView) nodes.get(i)).setImage(image);
            nodes.get(i).setVisible(true);
        }
    }

    private Image takeColorOfTower(Tower tower) {
        switch (tower.getTowerColors()) {
            case Gray -> {
                return new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("/Graphics/GreyTower.PNG")).toExternalForm());
            }
            case Black -> {
                return new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("/Graphics/BlackTower.PNG")).toExternalForm());
            }
            case White -> {
                return new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("/Graphics/WhiteTower.PNG")).toExternalForm());
            }
            default -> {
                return null;
            }
        }
    }

    private void loadStudentsOnTable(HBox table, List<Student> students) {
        for (int i = 0; i < students.size(); i++) {
            table.getChildren().get(i).setVisible(true);
        }
    }

    private HBox getTable(TableOfStudents t) {
        switch (t.getColor()) {
            case GREEN -> {
                return greenTable;
            }
            case BLUE -> {
                return blueTable;
            }
            case PINK -> {
                return pinkTable;
            }
            case RED -> {
                return redTable;
            }
            case YELLOW -> {
                return yellowTable;
            }
            default -> {
                return null;
            }

        }
    }

    private void setProfessors(List<Professor> professors) {
        for (Professor p : professors) {
            switch (p.getColor()) {
                case RED -> profRed.setVisible(true);
                case PINK -> profPink.setVisible(true);
                case GREEN -> profGreen.setVisible(true);
                case YELLOW -> profYellow.setVisible(true);
                case BLUE -> profBlue.setVisible(true);
            }
        }
    }
}
