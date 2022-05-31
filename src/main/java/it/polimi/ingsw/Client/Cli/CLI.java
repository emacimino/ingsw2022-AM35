package it.polimi.ingsw.Client.Cli;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.ObjectInputStream;
import java.util.Scanner;

public class CLI extends Client {
    protected CLIHandler cliHandler = new CLIHandler(this);
    protected Scanner scanner;

    public CLI(String ip, int port) {
        super(ip, port);

    }

    @Override
    public Thread asyncWriteToSocket(){
        Thread thread = new Thread(() -> {
            try{
                scanner = new Scanner(System.in);
                while (isActive()){
                    String inputLine = scanner.nextLine(); //Scan input from command line
                    Message message = cliHandler.convertInputToMessage(inputLine, super.turnPhase); //Create message from input
                    if(message != null) {
                        sendToServer(message);
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



}
