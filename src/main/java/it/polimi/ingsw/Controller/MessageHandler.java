package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHandler {
    private Map<Integer, Student> studentsOnEntranceMap = new HashMap<>();
    private Map<Integer, Archipelago> archipelagoMap = new HashMap<>();
    private Map<Integer, Cloud> cloudMap = new HashMap<>();
    private final Map<String, Color> colorMap = setColorMap();

    protected void setArchipelagoMap(List<Archipelago> archipelagos){
        archipelagoMap.clear();
        Integer i = 1;
        for(Archipelago a: archipelagos){
            archipelagoMap.put(i, a);
            i++;
        }
    }
    protected void setStudentOnEntranceMap(List<Student> students){
        studentsOnEntranceMap.clear();
        Integer i = 1;
        for(Student s: students){
            studentsOnEntranceMap.put(i, s);
            i++;
        }
    }
    protected void setCloudMap(List<Cloud> clouds){
        cloudMap.clear();
        Integer i = 1;
        for(Cloud c: clouds){
            cloudMap.put(i, c);
            i++;
        }
    }
    private Map<String, Color>  setColorMap(){
        Map<String, Color> map = new HashMap<>();
        for(Color c: Color.values()){
            switch (c){
                case BLUE -> map.put("BLUE", Color.BLUE);
                case GREEN -> map.put("GREEN", Color.GREEN);
                case YELLOW -> map.put("YELLOW", Color.YELLOW);
                case PINK -> map.put("PINK", Color.PINK);
                case RED -> map.put("RED", Color.RED);
            }
        }
        return map;
    }

    public Map<Integer, Student> getStudentsOnEntranceMap() {
        return studentsOnEntranceMap;
    }
    public Map<Integer, Archipelago> getArchipelagoMap() {
        return archipelagoMap;
    }
    public Map<Integer, Cloud> getCloudMap() {
        return cloudMap;
    }
    public Map<String, Color> getColorMap() {
        return colorMap;
    }

}
