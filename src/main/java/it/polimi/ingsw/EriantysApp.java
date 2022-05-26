package it.polimi.ingsw;

import it.polimi.ingsw.Client.CLI;
import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Client.Gui.GUI;
import it.polimi.ingsw.Client.Gui.JavaFxGui;
import it.polimi.ingsw.Server.Server;
import javafx.application.Application;

import java.io.IOException;
import java.util.Scanner;

public class EriantysApp {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input) {
            case "server" -> {
                Server server;
                try {
                    server = new Server();
                    server.run();
                } catch (IOException e) {
                    System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
                }
            }
            case "client -cli" -> {
                System.out.println("Insert the IP: ");
                String ip = scanner.nextLine();
                Client client = new CLI("127.0.0.1", 1234);
                client.run();
            }
            case "client -gui" -> {
                System.out.println("TO DO");
                System.out.println("Insert the IP: ");
               // String ip = scanner.nextLine();
                GUI gui = new GUI("127.0.0.1", 1234);
                Application.launch(JavaFxGui.class, args);
            }
        }
    }
}