package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.View.RemoteView;
import it.polimi.ingsw.View.ViewInterface;
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

    private static final int PORT = 1234;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private final Map<String, ClientConnection> waitingPlayersInLobby = new HashMap<>();
    private final Map<Integer, ClientsInMatch> matchInServer = new HashMap<>();
    private int matchCounter = 0;
    private int connections = 0;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public synchronized void deregisterConnection(ClientConnection c) {
        ClientsInMatch clientsInMatch = matchInServer.get(c.getNumOfMatch());
        Collection<ClientConnection> opponents = clientsInMatch.getClientConnectionList();
        opponents.remove(c);
        for (ClientConnection clientConnection : opponents) {
                clientConnection.closeConnection();
                connections--;
        }

        matchInServer.remove(c.getNumOfMatch());
        waitingPlayersInLobby.keySet().removeIf(s -> waitingPlayersInLobby.get(s) == c);

    }

    public void run() {
        System.out.println("Server is running");
        while (true) {
            acceptConnections();
        }
    }

    public synchronized void lobby(ClientConnection c) {
        SocketClientConnection newClientConnection = (SocketClientConnection) c;
        List<String> keys = new ArrayList<>(waitingPlayersInLobby.keySet());
        Map<String, ClientConnection> waitingList = new HashMap<>();
        findCompatiblePlayers(newClientConnection, keys, waitingList);
        createIfPossibleAMatch(newClientConnection, waitingList);
        }


  /*  public void EndGameDisconnected(){
        for (Integer i=0; i<waitingPlayersInLobby.size();i++) {
            waitingPlayersInLobby.get(i).asyncSendMessage(new EndOfGameMessage());
            waitingPlayersInLobby.get(i).closeConnection();
        }
    }*/

    private void createIfPossibleAMatch(SocketClientConnection newClientConnection, Map<String, ClientConnection> waitingList) {
        if (waitingList.size() == newClientConnection.getNumberOfPlayers()) {
            BasicMatch match = instantiateModel(newClientConnection);
            Controller controller = instantiateController(match, waitingList) ;
            ClientsInMatch clientsInMatch = new ClientsInMatch(matchCounter, waitingList.values());
            matchInServer.put(matchCounter, clientsInMatch);
            matchCounter++;
            try {
                controller.initGame();
            }catch (ExceptionGame e){
                e.printStackTrace();
            }

        }else{ // put all the players in waiting list in lobby
            waitingPlayersInLobby.putAll(waitingList);
            waitingList.clear();

            newClientConnection.asyncSendMessage(new GenericMessage("Waiting for another player"));
        }
    }

    private void findCompatiblePlayers(SocketClientConnection clientConnection, List<String> keys, Map<String, ClientConnection> waitingList) {
        for (String key : keys) { //keys belong to size of players in lobby
            SocketClientConnection connection = (SocketClientConnection) waitingPlayersInLobby.get(key); //tacke connection of whoever is in the lobby
            if (clientConnection.getNumberOfPlayers() == connection.getNumberOfPlayers() && clientConnection.isExpert() == connection.isExpert()) {
                waitingList.put(key, connection); //put whoever is matchable in the waiting list
                waitingPlayersInLobby.remove(key); //and remove it from lobby
            }
        }
        waitingList.put(clientConnection.getUsername(), clientConnection);
    }

    private Controller instantiateController(BasicMatch match, Map<String, ClientConnection> waitingList) {
        Controller controller = null;
        try {
            controller = new Controller(match, waitingList.keySet());
        } catch (ExceptionGame | CloneNotSupportedException e) {
            e.printStackTrace();
        }
        for (String clientName: waitingList.keySet()) {
            ViewInterface clientView = new RemoteView((SocketClientConnection) waitingList.get(clientName));
            match.addObserver(clientView);
            clientView.addObserver(controller);
            assert controller != null;
            controller.addView(clientName, clientView);
            ((SocketClientConnection) waitingList.get(clientName)).setController(controller);

        }
        return controller;
    }

    private BasicMatch instantiateModel(SocketClientConnection clientConnection) {
        BasicMatch match;
        FactoryMatch factoryMatch = new FactoryMatch();
        match = factoryMatch.newMatch(clientConnection.getNumberOfPlayers());
        if (clientConnection.isExpert()) {
            return new ExpertMatch(match);
        }
        return match;
    }

    public synchronized boolean isNameNotOk(String name) {
        List<String> keys = new ArrayList<>(waitingPlayersInLobby.keySet());
        boolean nameNotOk;
        if(name == null)
            nameNotOk = true;
        else nameNotOk = keys.contains(name);
        return nameNotOk;
    }

    private void acceptConnections() {
        try {
            Socket newSocket = serverSocket.accept();
            connections++;
            System.out.println("Ready for the new connection; current connections = " + connections);
            SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
            executor.submit(socketConnection); //starts the socketClientConnection
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}


