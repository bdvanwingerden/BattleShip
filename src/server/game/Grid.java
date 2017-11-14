package server.game;

import java.util.Random;

public class Grid {
    static final int DIMENSIONS = 10;
    String[][] grid;
    Random random;


    public Grid() {
        grid = new String[DIMENSIONS][DIMENSIONS];
        random = new Random();

        for(int y = 0; y < DIMENSIONS; y++){
            for(int x = 0; x < DIMENSIONS; x++){
                grid[x][y] = " ";
            }
        }
    }

    public void addShip(String symbol, int length){

        System.out.println("starting to add ship");

        int x = random.nextInt(DIMENSIONS);
        int y = random.nextInt(DIMENSIONS);
        int direction = random.nextInt(2);

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
                    for(int i = y; i < y+length; i++){
                        grid[x][i] = symbol;
                    }
                }else{
                    addShip(symbol,length);
                    return;
                }
            }
        }else{
            addShip(symbol, length);
            return;
        }

    }

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

    public static void main(String args[]){
        Grid grid = new Grid();
        grid.addShip("A", 5);
        grid.addShip("B", 4);
        grid.addShip("S", 3);
        grid.addShip("D", 3);
        grid.addShip("P", 2);
        System.out.println(grid.getGrid());

    }
}
