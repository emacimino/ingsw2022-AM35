package it.polimi.ingsw.ClientServerNetwork.Server;
import java.net.*;
import java.io.*;

public class MultiThreadServer extends Thread {
    private Socket socket = null;

    public MultiThreadServer(Socket socket) {
        super("MultiThreadServer");
        this.socket = socket;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()))
                ) {
            String inputLine, outputLine;
            JsonObjectsHandler obh = new JsonObjectsHandler();
            outputLine = obh.processInput("");
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = obh.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Game Over"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
