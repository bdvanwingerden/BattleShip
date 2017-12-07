package common;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionAgent extends MessageSource implements Runnable {
    private Socket socket;
    private Scanner in;
    private PrintStream out;
    private Thread thread;

    public ConnectionAgent(Socket socket, Scanner in, PrintStream out, Thread
            thread){
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.thread = thread;
    }

    public void sendMessage(String message){
        out.println(message);
    }

    public boolean isConnected(){
        return socket.isConnected();
    }

    public void close(){
        try {
            socket.close();
        }catch (IOException e){
            //Do nothing because if we can't close the socket then it is
            // probably already closed
        }
    }

    public void run(){
        while(isConnected()){
            System.out.println("this socket is connected");
            thread.run();
        }
    }
}
