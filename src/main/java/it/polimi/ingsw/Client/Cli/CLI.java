package it.polimi.ingsw.Client.Cli;

import it.polimi.ingsw.Client.CLIENT2.UserView;
import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLI   implements UserView {
    private CLIHandler cliHandler = new CLIHandler(this);
    protected Scanner scanner;
    private final RemoteModel remoteModel = new RemoteModel();
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
                    }else if(inputLine.equals("quit")){

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

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public void run() throws IOException {
        Socket socketClient = new Socket(ip,port);
        System.out.println("Connection Established");
        socketIn = new ObjectInputStream(socketClient.getInputStream());
        outputStream = new ObjectOutputStream(socketClient.getOutputStream());

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket();
            Thread t2 = ping();
         //   t0.join();
            t1.join();
            t2.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            socketIn.close();
            outputStream.close();
            socketClient.close();
        }
    }

    private void setNextAction(Message message) {
        switch (message.getType()){
            case ASK_ASSISTANT_CARD -> this.turnPhase = TurnPhase.PLAY_ASSISTANT;
            case STUDENTS_ON_ENTRANCE ->  this.turnPhase = TurnPhase.MOVE_STUDENTS;
            case ASK_MOVE_MOTHER_NATURE -> this.turnPhase = TurnPhase.MOVE_MOTHERNATURE;
            case CLOUD_IN_GAME -> this.turnPhase = TurnPhase.CHOOSE_CLOUD;
            case END_OF_TURN -> this.turnPhase = TurnPhase.END_TURN;

            default -> {
                break;
            }

        }
    }

    protected synchronized void sendToServer(Message message) {
        try{
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Thread ping(){
        Ping ping = new Ping();
        Thread thread = new Thread(() -> {
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
        return thread;
    }

    public RemoteModel getRemoteModel() {
        return remoteModel;
    }






    //codice da rendere coeso successivamente
    @Override
    public void askLogin() {
        cliHandler.requestLogin();
    }

    @Override
    public void askToPlayAssistantCard(List<AssistantsCards> assistantsCards) {
        cliHandler.showAssistantsCardOption(assistantsCards);
    }



    @Override
    public void askMoveMotherNature(String message) {
        cliHandler.askToMotherNature(message);
    }

    @Override
    public void askChooseCloud(CloudInGame clouds) {
        cliHandler.showClouds(clouds);
    }

    @Override
    public void showLogin(boolean success) {
        System.out.println("Login successful");
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        cliHandler.showGenericMessage(genericMessage);
    }

    @Override
    public void showDisconnectionMessage(String usernameDisconnected, String text) {
        //TO DO
    }

    @Override
    public void showError(String error) {
        cliHandler.showErrorMessage(error);
    }

    @Override
    public void loadBoard(Board board) {
        cliHandler.showBoard(board);
    }

    @Override
    public void showWinMessage(EndMatchMessage message) {

    }

    @Override
    public void showGameState(CurrentGameMessage currentGameMessage) {
        cliHandler.showCurrentGame(currentGameMessage);
    }

    @Override
    public void showCharactersCards(CharacterCardInGameMessage characterCardInGameMessage) {
        cliHandler.showCharacterCardsInGame(characterCardInGameMessage);
    }

    @Override
    public void askToMoveStudent() {

    }

    @Override
    public void loadArchipelagosOption(Map<Integer, Archipelago> archipelago) {

    }

    @Override
    public void loadStudentOnEntrance(Map<Integer, Student> students) {
        cliHandler.showStudentsOnEntranceOption(students);
    }
}
