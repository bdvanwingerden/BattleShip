package server;

import java.io.IOException;

public class BattleShipDriver {
    static final int PORT = 5700;

    public static void main(String args[]){
        BattleServer bs = new BattleServer(PORT);

        try {
            bs.listen();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
