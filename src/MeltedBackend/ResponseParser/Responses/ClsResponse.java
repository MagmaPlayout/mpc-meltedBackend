package MeltedBackend.ResponseParser.Responses;

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

    public ArrayList<String[]> getFileList(){
        return data;
    }
}
