package server.game;

import common.ConnectionAgent;
import common.MessageListener;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Contains the user information and also extends the connection agent
 */
public class User extends ConnectionAgent{

    /** the users current grid */
    Grid grid;

    /** the users current user name */
    String username;

    /**
     * public constructor that is used to create a new user when anyone
     * connects to a server with a blank username that is set after they have
     * connected
     *
     * @param socket the socket that the connection is on
     * @param in information going from this user to the server
     * @param out information going from the server to this user
     * @param messageListener passes this users message listener to this
     */
    public User(Socket socket, Scanner in, PrintStream out, MessageListener
            messageListener) {
        super(socket, in, out, messageListener);
        this.username = null;
        grid = new Grid();
    }

    /**
     * sets this users user name
     * @param username the user to pass it to
     */
    public void setUsername(String username){
        this.username = username;
        System.out.println("username " +  username);
    }

    /**
     * returns this users current grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * returns this users current user name
     * @return the current user name
     */
    public String getUsername() {
        return username;
    }
}
