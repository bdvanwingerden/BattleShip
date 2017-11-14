package server.game;

import java.util.LinkedList;

public class Game {

    private LinkedList<User> currentPlayers;

    public Game() {
        currentPlayers = new LinkedList<>();
    }

    public void addUser(String username){
        if(!containsName(username)){
            currentPlayers.push(new User(username));
        }
    }

    public void removeUser(User user){
        currentPlayers.remove(user);
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
