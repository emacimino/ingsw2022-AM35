package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.View.RemoteView;

import java.io.ObjectInputStream;
import java.util.Scanner;

public class CLI extends Client{
    RemoteView remoteView;
    CLIHandler cliHandler = new CLIHandler();

    public CLI(String ip, int port) {
        super(ip, port);

    }

    @Override
    public Thread asyncWriteToSocket(final Scanner scanner){
        Thread thread = new Thread(() -> {
            try{
                while (isActive()){
                    String inputLine = scanner.nextLine(); //Scan input from command line
                    Message message = cliHandler.convertInputToMessage(inputLine, super.turnPhase); //Create message from input
                    if(message != null) {
                        super.outputStream.writeObject(message); // prepare the outputStream from the client to the server
                        super.outputStream.flush(); //send message derivate from input to the server
                    }else if(inputLine.equals("quit")){

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                setActive(false);
            }
        });
        thread.start();
        return  thread;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream inputObject){
        Thread thread = new Thread(() -> {
            try{
                while (isActive()){
                    Message inputFromServer = (Message) inputObject.readObject(); //read and cast the input from server
                    setNextAction(inputFromServer);
                    cliHandler.showMessage(inputFromServer); //manage to show to the user what is need to be shown
                }
            }catch (Exception e){
                e.printStackTrace();
                setActive(false);
            }
        });
        thread.start();
        return  thread;
    }

    public void login(){

    }
}
