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

public class CLI  extends Observable implements UserView {
    private final CLIHandler cliHandler = new CLIHandler(this);
    protected Scanner scanner;
    private RemoteModel remoteModel;
    private final String ip;
    private final int port;
    protected ObjectOutputStream outputStream;
    private boolean active = true;
    protected ObjectInputStream socketIn;
    protected TurnPhase turnPhase = TurnPhase.LOGIN;


    public CLI(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Scan the user input and elaborate the result
     * @return a new thread that handle the reading functions
     */
    public Thread asyncWriteToSocket(){
        Thread thread = new Thread(() -> {
            try{
                scanner = new Scanner(System.in);
                while (isActive()){
                    String inputLine = scanner.nextLine(); //Scan input from command line
                    Message message = cliHandler.convertInputToMessage(inputLine, turnPhase); //Create message from input
                    if(message != null) {
                        //notifyObserver(message);
                        sendToServer(message);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                setActive(false);
            }
        });
        thread.start();
        return thread;
    }

    /**
     * Receive an input from server and elaborate it
     * @param inputObject object from server
     * @return a thread that handle everything
     */
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

    /**
     * Check if the connection exist
     * @return if is active
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * set the status of connection
     * @param active status of connection
     */
    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    /**
     * runnable for the cli application of Eryantis
     * @throws IOException if the connection cease to exist
     */
    public void run() throws IOException {
        Socket socketClient = new Socket(ip,port);

        try (socketClient) {
            ClientController clientController = new ClientController(this);
            this.addObserver(clientController);
            System.out.println("Connection Established");
            socketIn = new ObjectInputStream(socketClient.getInputStream());
            outputStream = new ObjectOutputStream(socketClient.getOutputStream());
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket();
            Thread t2 = ping();
            t0.join();
            t1.join();
            t2.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            socketIn.close();
            outputStream.close();
        }
    }

    /**
     * set the turnPhase by the type of message that arrives
     * @param message message that arrives from server
     */
    private void setNextAction(Message message) {
        switch (message.getType()){
            case ASK_ASSISTANT_CARD -> this.turnPhase = TurnPhase.PLAY_ASSISTANT;
            case STUDENTS_ON_ENTRANCE ->  this.turnPhase = TurnPhase.MOVE_STUDENTS;
            case ASK_MOVE_MOTHER_NATURE -> this.turnPhase = TurnPhase.MOVE_MOTHER_NATURE;
            case CLOUD_IN_GAME -> this.turnPhase = TurnPhase.CHOOSE_CLOUD;
            case SHOW_CHARACTER_CARD_INFO -> this.turnPhase = TurnPhase.PLAY_CHARACTER_CARD;
            case END_OF_TURN -> this.turnPhase = TurnPhase.END_TURN;

            default -> {
                //do nothing
            }

        }
    }

    /**
     * Send to server a message
     * @param message message to be sent
     */
    protected synchronized void sendToServer(Message message) {
        try{
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * thread that handle the ping function
     * @return a thread
     */
    public Thread ping(){
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
    }

    /**
     * getter of remoteModel
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
     * @param assistantsCards assistant card to be picked
     */
    @Override
    public void askToPlayAssistantCard(List<AssistantsCards> assistantsCards) {
        cliHandler.showAssistantsCardOption(assistantsCards);
    }


    /**
     * ask view to move mother nature
     * @param message step of mother nature
     */
    @Override
    public void askMoveMotherNature(String message) {
        cliHandler.askToMotherNature(message);
    }
    /**
     * ask view to pick a cloud
     * @param clouds cloud to pick
     */
    @Override
    public void askChooseCloud(CloudInGame clouds) {
        cliHandler.showClouds(clouds);
    }
    /**
     * tell view if the login is correct
     * @param success true if log has success
     */
    @Override
    public void showLogin(boolean success) {
        System.out.println("Login successful");
    }
    /**
     * Show the view a generic message
     * @param genericMessage could be a phrase that help the client
     */
    @Override
    public void showGenericMessage(String genericMessage) {
        cliHandler.showGenericMessage(genericMessage);
    }

    @Override
    public void showDisconnectionMessage(String usernameDisconnected, String text) {
        //TO DO
    }
    /**
     * Show the view an error message
     * @param error could be a phrase that help the client doing the right choice
     */
    @Override
    public void showError(String error) {
        cliHandler.showErrorMessage(error);
    }

    /**
     * load the board for the view
     * @param board id the board selected
     */
    @Override
    public void loadBoard(Board board) {
        cliHandler.showBoard(board);
    }
    /**
     * Communicate if someone won the game
     * @param message endOfMatch
     * @param isWinner tell if the match has been won
     */
    @Override
    public void showWinMessage(EndMatchMessage message, Boolean isWinner) {

    }
    /**
     * Help to understand how the current match is going
     * @param currentGameMessage send a copy of the match with its information
     */
    @Override
    public void showGameState(CurrentGameMessage currentGameMessage) {
        cliHandler.showCurrentGame(currentGameMessage);
    }
    /**
     * Show the Character Cards for this game
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

    }
    /**
     * load the archipelago situation for the client
     * @param archipelago current archipelago list whit index
     */
    @Override
    public void loadArchipelagosOption(Map<Integer, Archipelago> archipelago) {

    }
    /**
     * load the student entrance for the client
     * @param students current students entrance list whit index
     */
    @Override
    public void loadStudentOnEntrance(Map<Integer, Student> students) {
        cliHandler.showStudentsOnEntranceOption(students);
    }
    /**
     * update the remote model
     * @param remoteModel remote model updated
     */
    @Override
    public void setRemoteModel(RemoteModel remoteModel) {
        this.remoteModel = remoteModel;
    }
    /**
     * Show the characterCard chosen by the player
     */
    @Override
    public void showChosenCharacterCard() {

    }


}
