package client;

import common.MessageSource;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionAgent extends MessageSource implements Runnable {

    /**Socket to connect to the server*/
    private Socket socket;

    /**Used to scan in data from the user*/
    private Scanner in;

    /**Used to read in data from the server*/
    private PrintStream out;

    /**Used to create a new thread*/
    private Thread thread;

    /**
     * Default constructor
     * @param socket
     */
    public ConnectionAgent(Socket socket){

        try {
            this.socket = socket;
            in = new Scanner(System.in);
            out = new PrintStream(socket.getOutputStream());
            thread = null;
        } catch(IOException ioe){
            System.err.println("Couldn't get I/O for the connection to the host ");
        }
    }

    /**
     * Send message to all observers
     * @param message
     */
    public void sendMessage(String message){
        //will notify here
    }

    public boolean isConnected(){
        return false;
    }

    public void close(){

    }

    /**
     * Starts thread
     */
    public void startThread(){
        thread = new Thread(new ConnectionAgent(socket)).start();
    }

    public void run(){

        //message response from the server
        String serverMessage = "";

        while((serverMessage = in.nextLine()) != null){
            System.out.print(serverMessage);
        }

    }
}
