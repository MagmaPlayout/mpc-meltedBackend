package MeltedBackend.ResponseParser.Responses;

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
    
    public int getPlayingClipIndex(){
        try{
            return Integer.parseInt(this.singleData[CUR_CLIP_INDEX]);
        }catch(NullPointerException e){
            // No está cargada la línea de datos
            return -1;
        }
    }

    public int getPlayingClipLength(){
        try{
            return Integer.parseInt(this.singleData[CUR_CLIP_LEN]);
        }catch(NullPointerException e){
            // No está cargada la línea de datos
            return -1;
        }
    }

    public int getPlayingClipFrame(){
        try{
            return Integer.parseInt(this.singleData[CUR_CLIP_FRAME]);
        }catch(NullPointerException e){
            // No está cargada la línea de datos
            return -1;
        }
    }

    public String getPlayingClipPath(){
        try{
            return this.singleData[CUR_CLIP_PATH];
        }catch(NullPointerException e){
            // No está cargada la línea de datos
            return " --- ";
        }
    }

    public String getUnitStatus(){
        return this.singleData[MODE];
    }
}
