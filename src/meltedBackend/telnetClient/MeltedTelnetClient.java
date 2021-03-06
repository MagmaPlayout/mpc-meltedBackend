package meltedBackend.telnetClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import meltedBackend.common.MeltedClient;
import meltedBackend.common.MeltedCommandException;

/** 
 * Telnet client that connects to the Melted server.
 * 
 * @author rombus
 */
public class MeltedTelnetClient implements MeltedClient {
    private static final int START_DELAY_MS = 100;      // Delay needed to start using melted after it's execution.
    private static final int SLEEP_TIME_MS = 10;        // Sleep time in ms while reading telnet's response.
    private static final int DEFAULT_CMD_TIMEOUT = 10000;// Command timeout in ms

    private int port;
    private String hostname;
    private long timeout;   // in ms
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;    
    private MeltedResponseWriter response;
    private Thread responseListener;
    private Logger logger;

    /**
     * Connects to a Melted Telnet server.
     *
     * @param hostname melted server hostname
     * @param port  melted server port
     * @param timeout default command timeout in ms
     * @param logger application logger
     * @return true if connection succedeed, false otherwise
     */
    public boolean connect(String hostname, int port, long timeout, Logger logger){
        this.hostname = hostname;
        this.port = port;
        this.timeout = timeout;
        this.logger = logger;

        try {
            socket = new Socket(hostname, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            return false;
        }
        
        response = new MeltedResponseWriter();
        responseListener = new Thread(new MeltedListener(response, reader, logger));
        
        responseListener.start();

        // Melted server needs a couple of ms to be ready for commands.
        try { Thread.sleep(START_DELAY_MS);
        } catch (InterruptedException ex) { //TODO
            ex.printStackTrace();
        }

        return true;
    }

    /**
     * Connects to a Melted Telnet server using the default timeout
     *
     * @param hostname melted server hostname
     * @param port  melted server port
     * @param logger application logger
     * @return true if connection succedeed, false otherwise
     */
    public boolean connect(String hostname, int port, Logger logger){
        this.logger = logger;
        return connect(hostname, port, DEFAULT_CMD_TIMEOUT, logger);
    }
    
    public void disconnect(){
        try {
            writer.close();
            reader.close();
            responseListener.interrupt();
            socket.close();
        } catch (IOException ex) {  //TODO
            ex.printStackTrace();
        }
    }

    /**
     * Reconnection routine.
     *
     * @param cantTries if 0 it tries indefinitely until the connection succeeds
     * @param msBetweenTries sleep between tries in ms
     * @return true if connected, false otherwise
     */
    public boolean reconnect(int cantTries, long msBetweenTries){
        //TODO: reemplazar los sout por un logger        
        boolean connected = false;
        boolean keepTrying = true;
        int tryNumber = 0;

        while(keepTrying){
            tryNumber++;
            logger.log(Level.INFO, "Reconnecting... try #"+tryNumber);

            if(connect(hostname, port, logger) == true){
                logger.log(Level.INFO, "Connection succeeded, resuming operations.");
                connected = true;
                break;
            }

            if(cantTries != 0){
                logger.log(Level.INFO, "Reconnection failed, waiting "+msBetweenTries+"ms for next try, "+(cantTries-tryNumber)+" remaining.");

                if(tryNumber >= cantTries){
                    keepTrying = false;
                    break;
                }
            }
            else {
                logger.log(Level.INFO, "Reconnection failed, waiting "+msBetweenTries+"ms for next try.");
            }

            try {   Thread.sleep(msBetweenTries);
            } catch (InterruptedException ex) {
                keepTrying = false;
                connected = false;
                logger.log(Level.INFO, "Killing MeltedTelnetClient thread.");
            }
        }
        
        return connected;
    }
    
    /**
     * Sends a melted command and returns it's response (empty string if timed out).
     * This is a synchronized method to prevent sending asynchronous commands to the melted server.
     *
     * @param cmd melted command
     * @param timeout command timeout in ms
     * @return melted response
     * @throws meltedBackend.common.MeltedCommandException     
     */
    @Override
    public synchronized String send(String cmd, long timeout) throws MeltedCommandException{
        response.resetResponse();   // Clean response
        writer.println(cmd);        // Send telnet command

        timeout = TimeUnit.NANOSECONDS.convert(timeout, TimeUnit.MILLISECONDS); // Convert to nanoseconds

        // Grab response...
        long inactiveTime, lastActiveTime = System.nanoTime();
        
        while(!response.completed()){
            inactiveTime = System.nanoTime() - lastActiveTime;
            
            if(inactiveTime >= timeout){
               throw new MeltedCommandException("Command `"+cmd+"` timed out.  "+inactiveTime+"ns inactive.");
            }
            
            try {
                Thread.sleep(SLEEP_TIME_MS);
            } catch (InterruptedException ex) { //TODO
                ex.printStackTrace();
            }
        }
        
        return response.getResponse();
    }

    /**
     * Sends a melted command using default timeout
     *
     * @param cmd melted command
     * @return melted response
     * @throws meltedBackend.common.MeltedCommandException     
     */
    @Override
    public String send(String cmd) throws MeltedCommandException{
        return send(cmd,timeout);
    }

    public Thread getListenerThread(){
        return responseListener;
    }
}
