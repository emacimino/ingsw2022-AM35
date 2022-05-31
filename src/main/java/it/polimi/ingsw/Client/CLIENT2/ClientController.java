package it.polimi.ingsw.Client.CLIENT2;


import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewObserver;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController implements Observer, ViewObserver {
    private final UserView view; //view è cli o gui
    private Client2 client;  //rappresenta il socket lato client
    private String username;
    private final ExecutorService tasks;


    public ClientController(UserView view) { //User view sara o cli o gui,
        this.view = view;

        tasks = Executors.newSingleThreadExecutor();
    }

    @Override
    public void update(Message message) {
        System.out.println(message);
        switch (message.getType()) {
            case OK_LOGIN -> tasks.execute(() -> view.showLogin(true));
            case LOGIN_RESPONSE -> updateOnLogin((LoginResponse) message);
            case PING -> {
            }
            case PONG -> {
            }
            case SERVER_INFO -> {
                ServerInfoMessage infoMessage = (ServerInfoMessage) message;
                updateOnServerInfo(infoMessage.getIp(), infoMessage.getPort());
            }
            case ERROR -> view.showError(((ErrorMessage) message).getError());
            case GENERIC_MESSAGE -> view.showGenericMessage((String) ((GenericMessage) message).getContent());
            case GAME_INFO -> view.showGameState((CurrentGameMessage) message);
            case YOUR_TURN -> view.showGenericMessage(((YourTurnMessage) message).getContent());
            case REQUEST_LOGIN -> {
                view.askLogin();
            }
            case END_OF_TURN -> view.showGenericMessage(((EndTurnMessage) message).getContent());
            case ASSISTANT_CARD -> {
                    updateOnSelectedAssistantCard((AssistantCardMessage) message);
            }
            case ASK_ASSISTANT_CARD -> view.askToPlayAssistantCard(((AskAssistantCardMessage) message).getAssistantsCards());
            case ASK_MOVE_MOTHER_NATURE -> {
                view.askMoveMotherNature(((AskToMoveMotherNatureMessage)message).getMessage());
            }
            case CLOUD_IN_GAME -> {
                view.askChooseCloud((CloudInGame) message);
            }
            case MOVE_STUDENT -> {

            }
            case STUDENTS_ON_ENTRANCE -> {
            }
            case ARCHIPELAGOS_IN_GAME -> {

            }
            case BOARD -> {
            }
            case MOVE_MOTHER_NATURE -> {

            }
            case CLOUD_CHOICE -> {
            }
            case CHARACTER_CARD_IN_GAME ->  view.showCharactersCards((CharacterCardInGameMessage)message );

            case END_MATCH -> {
            }
            case SHOW_CHARACTER_CARD -> {
                view.showCharactersCards((CharacterCardInGameMessage) message);
            }
            case PLAY_CHARACTER_CARD -> {
            }
        }
    }


    @Override
    public void updateOnServerInfo(String ip, String port) {
        try {
            client = new SocketClientSide(ip, Integer.parseInt(port));
            client.addObserver(this); //alla SocketClientSide, attraverso la facciata di Client2 aggiungo come osservatore il clientController -> Client controller verrà aggiornato alla notifica di cioe che accade a SocketClientSide
            client.readMessage(); //inizia una lettura asincrona dal server
           // client.enablePingPong(true);
            tasks.execute(view::askLogin);
        } catch (IOException e) {
            tasks.execute(() -> view.showLogin(false));
        }
    }

    @Override
    public void updateOnLogin(LoginResponse message) {
        client.sendMessage(message);
    }


    @Override
    public void updateOnSelectedAssistantCard(AssistantCardMessage message) {
        client.sendMessage(message);
    }


    @Override
    public void updateOnMoveStudent(Student student, Archipelago archipelago) {

    }

    @Override
    public void updateOnMoveMotherNature(Archipelago archipelago) {

    }

    @Override
    public void updateOnSelectedCloud(Cloud cloud) {

    }

    @Override
    public void updateOnSelectedColor(Color color) {

    }

    @Override
    public void onDisconnection() {
        client.disconnect();
    }

    public static boolean isValidIpAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex);
    }

    public static boolean isValidPort(String portStr) {
        try {
            int port = Integer.parseInt(portStr);
            return port >= 1 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
