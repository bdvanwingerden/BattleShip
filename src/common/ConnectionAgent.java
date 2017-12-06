package common;

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
    }

    public boolean isConnected(){
        return false;
    }

    public void close(){

    }

    public void run(){

    }
}
