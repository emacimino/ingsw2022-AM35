package it.polimi.ingsw;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.Cli.CLI;
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
                CLI cli = new CLI();
                ClientController clientController = new ClientController(cli);
                cli.addObserver(clientController);
                cli.run();
            }
            case "-gui" -> Application.launch(JavaFxGui.class);
        }
    }
}
