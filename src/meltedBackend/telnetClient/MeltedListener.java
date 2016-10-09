package meltedBackend.telnetClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This thread listens to melted responses and sends them to the MeltedResponse object.
 *
 * @author rombus
 */
public class MeltedListener implements Runnable {
    private final MeltedResponseWriter response;
    private final BufferedReader reader;
    private Logger logger;
    
    public MeltedListener(MeltedResponseWriter response, BufferedReader reader, Logger logger){
        this.response = response;
        this.reader = reader;
        this.logger = logger;
    }

    @Override
    public void run() {
        boolean keepRunning = true;
        
        while(keepRunning){
            try {
                String line = reader.readLine(); // Blocking method
                response.appendLine(line);
            } catch (IOException e) {
                keepRunning = false;
                logger.log(Level.WARNING, "Kiling TelnetListener Thread.");
            }
        }
    }
}
