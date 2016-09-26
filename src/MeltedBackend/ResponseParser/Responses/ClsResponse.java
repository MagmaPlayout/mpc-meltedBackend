package MeltedBackend.ResponseParser.Responses;

import MeltedBackend.Common.MeltedCommandException;
import java.util.ArrayList;

/**
 * Response class for storing Melted's "CLS {PATH}" command responses.
 * Use this class with MultiLineParser.
 * 
 * @author rombus
 */
public class ClsResponse extends GenericResponse{
    public static final short PLAYLIST_MODIFICATIONS = 0;
    public static final short CLIP_PATH = 1;

    public ArrayList<String[]> getFileList() throws MeltedCommandException{
        try{
            return data;
        }
        catch(Exception e){
            throw new MeltedCommandException("`ClsResponse` - Cannot get file list data.");
        }
    }
}
