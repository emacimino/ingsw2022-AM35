package it.polimi.ingsw.Server;

public class JsonObjectsHandler {
    private static final int WAITING = 0;
    private static final int PINGPONG = 1;
    private static final int GAMESTARTED = 2;
    private static final int GAMEINPROGRESS = 3;
    private static final int GAMEOVER = 4;

    private int state = WAITING;

    public String processInput(String theInput) {
        String theOutput = null;
        if(state == WAITING)theOutput="Waiting for client connection";
        switch (theInput){
            case "Ping": {
                state = PINGPONG;
                theOutput = "Pong";
            }


        }
        return theOutput;
    }
}
