package server.game;


import common.ConnectionAgent;
import common.MessageListener;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class User extends ConnectionAgent{

    Grid grid;
    String username;

    public User(Socket socket, Scanner in, PrintStream out, MessageListener
            messageListener) {
        super(socket, in, out, messageListener);
        this.username = username;
        grid = new Grid();
    }

    public void setUsername(String username){
        this.username = username;
        System.out.println("username " +  username);
    }

    public Grid getGrid() {
        return grid;
    }

    public String getUsername() {
        return username;
    }
}
