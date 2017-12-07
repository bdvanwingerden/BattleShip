package server.game;

import java.util.LinkedList;

public class Game {

    private LinkedList<User> currentPlayers;

    public Game() {
        currentPlayers = new LinkedList<>();
    }

    public void addUser(User user){
        currentPlayers.push(user);
    }

    public void removeUser(User user){
        currentPlayers.remove(user);
    }

    public LinkedList<User> getCurrentPlayers() {
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
