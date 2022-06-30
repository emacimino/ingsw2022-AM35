package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to control the archipelagos on the GUI
 */
public class ArchipelagoPanelController {
    @FXML
    public Circle blueStudent, greenStudent, pinkStudent, redStudent, yellowStudent;
    @FXML
    public Polygon motherNature;
    @FXML
    public ImageView whiteTower, greyTower, blackTower;
    @FXML
    public ImageView prohibitionCard;

    @FXML
    public Label numBlueStudents, numPinkStudents, numGreenStudents, numRedStudents, numYellowStudents;

    @FXML
    public Label numOfTowers;


    /**
     * Class used to set the archipelago
     * @param archipelago the archipelago that needs to be set
     */
    public void setArchipelago(Archipelago archipelago) {
            resetArchipelago();
        {
            int numberOfStudentBlue = archipelago.getStudentFromArchipelago().stream()
                    .filter(s -> s.getColor().equals(Color.BLUE))
                    .mapToInt(s -> 1).sum();
            if (numberOfStudentBlue > 0) {
                blueStudent.setVisible(true);
                numBlueStudents.setVisible(true);
                numBlueStudents.setText(String.valueOf(numberOfStudentBlue));
            }
            int numberOfStudentPink = archipelago.getStudentFromArchipelago().stream()
                    .filter(s -> s.getColor().equals(Color.PINK))
                    .mapToInt(s -> 1).sum();
            if (numberOfStudentPink > 0) {
                pinkStudent.setVisible(true);
                numPinkStudents.setVisible(true);
                numPinkStudents.setText(String.valueOf(numberOfStudentPink));
            }
            int numberOfStudentGreen = archipelago.getStudentFromArchipelago().stream()
                    .filter(s -> s.getColor().equals(Color.GREEN))
                    .mapToInt(s -> 1).sum();
            if (numberOfStudentGreen > 0) {
                greenStudent.setVisible(true);
                numGreenStudents.setVisible(true);
                numGreenStudents.setText(String.valueOf(numberOfStudentGreen));
            }
            int numberOfStudentRed = archipelago.getStudentFromArchipelago().stream()
                    .filter(s -> s.getColor().equals(Color.RED))
                    .mapToInt(s -> 1).sum();
            if (numberOfStudentRed > 0) {
                redStudent.setVisible(true);
                numRedStudents.setVisible(true);
                numRedStudents.setText(String.valueOf(numberOfStudentRed));
            }
            int numberOfStudentYellow = archipelago.getStudentFromArchipelago().stream()
                    .filter(s -> s.getColor().equals(Color.YELLOW))
                    .mapToInt(s -> 1).sum();
            if (numberOfStudentYellow > 0) {
                yellowStudent.setVisible(true);
                numYellowStudents.setVisible(true);
                numYellowStudents.setText(String.valueOf(numberOfStudentYellow));
            }
        }
        try {
            if (archipelago.getIsle().get(0).isThereTower()) {
                int numberOfTowers = archipelago.getIsle().size();
                numOfTowers.setVisible(true);
                numOfTowers.setText(String.valueOf(numberOfTowers));
                switch (archipelago.getIsle().get(0).getTower().getTowerColors()) {
                    case Gray -> {
                        greyTower.setVisible(true);
                        greyTower.setDisable(false);
                    }
                    case Black -> {
                        blackTower.setVisible(true);
                        blackTower.setDisable(false);
                    }
                    case White -> {
                        whiteTower.setVisible(true);
                        whiteTower.setDisable(false);
                    }
                    default -> System.out.println("COLOR OF TOWER NOT CORRECT, in archipelagoPanelController");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(archipelago.isMotherNaturePresence()){
            motherNature.setVisible(true);
        }

        if(archipelago.isProhibition())
            prohibitionCard.setVisible(true);
    }

    /**
     * Method used to reset the archipelagos in the game
     */
    private void resetArchipelago() {
        pinkStudent.setVisible(false);
        numPinkStudents.setVisible(false);

        redStudent.setVisible(false);
        numRedStudents.setVisible(false);

        greenStudent.setVisible(false);
        numGreenStudents.setVisible(false);

        yellowStudent.setVisible(false);
        numYellowStudents.setVisible(false);

        blueStudent.setVisible(false);
        numBlueStudents.setVisible(false);

        greyTower.setVisible(false);
        whiteTower.setVisible(false);
        blackTower.setVisible(false);
        numOfTowers.setVisible(false);

        motherNature.setVisible(false);
        prohibitionCard.setVisible(false);
    }

}
