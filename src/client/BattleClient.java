package client;

import common.MessageListener;
import common.MessageSource;

import java.net.InetAddress;

public class BattleClient extends MessageSource implements MessageListener {
    private InetAddress host;
    private int port;
    private String userName;

    public BattleClient(InetAddress host, int port, String userName) {
        this.host = host;
        this.port = port;
        this.userName = userName;
    }

    public void connect(){

    }

    public void messageReceived(String message, MessageSource source){

    }

    public void sourceClosed(MessageSource source){

    }

    public void send(String message){

    }
}
