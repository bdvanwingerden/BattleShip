package client;

import common.ConnectionAgent;
import common.MessageListener;
import common.MessageSource;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class BattleClient extends MessageSource implements MessageListener {

    /**Address of battleship server*/
    private InetAddress host;

    /**Port to make available*/
    private int port;

    /**User name of client*/
    private String userName;

    /**Socket to connect to the server*/
    private Socket clientSocket;

    /**The current users corresponding connection agent*/
    ConnectionAgent currentAgent;

    /**Boolean to check if socket is still connected*/
    private boolean isConnected;

    /**Command to make request from the server*/
    String battleCommand;

    Scanner userInput;

    /**
     * Default constructor
     *
     * @param host - address of the BattleServer
     * @param port - port to connect to
     * @param userName - Name of current user
     */
    public BattleClient(InetAddress host, int port, String userName) {
        this.host = host;
        this.port = port;
        this.userName = userName;

        /**
        try {
            clientSocket = new Socket(host, port);
        }catch(IOException ioe){
            System.err.println("Couldn't get I/O for the connection to the host "
                    + host);
        }
         */
    }

    /**
     *
     * @throws IOException
     */
    public void connect() throws IOException{

        isConnected = true;

        clientSocket = new Socket(host, port);

        userInput = new Scanner(System.in);

            Scanner inFromServer =
                    new Scanner(new InputStreamReader(clientSocket.getInputStream()));

            PrintStream outToServer =
                    new PrintStream(clientSocket.getOutputStream());

            currentAgent = new ConnectionAgent(clientSocket, inFromServer,
                    outToServer, this);

            currentAgent.setThread(new Thread(currentAgent));

            currentAgent.go();

            send("/join " + userName);
            
            while(userInput.hasNextLine()){
                battleCommand = userInput.nextLine();
                send(battleCommand);
            }

    }//end connect()


    public void messageReceived(String message, MessageSource source){
        System.out.println(message);
    }


    public void sourceClosed(MessageSource source){

    }

    public void send(String message){
        currentAgent.sendMessage(message);
    }
}
