package client;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BattleDriver {

    /**
     * Main method: This is the entry point for the clients
     * @param args
     */
    public static void main(String[] args){

        /**Defaualt port number*/
        int port = 5700;

        /**Address of our battleship server*/
        InetAddress host;

        /**User name of client*/
        String username;

        //command line args are Hostname, Port, and username
        if(args.length != 3){
            System.out.println("Usage: <protocol> [<host name>] [<port>] [<username>]");
            System.exit(1); //1 -> Wrong number of arguments
        }
        else{
            try {
                port = Integer.parseInt(args[1]);
                host = InetAddress.getByName(args[0]);
                username = args[2];
                BattleClient client = new BattleClient(host,port,username);
                client.connect();
            }catch(UnknownHostException e){
                System.out.println("UnknownHostException: " + e.getMessage());
            }catch(IOException ioe){
                ///yeahhhh
                }
        }




    }//end main

}//end class BattleDriver
