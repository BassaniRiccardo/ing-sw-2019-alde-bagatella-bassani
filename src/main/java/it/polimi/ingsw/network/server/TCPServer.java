package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server for TCP connections running on a separate thread and capable of accepting requests in a timely manner
 *
 * @author marcobaga
 */
public class TCPServer implements Runnable {

    private int port;
    private boolean running;
    private static final Logger LOGGER = Logger.getLogger("serverLogger");

    public TCPServer(int port){
        this.port = port;
        this.running = false;
    }

    /**
     * Loop accepting connections
     */
    public void run(){
        running = true;
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try{
            serverSocket = new ServerSocket(port);

            LOGGER.log(Level.INFO, "TCPServer ready");

            while (running){
                Socket socket = serverSocket.accept();
                executor.submit(new TCPPlayerController(socket));
                LOGGER.log(Level.INFO, "Accepted new connection");
            }
            serverSocket.close();
            LOGGER.log(Level.INFO, "TCPServer shutting down");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "TCPServer initialization failed", ex);
            //try again?
        }
    }

    /**
     * Getters and setters
     *
     */

    public int getPort() {  //only used for testing
        return port;
    }

    public void shutdown(){ this.running = false;}
}
