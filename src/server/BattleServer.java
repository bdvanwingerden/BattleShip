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
import java.util.Scanner;

public class BattleServer implements MessageListener{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int current;
    private Game game;
    private int port;
    private ArrayList<User> userQueue;

    public BattleServer(int port) {
        this.game = new Game();
        current = -1;
        this.port = port;
        serverSocket = null;
        clientSocket =  null;
        userQueue = new ArrayList<>();
    }

    public void listen() throws IOException{
        serverSocket = new ServerSocket(port);

        boolean serverDone = false;
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

    public void broadcast(String message){
        for(User c : game.getCurrentPlayers()){
            System.out.println("sending to all user");
            c.sendMessage(message);
        }
    }

    public void messageReceived(String message, MessageSource source){
        User currentUser = (User)source;

        System.out.println(message);

        Scanner messageScanner = new Scanner(message);

        switch (messageScanner.next()){
            case "/join":
                currentUser.setUsername(messageScanner.next());
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
                break;
            case "/show":
                currentUser.sendMessage(currentUser.getGrid().getGrid());
                break;
        }
    }

    public void play(User currentUser){
        if(current == -1) {
            game = new Game();
            game.addUsers(userQueue);
            broadcast("GAME STARTING!");
            incrementTurn();
        }else{
            currentUser.sendMessage("Please wait a game is already " +
                    "started");
        }
    }

    public void incrementTurn(){
        if(current < game.getCurrentPlayers().size()){
            current ++;
        }else{
            current = 0;
        }

        broadcast("it is " + game.getCurrentPlayers().get(current) + "'s turn");
    }

    public boolean isUsersTurn(User currentUser){
       boolean isTurn = false;
       if(game.getCurrentPlayers().get(current) == currentUser){
           isTurn = true;
       }
       return  isTurn;
    }

    public void attack(User currentUser, Scanner messageScanner){
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

    public void sourceClosed(MessageSource source){

    }

}
