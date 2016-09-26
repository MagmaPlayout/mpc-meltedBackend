package MeltedBackend.Common;

/**
 * Checked exception to be thrown by the exec() or parse() of the command and it's response
 * 
 * @author rombus
 */
public class MeltedCommandException extends Exception{
    public MeltedCommandException(String msg){
        super(msg);
    }
}
