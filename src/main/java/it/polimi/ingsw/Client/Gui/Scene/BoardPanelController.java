package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Client.RemoteModel;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.*;

/**
 * Class used to control the Board on the GUI
 */
public class BoardPanelController extends GenericSceneController{
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
    @FXML
    private Text studentSelectedTxt, colorSelectedTxt, coinsTxt;

    private String colorSel = "Color selected: ";
    private Student studentToMove;
    private Map<Node, Student> studentEntranceMap = new HashMap<>();
    private boolean onBoard = false;
    private Map<HBox, Color> tableToColorMap = new HashMap<>();
    private Map<Color, HBox> colorToTableMap = new HashMap<>();


    /**
     * Method used to set the board
     * @param board the board to set
     * @param wizardName the name of the wizard that owns the board
     */
    public void setBoard(Board board, String wizardName) {
        for (TableOfStudents t : board.getTables()) {
            loadStudentsOnTable(getTable(t), t.getStudentsInTable().stream().toList());
        }
        setProfessors(board.getProfessorInTable());
        setTowers(board.getTowersInBoard());
        setStudentsInEntrance(board.getStudentsInEntrance().stream().toList());
        wizardLbl.setText(wizardName + " board");
        coinsTxt.setText("coins: " + board.getCoins());

    }

    /**
     * Method used to set students in the entrance of the board
     * @param studentsInEntrance a list of students in entrance
     */
    private void setStudentsInEntrance(List<Student> studentsInEntrance) {
        List<Node> nodes1 = entrance1.getChildren();
        List<Node> nodes2 = entrance2.getChildren();
        for (int i = 0; i < studentsInEntrance.size(); i++) {
            if (i < 4) {
                setRightColorStudent(nodes1.get(i), studentsInEntrance.get(i));
            } else {
                setRightColorStudent(nodes2.get(i - 4), studentsInEntrance.get(i));
            }
        }
    }

    /**
     * Method used to set the color of the students
     * @param node a node used to display students
     * @param student student to display
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
        node.setVisible(true);
        studentEntranceMap.put(node, student);
    }

    /**
     * Method used to set the towers in the board
     * @param towersInBoard a collection of towers in the board
     */
    private void setTowers(Collection<Tower> towersInBoard) {
        List<Node> nodes = towersPane.getChildren();
        for (int i = 0; i < towersInBoard.size(); i++) {
            javafx.scene.image.Image image = takeColorOfTower(towersInBoard.stream().toList().get(0));
            ((ImageView) nodes.get(i)).setImage(image);
            nodes.get(i).setVisible(true);
        }
    }

    /**
     * Method that returns the color of the towers
     * @param tower tower to get the color from
     * @return an image with the towers of the correct color
     */
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

    /**
     * Method used to load students on the table
     * @param table a graphical tablet o arrange the students
     * @param students students to load on the table
     */
    private void loadStudentsOnTable(HBox table, List<Student> students) {
        for (int i = 0; i < students.size(); i++) {
            table.getChildren().get(i).setVisible(true);
        }
    }

    /**
     * Method used to get the table
     * @param t a table of students
     * @return a box to display the table
     */
    private HBox getTable(TableOfStudents t) {
        return colorToTableMap.get(t.getColor());
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

    /**
     * Method used to set the movable students on the entrance
     * @param students a map of students
     */
    public void setMovableStudentOnEntrance(Map<Integer, Student> students) {
        setStudentsInEntrance(students.values().stream().toList());
    }

    /**
     * Method used to select a student on click
     * @param event the input of the player
     */
    public void onStudentClick(MouseEvent event){
        Node node = (Circle) event.getTarget();
        studentToMove = studentEntranceMap.get(node);
        studentSelectedTxt.setText("");
        studentSelectedTxt.setText("You have selected a " + studentToMove.getColor() + " student");

    }

    /**
     * Method used to get the color of the table
     * @param table a box representing a table of students
     * @return a color
     */
    public Color getColorOfTable(HBox table){
        return tableToColorMap.get(table);
    }

    /**
     * Method that returns the students to move
     * @return a student
     */
    public Student getStudentToMove() {
        return studentToMove;
    }

    /**
     * Method used when the expert match is selected
     */
    public void enableExpert(){
        for(HBox t : tableToColorMap.keySet()){
            t.setDisable(false);
            t.setOnMouseClicked(mouseEvent -> {
                remoteModel.setColorSelected(getColorOfTable(t));
                colorSel = colorSel + getColorOfTable(t) + " ";
                colorSelectedTxt.setText(colorSel);
            });
        }
        for(Node n : entrance1.getChildren()){
            n.setOnMouseClicked(mouseEvent -> {
                studentToMove = studentEntranceMap.get(n);
                remoteModel.setStudentFromEntrance(studentToMove);
                System.out.println("on student click: " + remoteModel.getStudentFromEntrance());
            });
        }
        for(Node n : entrance2.getChildren()){
            n.setOnMouseClicked(mouseEvent -> {
                studentToMove = studentEntranceMap.get(n);
                remoteModel.setStudentFromEntrance(studentToMove);
                System.out.println("on student click: " + remoteModel.getStudentFromEntrance());
            });
        }
    }

    /**
     * update the remote model
     * @param remoteModel remote model updated
     */
    @Override
    public void setRemoteModel(RemoteModel remoteModel){
        this.remoteModel = remoteModel;
        setTablesMap();
    }

    /**
     * Method that sets the tables of students map
     */
    private void setTablesMap() {
        tableToColorMap.put(greenTable, Color.GREEN);
        tableToColorMap.put(blueTable, Color.BLUE);
        tableToColorMap.put(yellowTable, Color.YELLOW);
        tableToColorMap.put(pinkTable, Color.PINK);
        tableToColorMap.put(redTable, Color.RED);

        colorToTableMap.put(Color.GREEN, greenTable);
        colorToTableMap.put(Color.BLUE, blueTable);
        colorToTableMap.put(Color.YELLOW, yellowTable);
        colorToTableMap.put(Color.PINK, pinkTable);
        colorToTableMap.put(Color.RED, redTable);

    }

    /**
     * Method used to clear the current selection
     */
    public void clearSelection() {
        studentSelectedTxt.setText("");
        colorSelectedTxt.setText("");
        colorSel = "Color selected: ";

    }
}
