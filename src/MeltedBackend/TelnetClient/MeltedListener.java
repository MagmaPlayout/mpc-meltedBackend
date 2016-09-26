package MeltedBackend.TelnetClient;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This thread listens to melted responses and sends them to the MeltedResponse object.
 *
 * @author rombus
 */
public class MeltedListener implements Runnable {
    private final MeltedResponseWriter response;
    private final BufferedReader reader;
    
    public MeltedListener(MeltedResponseWriter response, BufferedReader reader){
        this.response = response;
        this.reader = reader;
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
                System.out.println("Kiling TelnetListener Thread.");
            }
        }
    }
}
