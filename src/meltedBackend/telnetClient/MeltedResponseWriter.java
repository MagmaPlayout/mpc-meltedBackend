package meltedBackend.telnetClient;

/**
 * Encapsulates a signle melted command's response.
 * There's only one object of this class during the execution of the melted server,
 * so when done using it for a command, resetResponse() must be called.
 *
 * @author rombus
 */
public class MeltedResponseWriter {
    private String response = "";
    private boolean active = false;         // If this response object is being written to with a single command's response
    private boolean finished = false;       // If the response is completely written
    private boolean isMultiLine = false;    // True = multiline response; False = single line response
    
    public boolean completed(){
        return finished;
    }
    
    public String getResponse(){
        return response;
    }

    public synchronized void appendLine(String line){
        if(!active){    // First line to be appended
            active = true;            
            int statusCode = Integer.parseInt(line.split(" ")[0]);
            
            if(statusCode == 200 || statusCode > 299){
                finished = true;
            }
            else if(statusCode == 202){
                isMultiLine = false;
            }
            else if(statusCode == 201) {
                isMultiLine = true;
            }
            else{
               finished = true; // Message without status  
            }
        }
        else if(!isMultiLine || line.equals("")){ // Multiline responses are finished with a blank line
            finished = true;
        }
        
        response = response.concat(line+"\n");
    }
    
    public void resetResponse(){
        response = "";
        finished = false;
        active = false;
    }
}
