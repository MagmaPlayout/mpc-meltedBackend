package meltedBackend.telnetClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import libconfig.ConfigurationManager;

/**
 * This thread listens to melted responses and sends them to the MeltedResponse object.
 *
 * @author rombus
 */
public class MeltedListener implements Runnable {
    private final MeltedResponseWriter response;
    private final BufferedReader reader;
    private Logger logger;
    private MeltedTelnetClient client;
    private final ConfigurationManager cfg;
    
    public MeltedListener(MeltedTelnetClient client, MeltedResponseWriter response, BufferedReader reader, Logger logger){
        this.response = response;
        this.reader = reader;
        this.logger = logger;
        this.client = client;
        this.cfg = ConfigurationManager.getInstance();
    }

    @Override
    public void run() {
        boolean keepRunning = true;
        
        while(keepRunning){
            try {
                String line = reader.readLine(); // Blocking method
                
                if(line != null){
                    response.appendLine(line);
                }
                else {
                    logger.log(Level.WARNING, "Melted backend - Error reading Melted output. Is it running?");
                    keepRunning = false;
                    client.disconnect();
                    client.reconnect(cfg.getMeltedReconnectionTries(), cfg.getMeltedReconnectionTimeout());
                }
                
            } catch (IOException e) {
                keepRunning = false;
                logger.log(Level.WARNING, "Kiling TelnetListener Thread.");
            }
        }
    }
}
