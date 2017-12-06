package server;

import common.MessageListener;
import common.MessageSource;
import server.game.Game;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BattleServer implements MessageListener{
    private ServerSocket serverSocket;
    private int current;
    private Game game;
    private int port;

    public BattleServer(int port) {
        this.game = new Game();
        current = 0;
        this.port = port;
    }

    public void listen() throws IOException{
        serverSocket = new ServerSocket(port);

        Socket connectionSocket = serverSocket.accept();

        Scanner inFromClient =
                new Scanner(new InputStreamReader(connectionSocket.getInputStream()));

        game.addUser(inFromClient.next());

        serverSocket.close();
    }

    public void broadcast(String message){

    }

    public void messageReceived(String message, MessageSource source){

    }

    public void sourceClosed(MessageSource source){

    }
}
