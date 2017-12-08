package server;

import common.ConnectionAgent;
import common.MessageListener;
import common.MessageSource;
import server.game.Game;
import server.game.User;

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
    private ArrayList<User> userQueue;

    public BattleServer(int port) {
        this.game = new Game();
        current = 0;
        this.port = port;
        serverSocket = null;
        clientSocket =  null;
        userQueue = new ArrayList<>();
    }

    public void listen() throws IOException{
        serverSocket = new ServerSocket(port);

        boolean serverDone = false;
        //TODO make this not a forever loop
        while (!serverDone) {
            clientSocket = serverSocket.accept();

            Scanner inFromClient =
                    new Scanner(new InputStreamReader(clientSocket
                            .getInputStream()));

            PrintStream outToClient =
                    new PrintStream(clientSocket.getOutputStream());

            outToClient.print("Successfully connected to Battleship!");

            User currentAgent = new User(clientSocket,
                    inFromClient, outToClient, this);

            currentAgent.setThread(new Thread(currentAgent));

            userQueue.add(currentAgent);

            currentAgent.go();
        }

        serverSocket.close();
    }

    public void broadcast(String message){
        for(User c : game.getCurrentPlayers()){
            System.out.println("sending to all user");
            c.sendMessage(message);
        }
    }

    public void messageReceived(String message, MessageSource source){
        User currentUser = (User)source;

        System.out.println(message);

        Scanner messageScanner = new Scanner(message);

        switch (messageScanner.next()){
            case "/join":
                currentUser.setUsername(messageScanner.next());
                break;
            case "/play":
                game = new Game();
                game.addUsers(userQueue);
                broadcast("GAME STARTING!");
                break;
            case "/attack":
                break;
            case "/quit":
                break;
            case "/show":
                currentUser.sendMessage(currentUser.getGrid().getGrid());
                break;
        }
    }

    public void sourceClosed(MessageSource source){

    }
    
}
