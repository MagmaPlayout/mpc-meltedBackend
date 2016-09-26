package MeltedBackend.ResponseParser.Responses;

import java.util.ArrayList;

/**
 * Generic Response class for storing Melted command's responses.
 * Use this class with StatusParser.
 * 
 * @author rombus
 */
public class GenericResponse {
    public String status;           // Status string
    public String[] singleData;     // Array of data for commands with one line responses (aside from status line)
    public ArrayList<String[]> data;// ArrayList of data for commands with multi line responses (aside from status line)

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
     */
    public boolean cmdOk(){
        if(!status.equals("-1"))
            return status.split(" ")[1].equals("OK");
        return false;
    }
}
