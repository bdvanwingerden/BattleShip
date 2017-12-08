package server.game;

import java.util.Random;

public class Grid {
    /**sets the x & y dimensions of the board to create */
    static final int DIMENSIONS = 10;

    /** the 2d array to contain the ships hits & misses for each players board */
    String[][] grid;

    /**random number generator to randomly generate the locations for a ship to be placed*/
    Random random;


    /**
     * public constructor that initializes the Grid then places a single space in each grid square
     */
    public Grid() {
        grid = new String[DIMENSIONS][DIMENSIONS];
        random = new Random();

        for(int y = 0; y < DIMENSIONS; y++){
            for(int x = 0; x < DIMENSIONS; x++){
                grid[x][y] = " ";
            }
        }

        addShips();
    }

    /**
     * randomly assigns a location and orientation to a ship given a ships symbol & length. If
     * there is already a ship at the random location or the ship extends out of bounds this
     * method recursively tries again until the ship can be successfully placed on the grid
     * @param symbol The single character String that is used to represent the ship being placed,
     *              it can be any single character so long as it is not " ", "@", "X"
     * @param length the desired length of the ship to be added MUST BE LESS THAN THE DIMENSIONS
     *               or the ship will not be added!
     */
    public void addShip(String symbol, int length){

        int x = random.nextInt(DIMENSIONS);
        int y = random.nextInt(DIMENSIONS);
        int direction = random.nextInt(2);

        if(length <= DIMENSIONS){
            if(grid[x][y].equals(" ")){
                attemptToDrawShip(x,y,symbol,length,direction);
            }else{
                addShip(symbol, length);
            }
        }

    }

    /**
     * Returns the contents of the grid correctly formatted as a battleship board
     * @return the String containing the formatted board
     */
    public String getGrid(){

        String drawnGrid = "  ";
        String gridLine  = "  +";

        for(int i = 0; i < (DIMENSIONS); i++){
            drawnGrid += "  " + i + " ";
            gridLine  += "---+";
        }

        drawnGrid += "\n" + gridLine;

        for(int y = 0; y < DIMENSIONS; y++){
            String nextRow = y + " |";
            for(int x = 0; x < DIMENSIONS; x++){
                nextRow  += " " + grid[x][y] + " |";
            }
            drawnGrid += "\n" + nextRow;
            drawnGrid += "\n" + gridLine;
        }
        return drawnGrid;
    }

    /**
     * attempts a shot at the given coordinates on the board if it is a miss it replaces that
     * square with a X representing a miss and if it is a hit it replaces the spot with an @
     * @param x the x coordinate to aim for
     * @param y the y coordinate to aim for
     */
    public String takeShot(int x,int y){

        String result;

        if(x >= DIMENSIONS || y >= DIMENSIONS){
            result = "has Given coordinates that are out of bounds!";
        }
        else{

            if(grid[x][y].equals(" ") || grid[x][y].equals("X")){
                result = "missed!";
                grid[x][y] = "X";
            }
            else if(grid[x][y].equals("@"))
                result = "already hit this spot!";
            else{
                grid[x][y] = "@";
                result = "hit!";
            }
        }

        return result;
    }


    /**
     *
     * @param x
     * @param y
     * @param symbol
     * @param length
     * @param orientation 0 if horizontal and 1 if vertical
     */
    public void attemptToDrawShip( int x, int y, String symbol,
                                          int length, int orientation){
        boolean noShips = true;

        for(int i = y; i < y+length; i++){

            if(i >= DIMENSIONS) {
                addShip(symbol, length);
                return;
            }else if(!grid[x][i].equals(" ") && orientation == 1 || !grid[i][y]
                    .equals(" ")
                    &&
                    orientation == 0){
                noShips = false;
            }
        }

        if(noShips){

            for(int i = y; i < y+length; i++){
                if(orientation == 1){
                    grid[x][i] = symbol;
                }else if(orientation == 0){
                    grid[i][y] = symbol;
                }

            }
        }else{
            addShip(symbol,length);
        }
    }

    public void addShips(){
        addShip("A", 5);
        addShip("B", 4);
        addShip("S", 3);
        addShip("D", 3);
        addShip("P", 2);
    }

    /**
     * main method to test adding ships, taking shots, and printing the board correctly
     * @param args
     */
    public static void main(String args[]){
        Grid grid = new Grid();

        grid.takeShot(4,4);
        grid.takeShot(5,5);
        grid.takeShot(6,6);

        System.out.println(grid.getGrid());

    }
}
