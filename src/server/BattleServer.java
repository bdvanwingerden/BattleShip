package server;

import common.MessageListener;
import common.MessageSource;
import server.game.Game;

import java.net.ServerSocket;

public class BattleServer implements MessageListener{
    private ServerSocket serverSocket;
    private int current;
    private Game game;

    public BattleServer(int port) {

    }

    public void listen(){

    }

    public void broadcast(String message){

    }

    public void messageReceived(String message, MessageSource source){

    }

    public void sourceClosed(MessageSource source){

    }
}
