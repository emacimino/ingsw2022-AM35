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

/**
 * Class that implements the UserView interface for CLI purposes
 */
public class CLI  extends Observable implements UserView {
    private final CLIHandler cliHandler = new CLIHandler(this);
    protected Scanner scanner;
    private RemoteModel remoteModel;
    private boolean active = true;
    protected TurnPhase turnPhase = TurnPhase.LOGIN;


    /**
     * Scan the user input and elaborate the result
     * @return a new thread that handle the reading functions
     */
    public Thread readFromInput() {
        Thread thread = new Thread(() -> {
            try {
                scanner = new Scanner(System.in);
                while (isActive()) {
                    String inputLine = scanner.nextLine();
                    Message message = cliHandler.convertInputToMessage(inputLine, turnPhase);
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

            Thread t1 = readFromInput();
            t1.join();

        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        }
    }




    /**
     * getter of remoteModel
     *
     * @return remoteModel
     */
    public RemoteModel getRemoteModel() {
        return remoteModel;
    }


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
     * @param clouds cloud to pick
     */
    @Override
    public void askChooseCloud(CloudInGame clouds) {
        turnPhase = TurnPhase.CHOOSE_CLOUD;
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
        System.out.println("write any letter to esc");
        String input = new Scanner(System.in).nextLine();
        System.exit(0);


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

    @Override
    public void showDisconnection() {
        cliHandler.showGenericMessage("Disconnection of the server or a player");
        System.exit(0);
    }



}
