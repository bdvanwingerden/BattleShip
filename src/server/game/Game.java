package server.game;

import java.util.ArrayList;


public class Game {

    private ArrayList<User> currentPlayers;

    public Game() {
        currentPlayers = new ArrayList<>();
    }

    public void addUsers(ArrayList<User> newPlayers){
        currentPlayers = newPlayers;
        for(User u : currentPlayers){
            u.grid = new Grid();
        }
    }

    public ArrayList<User> getCurrentPlayers() {
        return currentPlayers;
    }

    public boolean containsName(String username){
        boolean contains = false;
        for(User u : currentPlayers){
            if(u.getUsername().equals(username)){
                contains = true;
            }
        }
        return contains;
    }
}
