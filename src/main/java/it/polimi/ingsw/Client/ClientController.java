package it.polimi.ingsw.Client;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.NetworkUtilities.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewObserver;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController implements Observer, ViewObserver {
    private final UserView view; //view è cli o gui
    private Client client;  //rappresenta il socket lato client
    private String username;
    private final ExecutorService tasks;

    private final RemoteModel remoteModel; //gestire in update tutti i salvataggi in remoteModel


    public ClientController(UserView view) { //User view sara o cli o gui,
        this.view = view;
        remoteModel = new RemoteModel();
        view.setRemoteModel(remoteModel);
        tasks = Executors.newSingleThreadExecutor();
    }

    public RemoteModel getRemoteModel() {
        return remoteModel;
    }

    @Override
    public void update(Message message) {
        System.out.println("in client controller: "+message);
        switch (message.getType()) {
            case OK_LOGIN -> tasks.execute(() -> view.showLogin(true));
            case LOGIN_RESPONSE -> updateOnLogin((LoginResponse) message);
            case PING -> {}
            case SERVER_INFO -> {
                ServerInfoMessage infoMessage = (ServerInfoMessage) message;
                updateOnServerInfo(infoMessage.getIp(), infoMessage.getPort());
            }
            case ERROR -> view.showError(((ErrorMessage) message).getError());
            case GENERIC_MESSAGE -> view.showGenericMessage((String) ((GenericMessage) message).getContent());
            case GAME_INFO -> {view.showGameState((CurrentGameMessage) message);
                remoteModel.setGame(((CurrentGameMessage)message).getGame());
            }
            case YOUR_TURN -> view.showGenericMessage(((YourTurnMessage) message).getContent());
            case REQUEST_LOGIN -> view.askLogin();
            case END_OF_TURN -> view.showGenericMessage(((EndTurnMessage) message).getContent());
            case ASSISTANT_CARD -> updateOnSelectedAssistantCard((AssistantCardMessage) message);
            case ASK_ASSISTANT_CARD -> {
                remoteModel.setAssistantsCardsMap(((AskAssistantCardMessage) message).getAssistantsCards());
                view.askToPlayAssistantCard(((AskAssistantCardMessage) message).getAssistantsCards());
            }
            case ASK_TO_MOVE_STUDENT -> view.askToMoveStudent();
            case ASK_MOVE_MOTHER_NATURE -> view.askMoveMotherNature(((AskToMoveMotherNatureMessage)message).getMessage());
            case CLOUD_IN_GAME -> view.askChooseCloud((CloudInGame) message);
            case MOVE_STUDENT -> updateOnMoveStudent((MoveStudentMessage) message);
            case STUDENTS_ON_ENTRANCE -> {
                remoteModel.setStudentOnEntranceMap(((StudentsOnEntranceMessage)message).getStudents());
                view.loadStudentOnEntrance(((StudentsOnEntranceMessage)message).getStudents());
            }
            case ARCHIPELAGOS_IN_GAME -> {
                remoteModel.setArchipelagosMap(((ArchipelagoInGameMessage)message).getArchipelago());
                view.loadArchipelagosOption(((ArchipelagoInGameMessage) message).getArchipelago());
            }
            case BOARD -> {
                remoteModel.setCurrentBoard(((BoardMessage)message).getBoard());
                view.loadBoard(((BoardMessage)message).getBoard());
            }
            case MOVE_MOTHER_NATURE -> updateOnMoveMotherNature((MoveMotherNatureMessage)message);
            case CLOUD_CHOICE -> updateOnSelectedCloud((CloudMessage)message);
            case CHARACTER_CARD_IN_GAME -> {
                remoteModel.setCharacterCardMap(((CharacterCardInGameMessage) message).getCharacterCard());
                view.showCharactersCards((CharacterCardInGameMessage) message);
            }
            case END_MATCH -> {
                EndMatchMessage matchMessage = (EndMatchMessage) message;
                boolean isWinner = matchMessage.getWinners().stream().map(Player::getUsername).anyMatch(s -> s.equals(username));
                view.showWinMessage(matchMessage, isWinner);
            }
            case NEW_MATCH -> client.sendMessage(message);
            case SHOW_CHARACTER_CARD_INFO -> {
                CharacterCardInfo card = (CharacterCardInfo)message;
                remoteModel.setStudentsOnCardMap(card.getStudentsOnCardMap());
                remoteModel.setStudentOnEntranceMap(card.getStudentsOnEntranceMap());
                remoteModel.setArchipelagosMap(card.getArchipelagoMap());
                remoteModel.setActiveCharacterCard(card.getCharacterCardName());
                view.showChosenCharacterCard();

            }
            case ASK_CHARACTER_CARD -> {askInfoCharacter(message);}
            case PLAY_CHARACTER_CARD -> {updateOnPlayCharacter(message);}
            case DISCONNECT -> onDisconnection();
        }
    }

    private void askInfoCharacter(Message message) {
        client.sendMessage(message);
    }

    private void updateOnPlayCharacter(Message message) {
        client.sendMessage(message);
    }


    @Override
    public void updateOnServerInfo(String ip, String port) {
        try {
            client = new SocketClientSide(ip, Integer.parseInt(port));
            client.addObserver(this); //alla SocketClientSide, attraverso la facciata di Client aggiungo come osservatore il clientController -> Client controller verrà aggiornato alla notifica di cioè che accade a SocketClientSide
            client.readMessage(); //inizia una lettura asincrona dal server
            //client.enablePingPong(true);
            tasks.execute(view::askLogin);
        } catch (IOException e) {
            tasks.execute(() -> view.showLogin(false));
        }
    }

    @Override
    public void updateOnLogin(LoginResponse message) {
        username = message.getName();
        client.sendMessage(message);
    }


    @Override
    public void updateOnSelectedAssistantCard(AssistantCardMessage message) {
        client.sendMessage(message);
    }


    @Override
    public void updateOnMoveStudent(MoveStudentMessage message) {
        client.sendMessage(message);
    }

    @Override
    public void updateOnMoveMotherNature(MoveMotherNatureMessage message) {
        client.sendMessage(message);
    }

    @Override
    public void updateOnSelectedCloud(CloudMessage message) {
        client.sendMessage(message);
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
