package server;

import java.io.IOException;

public class BattleShipDriver {
    static final int PORT = 5700;

    public static void main(String args[]){

        BattleServer bs;

        if(args.length > 0){
            bs = new BattleServer(Integer.parseInt(args[0]));
        }else{
            bs = new BattleServer(PORT);
        }


        try {
            bs.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
