package server;

import common.ConnectionAgent;
import common.MessageListener;
import common.MessageSource;
import server.game.Game;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class BattleServer implements MessageListener{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int current;
    private Game game;
    private int port;
    private ArrayList<ConnectionAgent> connectionAgents;

    public BattleServer(int port) {
        this.game = new Game();
        current = 0;
        this.port = port;
        connectionAgents = new ArrayList<>();
        serverSocket = null;
        clientSocket =  null;
    }

    public void listen() throws IOException{
        serverSocket = new ServerSocket(port);

        boolean serverDone = false;
        //TODO make this not a forever loop
        while (!serverDone) {
            System.out.println("connected1");
            clientSocket = serverSocket.accept();
            System.out.println("connected2");

            Scanner inFromClient =
                    new Scanner(new InputStreamReader(clientSocket
                            .getInputStream()));

            PrintStream outToClient =
                    new PrintStream(clientSocket.getOutputStream());

            ConnectionAgent newConnection = new ConnectionAgent(clientSocket,
                    inFromClient, outToClient);

            newConnection.setThread(new Thread(newConnection));
            connectionAgents.add(newConnection);

            System.out.println("connected3");

            newConnection.go();
            System.out.println("connected4");
        }

        System.out.println("out of loop");
        serverSocket.close();
    }

    public void broadcast(String message){
        for(ConnectionAgent c : connectionAgents){
            c.sendMessage(message);
        }
    }

    public void messageReceived(String message, MessageSource source){

    }

    public void sourceClosed(MessageSource source){

    }
}
