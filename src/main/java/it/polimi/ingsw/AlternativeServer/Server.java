package it.polimi.ingsw.AlternativeServer;

import it.polimi.ingsw.AlternativeController.Controller;
import it.polimi.ingsw.AlternativeView.RemoteView;
import it.polimi.ingsw.AlternativeView.ViewInterface;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;

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
    private int matchCounter = 0;


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public synchronized void deregisterConnection(ClientConnection c) {
        List<ClientConnection> opponents = (List<ClientConnection>) matchInServer.get(c.getNumOfMatch());
        if (opponents != null) {
            for (ClientConnection clientConnection : opponents) {
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

    public synchronized void lobby(ClientConnection c) throws ExceptionGame {
        String name = null;
        SocketClientConnection clientConnection = (SocketClientConnection) c;
        List<String> keys = new ArrayList<>(waitingPlayersInLobby.keySet());
        Map<String, ClientConnection> waitingList = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            ClientConnection connection = waitingPlayersInLobby.get(keys.get(i));
            connection.asyncSend("Connected User: " + keys.get(i));
        }

        try {
            boolean nameNotOk = false;
            do {
                Scanner in = new Scanner(((SocketClientConnection) c).getSocket().getInputStream());
                ((SocketClientConnection) c).sendMessage("Welcome!\nWhat is your name?");
                name = in.nextLine();
                if(name == null)
                    nameNotOk = true;
                for (String username : keys) {
                    if (name.equals(username)) {
                            nameNotOk = true;
                    }
                }

            } while (nameNotOk);
            waitingList.put(name,clientConnection);

        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error! " + e.getMessage());
        }
        for (int i = 0; i < keys.size(); i++) {
            SocketClientConnection connection = (SocketClientConnection) waitingPlayersInLobby.get(keys.get(i));
            if (clientConnection.getNumberOfPlayers() == connection.getNumberOfPlayers() && clientConnection.isExpert() && connection.isExpert()) {
                waitingList.put(keys.get(i),connection);
            }
            if (waitingList.size() == clientConnection.getNumberOfPlayers()) {
                BasicMatch match;
                FactoryMatch factoryMatch = new FactoryMatch();
                match = factoryMatch.newMatch(clientConnection.getNumberOfPlayers());
                if (clientConnection.isExpert()) {
                    match = new ExpertMatch(match);
                }
                Controller controller = new Controller(match, waitingList.keySet());
                for (String clientName: waitingList.keySet()) {
                    ViewInterface clientView = new RemoteView((SocketClientConnection) waitingList.get(clientName));
                    match.addObserver(clientView);
                    clientView.addObserver(controller);
                    controller.addView(clientName, clientView);
                }
                ClientsInMatch clientsInMatch = new ClientsInMatch(waitingList.values());
                matchInServer.put(matchCounter, clientsInMatch);
                matchCounter++;

            }else{
                waitingPlayersInLobby.put(name,c);
                c.asyncSend("Waiting for another player");
            }
        }
    }
}


