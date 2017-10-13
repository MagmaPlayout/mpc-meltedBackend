package meltedBackend.responseParser.responses;

import meltedBackend.common.MeltedCommandException;

/**
 * Response class for storing Melted's "USTA {UNIT}" command responses.
 * Use this class with SingleLineParser.
 *
 * @author rombus
 */
public class UstaResponse extends GenericResponse{
    public static final short MODE = 1;
    public static final short CUR_CLIP_PATH = 2;
    public static final short CUR_CLIP_INDEX = 16;
    public static final short CUR_CLIP_LEN = 8;
    public static final short CUR_CLIP_FRAME = 3;
    public static final short CUR_CLIP_FPS = 5;
    
    public int getPlayingClipIndex() throws MeltedCommandException{
        try{
            return Integer.parseInt(this.singleData[CUR_CLIP_INDEX]);
        }
        catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
            throw new MeltedCommandException("`UstaResponse` - Cannot get data at index "+CUR_CLIP_INDEX+".");
        }
    }

    public int getPlayingClipLength() throws MeltedCommandException{
        try{
            return Integer.parseInt(this.singleData[CUR_CLIP_LEN]);    
        }
        catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
            throw new MeltedCommandException("`UstaResponse` - Cannot get data at index "+CUR_CLIP_LEN+".");
        }
    }

    public int getPlayingClipFrame() throws MeltedCommandException{
        try{
            return Integer.parseInt(this.singleData[CUR_CLIP_FRAME]);
        }
        catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
            throw new MeltedCommandException("`UstaResponse` - Cannot get data at index "+CUR_CLIP_FRAME+".");
        }
    }

    public float getPlayingClipFPS() throws MeltedCommandException{
        try{
            String fpsValue = this.singleData[CUR_CLIP_FPS].replace(",", ".");
            return Float.parseFloat(fpsValue);
        }
        catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
            throw new MeltedCommandException("`UstaResponse` - Cannot get data at index "+CUR_CLIP_FPS+".");
        }
    }

    public String getPlayingClipPath() throws MeltedCommandException{
        try{
            return this.singleData[CUR_CLIP_PATH];
        }
        catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
            throw new MeltedCommandException("`UstaResponse` - Cannot get data at index "+CUR_CLIP_PATH+".");
        }
    }

    public String getUnitStatus() throws MeltedCommandException{
        try{
            return this.singleData[MODE];
        }
        catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
            throw new MeltedCommandException("`UstaResponse` - Cannot get data at index "+MODE+".");    
        }        
    }
}
