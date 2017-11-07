package meltedBackend.responseParser.responses;

import java.util.ArrayList;
import meltedBackend.common.MeltedCommandException;

/**
 * Generic Response class for storing Melted command's responses.
 * Use this class with StatusParser.
 * 
 * @author rombus
 */
public class GenericResponse {
    protected String status;           // Status string
    protected String[] singleData;     // Array of data for commands with one line responses (aside from status line)
    protected ArrayList<String[]> data;// ArrayList of data for commands with multi line responses (aside from status line)

    public GenericResponse(){}
    
    public <T extends GenericResponse> T createCopy(Class<T> type) throws InstantiationException, IllegalAccessException{
        T gr = type.newInstance();
        gr.status = status;
        gr.singleData = singleData;
        gr.data = data;

        return gr;
    }

    public GenericResponse setData(String status, String[] singleData, ArrayList<String[]> data){
        this.status = status;
        this.singleData = singleData;
        this.data = data;
        
        return this;
    }

    /**
     * Simlpe way of checking if the command was successful.
     *
     * @return true if the command was OK, false otherwise.
     * @throws meltedBackend.common.MeltedCommandException
     */
    public boolean cmdOk() throws MeltedCommandException{
        try{
            return status.split(" ")[1].equals("OK");
        }
        catch(Exception e){
            throw new MeltedCommandException("`GenericResponse` - Cannot get status data.");
        }
    }

    public String getStatus(){
        return status;
    }
}
