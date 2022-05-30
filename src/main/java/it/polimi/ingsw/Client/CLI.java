package it.polimi.ingsw.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class CLI extends Client{
    protected CLIHandler cliHandler = new CLIHandler(this);
    protected Scanner scanner;

    public CLI(String ip, int port) {
        super(ip, port);

    }

    @Override
    public Thread asyncWriteToSocket(Object inputFromUSer){
        Thread thread = new Thread(() -> {
            try{
                scanner = new Scanner(System.in);
                while (isActive()){
                    String inputLine = scanner.nextLine(); //Scan input from command line
                    if(this.getRemoteModel().getAssistantsCardsMap().containsKey(inputLine)) {
                        characterCard(inputLine);
                    }
                    Message message = cliHandler.convertInputToMessage(inputLine, super.turnPhase); //Create message from input

                    if(message != null) {
                        super.outputStream.writeObject(message); // prepare the outputStream from the client to the server
                        super.outputStream.flush(); //send message derivate from input to the server
                    }else if(inputLine.equals("quit")){
                    //to add
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                //setActive(false);
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
                //setActive(false);
            }
        });
        thread.start();
        return  thread;
    }


    public void login(){}

    @Override
    public Thread characterCard(Object inputFromUser) {
        Thread thread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            try{
                while (isActive()){
                    String inputLine = scanner.nextLine();
                    Message message = cliHandler.convertInputToMessage(inputLine, super.turnPhase);
                    if(message != null) {
                        super.outputStream.writeObject(message); // prepare the outputStream from the client to the server
                        super.outputStream.flush(); //send message derivate from input to the server
                        }
                    }
                } catch (IOException e) {
                throw new RuntimeException(e);
            }catch (Exception e){
                e.printStackTrace();
        }
            });
        thread.start();
        return  thread;

    }
}
