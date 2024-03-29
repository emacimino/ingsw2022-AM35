package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.Gui.GUI;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.NetworkUtilities.*;
import it.polimi.ingsw.Observer.Observer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class that handles the client controller
 */
public class ClientController implements Observer{
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
            case SERVER_INFO -> {
                ServerInfoMessage infoMessage = (ServerInfoMessage) message;
                updateOnServerInfo(infoMessage.getIp(), infoMessage.getPort());
            }
            case GAME_INFO -> showGame(message);
            case CHARACTER_CARD_IN_GAME -> showCharacterInGame(message);
            case END_MATCH -> updateOnEndMatch(message);
            case SHOW_CHARACTER_CARD_INFO -> showCharacterCardInfo(message);
            case ASK_CHARACTER_CARD -> askInfoCharacter(message);
            case DISCONNECT -> onDisconnection();
            case PLAY_CHARACTER_CARD -> playCharacterCard(message);

            case ERROR -> view.showError(((ErrorMessage) message).getError());
            case GENERIC_MESSAGE -> view.showGenericMessage((String) ((GenericMessage) message).getContent());
            case YOUR_TURN -> view.showGenericMessage(((YourTurnMessage) message).getContent());
            case REQUEST_LOGIN -> view.askLogin();
            case END_OF_TURN -> view.showGenericMessage(((EndTurnMessage) message).getContent());
            case ASK_ASSISTANT_CARD -> showAssistantCardOptions(message);
            case ASK_TO_MOVE_STUDENT -> view.askToMoveStudent();
            case ASK_MOVE_MOTHER_NATURE -> view.askMoveMotherNature(message.getMessage());
            case CLOUD_IN_GAME -> view.askChooseCloud((CloudInGame) message);

            case ASSISTANT_CARD, NEW_MATCH, MOVE_STUDENT, MOVE_MOTHER_NATURE, CLOUD_CHOICE -> client.sendMessage(message);

            case STUDENTS_ON_ENTRANCE -> remoteModel.setStudentOnEntranceMap(((StudentsOnEntranceMessage) message).getStudents());
            case ARCHIPELAGOS_IN_GAME -> remoteModel.setArchipelagosMap(((ArchipelagoInGameMessage) message).getArchipelago());
            case BOARD -> remoteModel.setCurrentBoard(((BoardMessage) message).getBoard());
            case TEAM_MESSAGE -> remoteModel.setTeams((TeamMessage) message);
        }
    }

    /**
     * Method used to play a character card
     * @param message a message
     */
    private void playCharacterCard(Message message) {
        remoteModel.setEnablePlayCharacter(false);
        client.sendMessage(message);
    }

    /**
     * Method used to show the current game
     * @param message a message containing the current game
     */
    private void showGame(Message message) {
        remoteModel.setGame(((CurrentGameMessage) message).getGame());
        view.showGameState((CurrentGameMessage) message);
    }

    /**
     * Method that shows a window containing the assistant cards
     * @param message a message containing the assistant cards
     */
    private void showAssistantCardOptions(Message message) {
        remoteModel.setEnablePlayCharacter(true);
        remoteModel.setAssistantsCardsMap(((AskAssistantCardMessage) message).getAssistantsCards());
        view.askToPlayAssistantCard(((AskAssistantCardMessage) message).getAssistantsCards());
    }

    /**
     * Method used to open a window containing the character cards in the game
     * @param message a message containing the character cards in the game
     */
    private void showCharacterInGame(Message message) {
        remoteModel.setCharacterCardMap(((CharacterCardInGameMessage) message).getCharacterCard());
        view.showCharactersCards((CharacterCardInGameMessage) message);
    }

    private void showCharacterCardInfo(Message message) {
        CharacterCardInfo card = (CharacterCardInfo) message;
        remoteModel.setStudentsOnCardMap(card.getStudentsOnCardMap());
        remoteModel.setStudentOnEntranceMap(card.getStudentsOnEntranceMap());
        remoteModel.setArchipelagosMap(card.getArchipelagoMap());
        remoteModel.setActiveCharacterCard(card.getCharacterCardName());
        view.showChosenCharacterCard();
    }

    /**
     * Message used to send the end of game message
     * @param message a message signaling the end of the game
     */
    private void updateOnEndMatch(Message message) {
        if(message == null){
            view.showDisconnection();
        }else {
            EndMatchMessage matchMessage = (EndMatchMessage) message;
            boolean isWinner = matchMessage.getWinners().stream().map(Player::getUsername).anyMatch(s -> s.equals(username));
            view.showWinMessage(matchMessage, isWinner);
        }
    }

    /**
     * Receive a message and send it to the client
     * @param message infoCharacterMessage
     */
    private void askInfoCharacter(Message message) {
        if(remoteModel.isEnablePlayCharacter()){
            client.sendMessage(message);
        }else
            view.showError("You have already played a Character Card in this turn");

    }


    /**
     * Send to the server the destination requested
     * @param ip requested from the client
     * @param port requested from the client
     */
    public void updateOnServerInfo(String ip, String port) {
        try {
            client = new SocketClientSide(ip, Integer.parseInt(port));
            client.addObserver(this); //throughout Client, like in SocketClientSide, add clientController as Observer -> Client controller will be updated from the SocketClientSide notification
            client.readMessage(); //start an async Read from server
        } catch (IOException e) {
            tasks.execute(() -> view.showLogin(false));
        }
    }

    /**
     * receive the login parameters checked from Server
     * @param message is a LoginResponse message
     */
    public void updateOnLogin(LoginResponse message) {
        username = message.getName();
        client.sendMessage(message);
    }


    /**
     * Handle client disconnection
     */
    public void onDisconnection() {
        updateOnEndMatch(null);
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
