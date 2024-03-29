package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used to control the clouds on the GUI
 */
public class CloudPanelController {

    private List<Circle> circles = new ArrayList<>();
    @FXML
    public Circle s1,s2,s3,s4;

    /**
     * Set method for clouds
     * @param cloud the cloud to be set
     */
    public void setCloud(Cloud cloud) {
        s1.setVisible(false);
        s2.setVisible(false);
        s3.setVisible(false);
        s4.setVisible(false);
        circles.add(0,s1);
        circles.add(1,s2);
        circles.add(2,s3);
        circles.add(3,s4);
        initialize(cloud);
    }

    /**
     * This method is used to put students on the cloud
     * @param cloud the target cloud
     */
    private void initialize(Cloud cloud) {
        List<Student> students = cloud.getStudentOnCloud().stream().toList();
        for(int i = 0; i < students.size(); i++){
            circles.get(i).setVisible(true);
            switch(students.get(i).getColor()){
                case RED -> circles.get(i).setFill(Paint.valueOf("#cd1a1a"));
                case YELLOW -> circles.get(i).setFill(Paint.valueOf("#e3ff0c"));
                case PINK -> circles.get(i).setFill(Paint.valueOf("#ff62e5"));
                case BLUE -> circles.get(i).setFill(Paint.valueOf("DODGERBLUE"));
                case GREEN -> circles.get(i).setFill(Paint.valueOf("#3cc945"));
            }
        }
    }
}
