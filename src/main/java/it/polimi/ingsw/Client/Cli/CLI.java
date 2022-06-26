package it.polimi.ingsw.Client.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.UserView;
import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.NetworkUtilities.*;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLI extends Observable implements UserView {
    private final CLIHandler cliHandler = new CLIHandler(this);
    protected Scanner scanner;
    private RemoteModel remoteModel;
    // protected ObjectOutputStream outputStream;
    private boolean active = true;
    // protected ObjectInputStream socketIn;
    protected TurnPhase turnPhase = TurnPhase.LOGIN;


    /**
     * Scan the user input and elaborate the result
     *
     * @return a new thread that handle the reading functions
     */
    public Thread readFromInput() {
        Thread thread = new Thread(() -> {
            try {
                scanner = new Scanner(System.in);
                while (isActive()) {
                    String inputLine = scanner.nextLine(); //Scan input from command line
                    Message message = cliHandler.convertInputToMessage(inputLine, turnPhase); //Create message from input
                    if (message != null) {
                        notifyObserver(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                setActive(false);
            }
        });
        thread.start();
        return thread;
    }

    /**
     * Receive an input from server and elaborate it
     * //  * @param inputObject object from server
     *
     * @return a thread that handle everything
     */
 /*   public Thread asyncReadFromSocket(final ObjectInputStream inputObject){
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
    }/*

    /**
     * Check if the connection exist
     * @return if is active
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * set the status of connection
     *
     * @param active status of connection
     */
    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    /**
     * runnable for the cli application of Eriantys
     */
    public void run() {

        try {

            boolean isValidIpAddress;
            boolean isValidPort;
            String port, ip;
            do {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Insert the IP: ");
                ip = scanner.nextLine();
                System.out.println("Port: ");
                port = scanner.nextLine();
                isValidIpAddress = ClientController.isValidIpAddress(ip);
                isValidPort = ClientController.isValidPort(port);
            } while (!(isValidIpAddress && isValidPort));

            notifyObserver(new ServerInfoMessage(ip, port));

          /*  System.out.println("Connection Established");
            socketIn = new ObjectInputStream(socketClient.getInputStream());
            outputStream = new ObjectOutputStream(socketClient.getOutputStream());
            Thread t0 = asyncReadFromSocket(socketIn);*/
            Thread t1 = readFromInput();
            // Thread t2 = ping();
            // t0.join();
            t1.join();
            //  t2.join();*/

        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        }
    }


    /**
     * Send to server a message
     * @param message message to be sent
     */
   /* protected synchronized void sendToServer(Message message) {
        try{
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }*/

    /**
     * thread that handle the ping function
     * @return a thread
     */
  /*  public Thread ping(){
        Ping ping = new Ping();
        return new Thread(() -> {
            try{
                while(isActive()){
                    long start = System.currentTimeMillis();
                    long end = start + 2 * 1000;
                    if (System.currentTimeMillis() > end) {
                        sendToServer(ping);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }*/

    /**
     * getter of remoteModel
     *
     * @return remoteModel
     */
    public RemoteModel getRemoteModel() {
        return remoteModel;
    }

    //code to be changed in order to be more readable and usable within cli and gui

    /**
     * ask view to log in
     */
    @Override
    public void askLogin() {
        cliHandler.requestLogin();
    }

    /**
     * ask view to play an assistant card
     *
     * @param assistantsCards assistant card to be picked
     */
    @Override
    public void askToPlayAssistantCard(List<AssistantsCards> assistantsCards) {
        turnPhase = TurnPhase.PLAY_ASSISTANT;
        cliHandler.showAssistantsCardOption(assistantsCards);
    }


    /**
     * ask view to move mother nature
     *
     * @param message step of mother nature
     */
    @Override
    public void askMoveMotherNature(String message) {
        turnPhase = TurnPhase.MOVE_MOTHER_NATURE;
        cliHandler.askToMotherNature(message);
    }

    /**
     * ask view to pick a cloud
     *
     * @param clouds cloud to pick
     */
    @Override
    public void askChooseCloud(CloudInGame clouds) {
        turnPhase = TurnPhase.CHOOSE_CLOUD;
        cliHandler.showClouds(clouds);
    }

    /**
     * tell view if the login is correct
     *
     * @param success true if log has success
     */
    @Override
    public void showLogin(boolean success) {
        System.out.println("Login successful");
    }

    /**
     * Show the view a generic message
     *
     * @param genericMessage could be a phrase that help the client
     */
    @Override
    public void showGenericMessage(String genericMessage) {
        cliHandler.showGenericMessage(genericMessage);
    }


    /**
     * Show the view an error message
     *
     * @param error could be a phrase that help the client doing the right choice
     */
    @Override
    public void showError(String error) {
        cliHandler.showErrorMessage(error);
    }


    @Override
    public void showWinMessage(EndMatchMessage message, Boolean isWinner) {
        if (isWinner)
            cliHandler.showGenericMessage("congratulation! You Won!");
        else
            cliHandler.showGenericMessage("I'm Sorry you have lose");
        System.exit(0);

    }

    /**
     * Help to understand how the current match is going
     *
     * @param currentGameMessage send a copy of the match with its information
     */
    @Override
    public void showGameState(CurrentGameMessage currentGameMessage) {
        cliHandler.showCurrentGame(currentGameMessage);
    }

    /**
     * Show the Character Cards for this game
     *
     * @param characterCardInGameMessage shows the deck of this match
     */
    @Override
    public void showCharactersCards(CharacterCardInGameMessage characterCardInGameMessage) {
        cliHandler.showCharacterCardsInGame(characterCardInGameMessage);
    }


    /**
     * ask view to move a student
     */
    @Override
    public void askToMoveStudent() {
        turnPhase = TurnPhase.MOVE_STUDENTS;
        cliHandler.showStudentsOnEntranceOption(remoteModel.getStudentsOnEntranceMap());
    }

    @Override
    public void setRemoteModel(RemoteModel remoteModel) {
        this.remoteModel = remoteModel;
    }

    /**
     * Show the characterCard chosen by the player
     */
    @Override
    public void showChosenCharacterCard() {
        turnPhase = TurnPhase.PLAY_CHARACTER_CARD;
        cliHandler.showInfoChosenCharacterCard();
    }


}
