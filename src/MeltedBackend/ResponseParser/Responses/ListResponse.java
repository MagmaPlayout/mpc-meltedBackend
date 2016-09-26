package MeltedBackend.ResponseParser.Responses;

import MeltedBackend.Common.MeltedCommandException;

/**
 * Response class for storing Melted's "LIST {UNIT}" command responses.
 * Use this class with MultiLineParser.
 * 
 * @author rombus
 */
public class ListResponse extends GenericResponse{
    public static final short PLAYLIST_MODIFICATIONS = 0;
    public static final short CLIP_PATH = 1;

    public String getListModifications() throws MeltedCommandException{
        try{
            return this.data.get(PLAYLIST_MODIFICATIONS)[0];
        }
        catch(Exception e){
            throw new MeltedCommandException("`ListResponse` - Cannot get data at row "+PLAYLIST_MODIFICATIONS+".");
        }
    }

    public String[] getMeltedPlaylist() throws MeltedCommandException{
        if(this.data.isEmpty()){
            throw new MeltedCommandException("`ListResponse` - Cannot get data at index "+CLIP_PATH+".");
        }

        String[] paths = new String[this.data.size()-1]; // -1 for ignoring the first line of the response
        int i=0;

        for(String[] line: this.data){
            if(line.length > 1){ // Ignores the PLAYLIST_MODIFICATIONS
                paths[i++] = line[CLIP_PATH];
            }
        }

        return paths;
    }
}
