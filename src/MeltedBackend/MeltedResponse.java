package MeltedBackend;

/**
 * Encapsulates a signle melted command's response.
 * There's only one object of this class during the execution of the melted server,
 * so when done using it for a command, resetResponse() must be called.
 *
 * @author rombus
 */
public class MeltedResponse {
    private String response = "";
    private boolean active = false; // If this response object is being written to
    
    public boolean isActive(){
        return active;
    }
    
    public String getResponse(){
        return response;
    }
    
    public void setActive(boolean active){
        this.active = active;
    }

    public synchronized void appendLine(String line){
        this.active = true;
        response = response.concat(line+"\n");        
    }
    
    public void resetResponse(){
        response = "";
    }
}
