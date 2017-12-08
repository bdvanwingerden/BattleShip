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
            c.sendMessage(message);
        }
    }

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
                if(game.getCurrentPlayers().size() < 2){
                    current = -1;
                    broadcast("There are not enough players to continue " +
                            "please wait for more to join");
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

    public void incrementTurn(){
        if(current < game.getCurrentPlayers().size() - 1){
            current ++;
        }else{
            current = 0;
        }

        broadcast("it is " + game.getCurrentPlayers().get(current).getUsername()
                + "'s " + "turn");
    }

    public boolean isUsersTurn(User currentUser){
       boolean isTurn = false;
       if(game.getCurrentPlayers().get(current) == currentUser){
           isTurn = true;
       }
       return  isTurn;
    }

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

    public void sourceClosed(MessageSource source){
        User exited = (User) source;
        exited.close();
        game.getCurrentPlayers().remove(exited);
    }

}
