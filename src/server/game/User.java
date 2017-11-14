package server.game;

public class User {
    Grid grid;
    String username;

    public User(String username) {
        this.username = username;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Grid getGrid() {
        return grid;
    }

    public String getUsername() {
        return username;
    }
}
