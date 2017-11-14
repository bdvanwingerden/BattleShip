package server.game;

public class Grid {
    static final int DIMENSIONS = 4;
    String[][] grid;


    public Grid() {
        grid = new String[DIMENSIONS][DIMENSIONS];

        for(int y = 0; y < DIMENSIONS; y++){
            for(int x = 0; x < DIMENSIONS; x++){
                grid[x][y] = " ";
            }
        }
    }

    public void addShips(){

    }

    public String getGrid(){

        String drawnGrid = "";

        for(int i = 0; i < DIMENSIONS; i++){
            drawnGrid += "_";
        }

        for(int y = 0; y < DIMENSIONS; y++){
            String nextRow = "|";
            for(int x = 0; x < DIMENSIONS; x++){
                nextRow  += grid[x][y] + "|";
            }
            drawnGrid += "\n" + nextRow;
        }
        return drawnGrid;
    }

    public static void main(String args[]){
        Grid grid = new Grid();
        System.out.println(grid.getGrid());

    }
}
