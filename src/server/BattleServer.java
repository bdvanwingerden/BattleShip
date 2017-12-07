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
    private ArrayList<ConnectionAgent> connectionAgents;

    /**Command sent from the user*/
    private String userCmd;


    public BattleServer(int port) {
        this.game = new Game();
        current = 0;
        this.port = port;
        connectionAgents = new ArrayList<>();
        serverSocket = null;
        clientSocket =  null;
        userCmd = "";
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

            ConnectionAgent currentAgent = new User(clientSocket,
                    inFromClient, outToClient, this);

            currentAgent.setThread(new Thread(currentAgent));

            connectionAgents.add(currentAgent);
            currentAgent.go();
        }

        serverSocket.close();
    }

    public void broadcast(String message){
        for(ConnectionAgent c : connectionAgents){
            c.sendMessage(message);
        }
    }

    public void messageReceived(String message, MessageSource source){
        System.out.println(source + message);
    }

    public void sourceClosed(MessageSource source){

    }

    /**
     * Interprets the command sent from the users
     */
    public void gameCommand(String userCmd){
        this.userCmd = userCmd;

        if(userCmd.equals("/join")){
            //user joins server
        }else if(userCmd.equals("/play")){
            //user attempts to enter game
        }else if(userCmd.equals("/attack")){
            //User attempts to attack another player
        }else if(userCmd.equals("/quit")){
            //User wants to quit
        }else if(userCmd.equals("/show")){
            //User wants to see an opponents board
        }

    }//end gameCommand

}
