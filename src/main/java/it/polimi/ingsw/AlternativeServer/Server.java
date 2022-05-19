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

    private static final int PORT = 12345;
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
        waitingPlayersInLobby.keySet().removeIf(s -> waitingPlayersInLobby.get(s) == c);
    }

    public void run() {
        System.out.println("Server is running");
        while (true) {
            acceptConnections();
        }
    }



    public synchronized void lobby(ClientConnection c) throws ExceptionGame, CloneNotSupportedException {
        String name = null;

        SocketClientConnection clientConnection = (SocketClientConnection) c;
        List<String> keys = new ArrayList<>(waitingPlayersInLobby.keySet());
        Map<String, ClientConnection> waitingList = new HashMap<>();
        for (String key : keys) {
            ClientConnection connection = waitingPlayersInLobby.get(key); //is never used
            clientConnection.asyncSend("Connected User: " + key);
        }

        pickAName(waitingList,clientConnection,keys);

        findCompatiblePlayers(clientConnection,keys,waitingList);


        if (waitingList.size() == clientConnection.getNumberOfPlayers()) {
            BasicMatch match = instantiateModel(clientConnection);
            Controller controller = instantiateController(match, waitingList) ;

                ClientsInMatch clientsInMatch = new ClientsInMatch(waitingList.values());
                matchInServer.put(matchCounter, clientsInMatch);
                matchCounter++;
                controller.initGame();

            }else{ // put all the plahyers in waiting list in lobby
                waitingPlayersInLobby.putAll(waitingList);
                waitingList.clear();
                System.out.println(waitingPlayersInLobby );
                c.asyncSend("Waiting for another player");
            }
        }

    private Controller instantiateController(BasicMatch match, Map<String, ClientConnection> waitingList) {
        Controller controller = null;
        try {
            controller = new Controller(match, waitingList.keySet());
        } catch (ExceptionGame | CloneNotSupportedException e) {
            e.printStackTrace();
        }
        //     Map<String, ViewInterface> viewMap = new HashMap<>();

        for (String clientName: waitingList.keySet()) {
            ViewInterface clientView = new RemoteView((SocketClientConnection) waitingList.get(clientName));
            match.addObserver(clientView);
            clientView.addObserver(controller);
            controller.addView(clientName, clientView);
        }
        return controller;
    }

    private BasicMatch instantiateModel(SocketClientConnection clientConnection) {
        BasicMatch match;
        FactoryMatch factoryMatch = new FactoryMatch();
        match = factoryMatch.newMatch(clientConnection.getNumberOfPlayers());
        if (clientConnection.isExpert()) {
            match = new ExpertMatch(match);
        }
        return match;
    }


    private void pickAName(Map<String, ClientConnection> waitingList, SocketClientConnection clientConnection, List<String> keys) {
        String name;
        try {
            boolean nameNotOk;
            do {
                Scanner in = new Scanner(clientConnection.getSocket().getInputStream());
                clientConnection.sendMessage("Welcome!\nWhat is your name?");
                name = in.next();
            } while (checkName(name,keys));

            waitingList.put(name,clientConnection);

        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error! " + e.getMessage());
        }
    }

    private void findCompatiblePlayers(SocketClientConnection clientConnection, List<String> keys, Map<String, ClientConnection> waitingList) {
        for (String key : keys) { //keys belongs to size of players in lobby
            SocketClientConnection connection = (SocketClientConnection) waitingPlayersInLobby.get(key); //tacke connection of whoever is in the lobby
            if (clientConnection.getNumberOfPlayers() == connection.getNumberOfPlayers() && clientConnection.isExpert() == connection.isExpert()) {
                waitingList.put(key, connection); //put whoever is matchable in the waiting list
                waitingPlayersInLobby.remove(key); //and remove it from lobby
            }
        }
    }

    private boolean checkName(String name, List<String> keys) {
        boolean nameNotOk;
        if(name == null)
            nameNotOk = true;
        else nameNotOk = keys.contains(name);
        return nameNotOk;
    }

    private void acceptConnections() {
        try {
            int connections = 0;
            Socket newSocket = serverSocket.accept();
            connections++;
            System.out.println("Ready for the new connection; current connections = " + connections );
            //clientHandlerToBeImplemented
            SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
            executor.submit(socketConnection);
        } catch (IOException e) {
            System.out.println("Connection Error!");
        }
    }

}


