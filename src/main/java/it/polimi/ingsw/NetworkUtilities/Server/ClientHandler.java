package it.polimi.ingsw.NetworkUtilities.Server;

//import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observable;
//import it.polimi.ingsw.View.ActualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler extends Observable implements ClientHandlerInterface, Runnable{
    private final Socket socket;
    private final SocketServer socketServer;

    private boolean connected;

    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final Object inputLock = new Object();
    private final Object outputLock = new Object();


    public ClientHandler(SocketServer socketServer, Socket socket) throws IOException {
        this.socket = socket;
        this.socketServer = socketServer;

        connected = true;

        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());

    }
    @Override
    public void run() {
        try {
            addObserver();
            handleClientConnection();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void handleClientConnection() throws IOException, ClassNotFoundException {
        while ((!Thread.currentThread().isInterrupted())){
            synchronized (inputLock){
                Message message = readMessage();
                switch (message.getType()){
                    case LOGIN_REQUEST -> sendMessage(message);
                    case ASK_NUM_OF_PLAYERS -> iu;
                    case CREATE_A_MATCH -> ok;
                    case PARTICIPATE_TO_A_MATCH -> ji;
                    case ASSISTANT_CARD -> okp;
                    case STUDENT_ON_BOARD -> fe;
                    case STUDENT_IN_ARCHIPELAGO -> mk;
                    case MOVE_MOTHER_NATURE -> okk;
                    }
                }
            }
        }

    public void addClient(String username, ClientHandler clientHandler){
        server.addAClient(username, clientHandler);

    }

    @Override
    public boolean isConnected() {
        return connected;
        }

    @Override
    public void disconnect() throws IOException {
        if(connected) {
            socket.close();
            connected = false;
        }
    }

    @Override
    public void sendMessage(Message message) {
        try{
            outputStream.writeObject(message);
            outputStream.reset();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Message readMessage() throws IOException, ClassNotFoundException {
        return (Message)inputStream.readObject();
    }

}
