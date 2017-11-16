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

        System.out.println("Attempting to add " + symbol);

        int x = random.nextInt(DIMENSIONS);
        int y = random.nextInt(DIMENSIONS);
        int direction = random.nextInt(2);

        if(length <= DIMENSIONS){
            if(grid[x][y].equals(" ")){
                //HORIZONTAL
                if(direction == 0){
                    boolean noShips = true;

                    for(int i = x; i < x+length; i++){

                        if(i >= DIMENSIONS) {
                            addShip(symbol, length);
                            return;
                        }else if(!grid[i][y].equals(" ")){
                            noShips = false;
                        }
                    }

                    if(noShips){
                        System.out.println(symbol + " added");
                        for(int i = x; i < x+length; i++){
                            grid[i][y] = symbol;
                        }
                    }else{
                        addShip(symbol,length);
                        return;
                    }
                }
                //VERTICAL
                else if(direction ==1){
                    boolean noShips = true;

                    for(int i = y; i < y+length; i++){
                        if(i >= DIMENSIONS) {
                            addShip(symbol, length);
                            return;
                        }else if(!grid[x][i].equals(" ")){
                            noShips = false;
                        }
                    }

                    if(noShips){
                        System.out.println(symbol + " added");
                        for(int i = y; i < y+length; i++){
                            grid[x][i] = symbol;
                        }
                    }else{
                        addShip(symbol,length);
                    }
                }
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
    public void takeShot(int x,int y){

        if(x >= DIMENSIONS || y >= DIMENSIONS){
            System.out.println("Given coordinates are out of bounds!");
        }
        else{

            if(grid[x][y].equals(" ") || grid[x][y].equals("X")){
                System.out.println("That's a miss!");
                grid[x][y] = "X";
            }
            else if(grid[x][y].equals("@"))
                System.out.println("You already hit this spot!");
            else{
                grid[x][y] = "@";
                System.out.println("That's a hit!");
            }
        }
    }

    /**
     * main method to test adding ships, taking shots, and printing the board correctly 
     * @param args
     */
    public static void main(String args[]){
        Grid grid = new Grid();
        grid.addShip("A", 5);
        grid.addShip("B", 4);
        grid.addShip("S", 3);
        grid.addShip("D", 3);
        grid.addShip("P", 2);
        grid.takeShot(4,4);
        grid.takeShot(5,5);
        grid.takeShot(6,6);

        System.out.println(grid.getGrid());

    }
}
