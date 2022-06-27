package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.Gui.GUI;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.NetworkUtilities.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller that handle the communication through the interfaces and the Server
 */
public class ClientController implements Observer, ViewObserver {
    private final UserView view; //either CLI or GUI
    private Client client;  //represent the socket in client
    private String username;
    private final ExecutorService tasks;

    private final RemoteModel remoteModel; //every save in remote in update must be managed in update

    /**
     * Constructor method of client Controller
     * @param view associated to the controller, either CLI or GUI
     */
    public ClientController(UserView view) {
        this.view = view;
        remoteModel = new RemoteModel();
        view.setRemoteModel(remoteModel);
        tasks = Executors.newSingleThreadExecutor();
    }

    /**
     * Getter for remote Model
     * @return the instance of RemoteModel
     */
    public RemoteModel getRemoteModel() {
        return remoteModel;
    }

    /**
     * Receive a message and by his type decide what to do
     * @param message is the message received
     */
    @Override
    public void update(Message message) {
        if(view instanceof GUI)
             System.out.println("in client controller: " + message);
        switch (message.getType()) {
            case OK_LOGIN -> tasks.execute(() -> view.showLogin(true));
            case LOGIN_RESPONSE -> updateOnLogin((LoginResponse) message);
            case PING -> {
            }
            case SERVER_INFO -> {
                ServerInfoMessage infoMessage = (ServerInfoMessage) message;
                updateOnServerInfo(infoMessage.getIp(), infoMessage.getPort());
            }
            case ERROR -> view.showError(((ErrorMessage) message).getError());
            case GENERIC_MESSAGE -> view.showGenericMessage((String) ((GenericMessage) message).getContent());
            case GAME_INFO -> {
                remoteModel.setGame(((CurrentGameMessage) message).getGame());
                view.showGameState((CurrentGameMessage) message);
            }
            case YOUR_TURN -> view.showGenericMessage(((YourTurnMessage) message).getContent());
            case REQUEST_LOGIN -> view.askLogin();
            case END_OF_TURN -> view.showGenericMessage(((EndTurnMessage) message).getContent());
            case ASSISTANT_CARD -> updateOnSelectedAssistantCard((AssistantCardMessage) message);
            case ASK_ASSISTANT_CARD -> {
                remoteModel.setAssistantsCardsMap(((AskAssistantCardMessage) message).getAssistantsCards());
                view.askToPlayAssistantCard(((AskAssistantCardMessage) message).getAssistantsCards());
            }
            case ASK_TO_MOVE_STUDENT -> {
                view.askToMoveStudent();
                //   view.loadArchipelagosOption(remoteModel.getArchipelagosMap());
                //  view.loadBoard(remoteModel.getCurrentBoard());
                //  view.loadStudentOnEntrance(remoteModel.getStudentsOnEntranceMap());
            }
            case ASK_MOVE_MOTHER_NATURE -> {
                //   view.loadArchipelagosOption(remoteModel.getArchipelagosMap());
                //  view.loadBoard(getRemoteModel().getCurrentBoard());
                view.askMoveMotherNature(((AskToMoveMotherNatureMessage) message).getMessage());

            }
            case CLOUD_IN_GAME -> view.askChooseCloud((CloudInGame) message);
            case MOVE_STUDENT -> updateOnMoveStudent((MoveStudentMessage) message);
            case STUDENTS_ON_ENTRANCE -> remoteModel.setStudentOnEntranceMap(((StudentsOnEntranceMessage) message).getStudents());
            case ARCHIPELAGOS_IN_GAME -> remoteModel.setArchipelagosMap(((ArchipelagoInGameMessage) message).getArchipelago());
            case BOARD -> remoteModel.setCurrentBoard(((BoardMessage) message).getBoard());
            case MOVE_MOTHER_NATURE -> updateOnMoveMotherNature((MoveMotherNatureMessage) message);
            case CLOUD_CHOICE -> updateOnSelectedCloud((CloudMessage) message);
            case CHARACTER_CARD_IN_GAME -> {
                remoteModel.setCharacterCardMap(((CharacterCardInGameMessage) message).getCharacterCard());
                view.showCharactersCards((CharacterCardInGameMessage) message);
            }
            case END_MATCH -> updateOnEndMatch(message);
            case NEW_MATCH -> client.sendMessage(message);
            case SHOW_CHARACTER_CARD_INFO -> {
                CharacterCardInfo card = (CharacterCardInfo) message;
                remoteModel.setStudentsOnCardMap(card.getStudentsOnCardMap());
                remoteModel.setStudentOnEntranceMap(card.getStudentsOnEntranceMap());
                remoteModel.setArchipelagosMap(card.getArchipelagoMap());
                remoteModel.setActiveCharacterCard(card.getCharacterCardName());
                view.showChosenCharacterCard();

            }
            case ASK_CHARACTER_CARD -> askInfoCharacter(message);

            case PLAY_CHARACTER_CARD -> updateOnPlayCharacter(message);

            case DISCONNECT -> onDisconnection();
        }
    }

