package MeltedBackend.ResponseParser.Responses;

/**
 * Response class for storing Melted's "USTA {UNIT}" command responses.
 * Use this class with SingleLineParser.
 *
 * @author rombus
 */
public class UstaResponse extends Response{    
    public static final short MODE = 1;
    public static final short CUR_CLIP_PATH = 2;
    public static final short CUR_CLIP_INDEX = 16;
    public static final short CUR_CLIP_LEN = 8;
    public static final short CUR_CLIP_FRAME = 3;
    
    public int getPlayingClipIndex(){
        return Integer.parseInt(this.singleData[CUR_CLIP_INDEX]);
    }

    public int getPlayingClipLength(){
        return Integer.parseInt(this.singleData[CUR_CLIP_LEN]);
    }

    public int getPlayingClipFrame(){
        return Integer.parseInt(this.singleData[CUR_CLIP_FRAME]);
    }

    public String getPlayingClipPath(){
        return this.singleData[CUR_CLIP_PATH];
    }

    public String getUnitStatus(){
        return this.singleData[MODE];
    }
}
