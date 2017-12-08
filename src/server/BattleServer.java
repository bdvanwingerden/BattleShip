package server;

import common.MessageListener;
import common.MessageSource;
import server.game.Game;
import server.game.User;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Creates games and allows multiple clients to join our battleship game
 *
 * @author Bronson VanWingerden , Dorian Barrier
 */
public class BattleServer implements MessageListener{

    /**the socket to listen on*/
    private ServerSocket serverSocket;

    /**the temporary clientSocket used when a client initializes a connection*/
    private Socket clientSocket;

    /**the current turn*/
    private int current;

    /**the current game*/
    private Game game;

    /**the current port*/
    private int port;

    /**the current userQueue*/
    private ArrayList<User> userQueue;

    /**false while the server is still running*/
    boolean serverDone;

    /**
     * public constructor that creates a battle server given a port
     * @param port the port to listen on
     */
    public BattleServer(int port) {
        this.game = new Game();
        current = -1;
        this.port = port;
        serverSocket = null;
        clientSocket =  null;
        userQueue = new ArrayList<>();
    }

    /**
     * listens for clients to connect while the server is not done
     *
     * @throws IOException client socket cannot connect
     */
    public void listen() throws IOException{
        serverSocket = new ServerSocket(port);

        serverDone = false;
        //TODO make this not a forever loop
        while (!serverDone) {
            clientSocket = serverSocket.accept();

            Scanner inFromClient =
                    new Scanner(new InputStreamReader(clientSocket
                            .getInputStream()));

            PrintStream outToClient =
                    new PrintStream(clientSocket.getOutputStream());

            outToClient.print("Successfully connected to Battleship!");

            User currentAgent = new User(clientSocket,
                    inFromClient, outToClient, this);

            currentAgent.setThread(new Thread(currentAgent));

            userQueue.add(currentAgent);

            currentAgent.go();
        }

        serverSocket.close();
    }

    /**
     * broadcasts to all current users in the current game
     *
     * @param message the message to send
     */
    public void broadcast(String message){
        for(User c : game.getCurrentPlayers()){
            c.sendMessage(message);
        }
    }

    /**
     * handles any messages the user sends
     *
     * @param message The message received by the subject
     * @param source The source from which this message originated (if needed).
     */
    public void messageReceived(String message, MessageSource source) {
        User currentUser = (User)source;

        System.out.println(message);

        Scanner messageScanner = new Scanner(message);

        switch (messageScanner.next()){
            case "/join":
                join(currentUser, messageScanner);
                break;
            case "/play":
                play(currentUser);
                break;
            case "/attack":
                if(isUsersTurn(currentUser)) {
                    attack(currentUser, messageScanner);
                }else{
                    currentUser.sendMessage("It's Not currently your turn " +
                            "please wait!");
                }
                break;
            case "/quit":
                game.getCurrentPlayers().remove(currentUser);
                currentUser.close();
                if(game.getCurrentPlayers().size() < 2){
                    current = -1;
                    broadcast("There are not enough players to continue " +
                            "please wait for more to join");

                    if(game.getCurrentPlayers().size() < 1 && userQueue.size
                            () < 1){
                        serverDone = true;
                    }
                }
                break;
            case "/show":
                if(current != -1) {
                    currentUser.sendMessage(currentUser.getGrid().getGrid());
                }else {
                    currentUser.sendMessage("The game is not started yet");
                }
                break;
        }
    }

    /**
     * allows a user to join the game once they have joined the server
     *
     * @param currentUser the user trying to join
     * @param messageScanner the scanner to read in the user name from
     */
    public void join(User currentUser, Scanner messageScanner) {
        String nameToSet = messageScanner.next();

        if(!game.containsName(nameToSet)){
            try {
                currentUser.setUsername(messageScanner.next());
            }catch (NoSuchElementException e){

            }
        }else{
            currentUser.sendMessage(nameToSet + " is already in use please " +
                    "try again");
        }
    }

    /**
     * starts the game if enough players are joined otherwise sends an error
     * to the user that tried to start the game
     * @param currentUser the user that tried to start play
     */
    public void play(User currentUser){
        if(userQueue.size() > 1 && current == -1) {
            game = new Game();
            game.addUsers(userQueue);
            broadcast("GAME STARTING!");
            incrementTurn();
        }else{
            if(current != -1) {
                currentUser.sendMessage("Please wait a game is already " +
                        "started");
            }else if(userQueue.size()  < 2){
                currentUser.sendMessage("Please wait there are not enough " +
                        "players in the server");
            }
        }
    }

    /**
     * increments the current turn
     */
    public void incrementTurn(){
        if(current < game.getCurrentPlayers().size() - 1){
            current ++;
        }else{
            current = 0;
        }

        broadcast("it is " + game.getCurrentPlayers().get(current).getUsername()
                + "'s " + "turn");
    }

    /**
     * returns true if it is the the given users current turn
     * @param currentUser the current user to check if it is their turn
     * @return returns true if it is the users turn
     */
    public boolean isUsersTurn(User currentUser){
       boolean isTurn = false;
       if(game.getCurrentPlayers().get(current) == currentUser){
           isTurn = true;
       }
       return  isTurn;
    }

    /**
     * allows a user to attempt an attack on another user if the user doesn't
     * exist or the attack is out of bounds the turn is incremented and the
     * player looses their current turn
     * @param currentUser the user attempting to attack
     * @param messageScanner the scanner to read the attack input in from
     */
    public void attack(User currentUser, Scanner messageScanner) {
        String nameToAttack = null;
        User userToAttack = null;
        int  x = -1;
        int  y = -1;

        if(messageScanner.hasNext()){
            nameToAttack = messageScanner.next();
            for(User u : game.getCurrentPlayers()){
                if(u.getUsername().equals(nameToAttack)){
                    userToAttack = u;
                }
            }
        }

        if(messageScanner.hasNextInt()) {
            x = messageScanner.nextInt();
            if(messageScanner.hasNextInt()){
                y = messageScanner.nextInt();
            }
        }

        if(nameToAttack != null && userToAttack != null && x != -1 && y != -1){
            broadcast(currentUser.getUsername() + " is attacking " +
                    nameToAttack + " at " + x + " " + y);
            String result = userToAttack.getGrid().takeShot(x,y);
            broadcast(currentUser.getUsername() + " " + result);
        }else{
            if(nameToAttack == null){
                currentUser.sendMessage("No parameters were given");
            }else if(userToAttack == null){
                currentUser.sendMessage("that user is not in the current game" +
                        " or does not exist");
            }else if(x == -1 || y == -1){
                currentUser.sendMessage("The coordinates were incorrect");
            }
        }
        incrementTurn();
    }

    /**
     * removes a user from the listeners list
     * @param source The <code>MessageSource</code> that does not expect more messages.
     */
    public void sourceClosed(MessageSource source){
        User exited = (User) source;
        game.getCurrentPlayers().remove(exited);
    }

}