    private void updateOnEndMatch(Message message) {
        EndMatchMessage matchMessage = (EndMatchMessage) message;
        boolean isWinner = matchMessage.getWinners().stream().map(Player::getUsername).anyMatch(s -> s.equals(username));
        view.showWinMessage(matchMessage, isWinner);
    }

    /**
     * Receive a message and send it to the client
     * @param message infoCharacterMessage
     */
    private void askInfoCharacter(Message message) {
        client.sendMessage(message);
    }

    /**
     * Receive a message and send it to the client
     * @param message playCharacterMessage
     */
    private void updateOnPlayCharacter(Message message) {
        client.sendMessage(message);
    }

    /**
     * Send to the server the destination requested
     * @param ip requested from the client
     * @param port requested from the client
     */
    @Override
    public void updateOnServerInfo(String ip, String port) {
        try {
            client = new SocketClientSide(ip, Integer.parseInt(port));
            client.addObserver(this); //throughout Client, like in SocketClientSide, add clientController as Observer -> Client controller will be updated from the SocketClientSide notification
            client.readMessage(); //start an async Read from server
            //client.enablePingPong(true);
        } catch (IOException e) {
            tasks.execute(() -> view.showLogin(false));
        }
    }

    /**
     * receive the login parameters checked from Server
     * @param message is a LoginResponse message
     */
    @Override
    public void updateOnLogin(LoginResponse message) {
        username = message.getName();
        client.sendMessage(message);
    }

    /**
     * receive the login parameters checked from Server
     * @param message is a LoginResponse message
     */
    @Override
    public void updateOnSelectedAssistantCard(AssistantCardMessage message) {
        client.sendMessage(message);
    }

    /**
     * receive the student parameters checked from Server
     * @param message is a MoveStudentMessage message
     */
    @Override
    public void updateOnMoveStudent(MoveStudentMessage message) {
        client.sendMessage(message);
    }
    /**
     * receive the mother Nature parameters checked from Server
     * @param message is a MoveMotherNatureMessage message
     */
    @Override
    public void updateOnMoveMotherNature(MoveMotherNatureMessage message) {
        client.sendMessage(message);
    }

    /**
     * receive the cloud parameters checked from Server
     * @param message is a CloudMessage message
     */
    @Override
    public void updateOnSelectedCloud(CloudMessage message) {
        client.sendMessage(message);
    }

    /**
     * Handle client disconnection
     */
    @Override
    public void onDisconnection() {
        updateOnEndMatch(new EndMatchMessage(new ArrayList<>()));
    }

    /**
     * check the ip inserted from the client
     * @param ip ip string
     * @return if is valid
     */
    public static boolean isValidIpAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex);
    }
    /**
     * check the port inserted from the client
     * @param portStr port string
     * @return if is valid
     */
    public static boolean isValidPort(String portStr) {
        try {
            int port = Integer.parseInt(portStr);
            return port >= 1 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
