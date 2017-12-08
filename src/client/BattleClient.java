package client;

import common.ConnectionAgent;
import common.MessageListener;
import common.MessageSource;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class implements the client end of the battleship game
 */
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
    }

    /**
     * Create a connection agent and spawn a new thread to read in from the server
     * ConnectionAgent is then used to connect to server
     * Prompt user input until the connection is closed
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

    /**
     *
     * Used to notify observers that the subject has received a message.
     *
     * @param message The message received by the subject
     * @param source The source from which this message originated (if needed).
     */
    public void messageReceived(String message, MessageSource source){
        System.out.println(message);
    }

    /**
     *
     * Used to notify observers that the subject will not receive new messages; observers can
     * deregister themselves.
     *
     * @param source The <code>MessageSource</code> that does not expect more messages.
     */
    public void sourceClosed(MessageSource source){
        source.removeMessageListener(this);
    }

    /**
     * Pass message to the conenctionAgent and invoke sendMessage to notify other observers.
     * @param message
     */
    public void send(String message){
        currentAgent.sendMessage(message);
    }

}//end class BattleClient
