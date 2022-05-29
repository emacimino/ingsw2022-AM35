package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class CloudPanelController {

    private List<Circle> circles = new ArrayList<>();
    @FXML
    private Circle s1,s2,s3,s4;

    public void setCloud(Cloud cloud) {
        circles.add(0,s1);
        circles.add(1,s2);
        circles.add(2,s3);
        circles.add(3,s4);
        initialize(cloud);
    }

    private void initialize(Cloud cloud) {
        List<Student> students = cloud.getStudentOnCloud().stream().toList();
        for(int i = 0; i < students.size(); i++){
            circles.get(i).setVisible(true);
            switch(students.get(i).getColor()){
                case RED -> circles.get(i).setFill(Paint.valueOf("#bf2727"));
                case YELLOW -> circles.get(i).setFill(Paint.valueOf("#e3ee44"));
                case PINK -> circles.get(i).setFill(Paint.valueOf("#ff9ff4"));
                case BLUE -> circles.get(i).setFill(Paint.valueOf("#0084ff"));
                case GREEN -> circles.get(i).setFill(Paint.valueOf("#ff921f"));
            }
        }
    }
}
