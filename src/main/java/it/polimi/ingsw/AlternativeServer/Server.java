package it.polimi.ingsw.AlternativeServer;

import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.ExpertMatch.MatchDecorator;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 62341;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingPlayersInLobby = new HashMap<>();
    private Map<Integer, ClientsInMatch> matchInServer = new HashMap<>();


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public synchronized void deregisterConnection(ClientConnection c) {
        List<ClientConnection> opponents = (List<ClientConnection>) matchInServer.get(c.getNumOfMatch());
        if (opponents != null) {
            for (ClientConnection clientConnection: opponents){
                clientConnection.closeConnection();
            }
        }
        matchInServer.remove(c.getNumOfMatch());
        matchInServer.remove(opponents);
        Iterator<String> iterator = waitingPlayersInLobby.keySet().iterator();
        while (iterator.hasNext()) {
            if (waitingPlayersInLobby.get(iterator.next()) == c) {
                iterator.remove();
            }
        }
    }

    public void run() {
        int connections = 0;
        System.out.println("Server is running");
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                connections++;
                System.out.println("Ready for the new connection - " + connections);
                //clientHandlerToBeImplemented
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

    public synchronized void lobby(ClientConnection c){
        SocketClientConnection clientConnection = (SocketClientConnection)c;
        List<String> keys = new ArrayList<>(waitingPlayersInLobby.keySet());
        List<SocketClientConnection> waitingList = new ArrayList<>();
        waitingList.add(clientConnection);
        for(int i = 0; i < keys.size(); i++){
            ClientConnection connection = waitingPlayersInLobby.get(keys.get(i));
            connection.asyncSend("Connected User: " + keys.get(i));
        }

        try{
            boolean nameNotOk = false;
            do {
                Scanner in = new Scanner(((SocketClientConnection) c).getSocket().getInputStream());
                ((SocketClientConnection) c).sendMessage("Welcome!\nWhat is your name?");
                String name = in.nextLine();
                for (String username : keys) {
                    if (name.equals(username)) {
                        nameNotOk = true;
                    }
                }
            }while(nameNotOk);
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error! " + e.getMessage());

        for(int i = 0; i < keys.size(); i++) {
            SocketClientConnection connection = (SocketClientConnection) waitingPlayersInLobby.get(keys.get(i));
            if (clientConnection.getNumberOfPlayers() == connection.getNumberOfPlayers() && clientConnection.isExpert() && connection.isExpert()) {
                waitingList.add(connection);
            }
            if (waitingList.size() == clientConnection.getNumberOfPlayers()) {
                BasicMatch match;
                FactoryMatch factoryMatch = new FactoryMatch();
                match = factoryMatch.newMatch(clientConnection.getNumberOfPlayers());
                if (clientConnection.isExpert()) {
                    match = new ExpertMatch(match);
                }
            }}}}}
/*

                    ClientConnection c2 = waitingConnection.get(keys.get(1));
                    Player player1 = new Player(keys.get(0), Cell.X);
                    Player player2 = new Player(keys.get(0), Cell.O);
                    View player1View = new RemoteView(player1, keys.get(1), c1);
                    View player2View = new RemoteView(player2, keys.get(0), c2);
                    Model model = new Model();
                    Controller controller = new Controller(model);
                    model.addObserver(player1View);
                    model.addObserver(player2View);
                    player1View.addObserver(controller);
                    player2View.addObserver(controller);
                    playingConnection.put(c1, c2);
                    playingConnection.put(c2, c1);
                    waitingConnection.clear();

                    c1.asyncSend(model.getBoardCopy());
                    c2.asyncSend(model.getBoardCopy());
                    //if(model.getBoardCopy().)
                    if(model.isPlayerTurn(player1)){
                        c1.asyncSend(gameMessage.moveMessage);
                        c2.asyncSend(gameMessage.waitMessage);
                    } else {
                        c2.asyncSend(gameMessage.moveMessage);
                        c1.asyncSend(gameMessage.waitMessage);
                    }
                }
            }
        }
        waitingPlayersInLobby.put(clientConnection.getName(), c);



        if(waitingConnection.size() == 1)
            c.asyncSend("Waiting for another player");

        keys = new ArrayList<>(waitingConnection.keySet());

        if (waitingConnection.size() == 2) {
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            Player player1 = new Player(keys.get(0), Cell.X);
            Player player2 = new Player(keys.get(0), Cell.O);
            View player1View = new RemoteView(player1, keys.get(1), c1);
            View player2View = new RemoteView(player2, keys.get(0), c2);
            Model model = new Model();
            Controller controller = new Controller(model);
            model.addObserver(player1View);
            model.addObserver(player2View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            matchInServer.put(c1, c2);
            matchInServer.put(c2, c1);
            waitingConnection.clear();

            c1.asyncSend(model.getBoardCopy());
            c2.asyncSend(model.getBoardCopy());
            //if(model.getBoardCopy().)
            if(model.isPlayerTurn(player1)){
                c1.asyncSend(gameMessage.moveMessage);
                c2.asyncSend(gameMessage.waitMessage);
            } else {
                c2.asyncSend(gameMessage.moveMessage);
                c1.asyncSend(gameMessage.waitMessage);
            }
        }
    }

*/
