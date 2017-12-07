package client;

import common.ConnectionAgent;
import common.MessageListener;
import common.MessageSource;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class BattleClient extends MessageSource implements MessageListener {

    /**Address of battleship server*/
    private InetAddress host;

    /**Port to make available*/
    private int port;

    /**User name of client*/
    private String userName;

    private Socket clientSocket;

    ConnectionAgent connectA;


    public BattleClient(InetAddress host, int port, String userName) {
        this.host = host;
        this.port = port;
        this.userName = userName;

        try {
            clientSocket = new Socket(host, port);
        }catch(IOException ioe){
            System.err.println("Couldn't get I/O for the connection to the host "
                    + host);
        }

        //connectA = new ConnectionAgent(clientSocket);
    }

    public void connect(){


    }//end connect()

    public void messageReceived(String message, MessageSource source){
        //will print out message received
    }

    public void sourceClosed(MessageSource source){

    }

    public void send(String message){

    }
}
