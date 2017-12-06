package server.game;

public class User {
    Grid grid;
    String username;

    public User(String username) {
        this.username = username;
        grid = new Grid();
    }

    public Grid getGrid() {
        return grid;
    }

    public String getUsername() {
        return username;
    }
}
