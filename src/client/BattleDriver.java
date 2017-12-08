package client;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Battle Driver is the starting point for the client end of the battleship game
 */
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

        //If number of arguments != 3 print a usage error
        if(args.length != 3){
            System.out.println("Usage: [<host name>] [<port>] [<username>]");
            System.exit(1); //1 -> Wrong number of arguments
        }
        else{
            try {

                port = Integer.parseInt(args[1]);
                host = InetAddress.getByName(args[0]);
                username = args[2];

                //Create the Client
                BattleClient client = new BattleClient(host,port,username);
                //Prompt client to contact the server
                client.connect();
            }catch(UnknownHostException e){
                System.out.println("UnknownHostException: " + e.getMessage());
            }catch(IOException ioe){
                System.out.println("IOException: " + ioe.getMessage());
                }
        }

    }//end main

}//end class BattleDriver
