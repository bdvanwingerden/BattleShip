package client;

import common.MessageListener;
import common.MessageSource;

import java.io.PrintStream;

public class PrintStreamMessageListener implements MessageListener {
    private PrintStream out;

    public PrintStreamMessageListener(PrintStream out) {
        this.out = out;
    }

    public void messageReceived(String message, MessageSource source){

    }

    public void sourceClosed(MessageSource source){
        
    }
}
