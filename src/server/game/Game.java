package server.game;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

    private ArrayList<User> currentPlayers;

    public Game() {
        currentPlayers = new ArrayList<>();
    }

    public void addUsers(ArrayList newPlayers){
        currentPlayers = newPlayers;
    }

    public void removeUser(User user){
        currentPlayers.remove(user);
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
