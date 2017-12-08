package server.game;

import java.util.ArrayList;

/**
 * Contains all current players in the game
 *
 * @author Bronson VanWingerden, Dorian Barrier
 */
public class Game {

    /** players currently playing */
    private ArrayList<User> currentPlayers;

    /**
     * public constructor that initializes the current player list
     */
    public Game() {
        currentPlayers = new ArrayList<>();
    }

    /**
     * accepts an arraylist of users to add then adds all of them to this
     * current game and creates a new grid for each player
     * @param newPlayers the list of players to add to the new game
     */
    public void addUsers(ArrayList<User> newPlayers){
        currentPlayers = newPlayers;
        for(User u : currentPlayers){
            u.grid = new Grid();
        }
    }

    /**
     * returns the list of current players
     * @return the list of current players
     */
    public ArrayList<User> getCurrentPlayers() {
        return currentPlayers;
    }

    /**
     * returns true if the list contains a current username
     * @param username the user name to check for
     * @return true or false depending on if the name is in the list
     */
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
