package it.polimi.ingsw.Client.CLIENT2;

import it.polimi.ingsw.NetworkUtilities.DisconnectMessage;
import it.polimi.ingsw.NetworkUtilities.ErrorMessage;
import it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.NetworkUtilities.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//CLASSE VERA CHE SI OCCUPA DEL READ AND SEND MESSAGE, NB SOLTANTO IL READ DA SOCKET HA BISOGNO DI UN THREAD, E A PARTE IL PING PONG
public class SocketClientSide extends Client {
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final ExecutorService executorService;
    private static final int SOCKET_TIMEOUT = 10000;
    private final ScheduledExecutorService pingPong;

    public SocketClientSide(String ipAddress, int port) throws IOException {
        this.socket = new Socket(ipAddress, port);
     //   this.socket.connect(new InetSocketAddress(ipAddress, port), SOCKET_TIMEOUT);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.executorService = Executors.newSingleThreadExecutor();
        this.pingPong = Executors.newSingleThreadScheduledExecutor();

    }

    @Override
    public synchronized void sendMessage(Message message) {
        try{
            System.out.println("sending in socketClientside :" + message);
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        }catch (IOException e){
            disconnect();
            notifyObserver(new ErrorMessage("Can't send the message, retry"));
        }

    }

    @Override
    public void readMessage() {
        executorService.execute(()->{
                while(!executorService.isShutdown()) {
                    Message message;
                    try{
                        message = (Message) inputStream.readObject();
                    }catch (IOException | ClassNotFoundException e ){
                        message = new ErrorMessage("Connection lost");
                        disconnect();
                        executorService.shutdownNow();
                    }
                    notifyObserver(message);
                }
        });

    }

    @Override
    public void disconnect() {
        try{
            new Thread(()-> sendMessage(new DisconnectMessage())).start();
            if(!socket.isClosed()){
                System.out.println("socket not closed yer");
                executorService.shutdownNow();
                socket.close();
            }
        }catch (IOException e){
            notifyObserver(new ErrorMessage("Can't disconnect, retry"));
            e.printStackTrace();
        }
    }

    public void enablePingPong(boolean enabled){
        if(enabled){
            pingPong.scheduleAtFixedRate(()-> sendMessage(new Ping()), 0, 1000, TimeUnit.MILLISECONDS);
        }else{
            pingPong.shutdownNow();
        }
    }
}
