package MeltedBackend;

/**
 * 
 * @author rombus
 */
public interface MeltedClient {
    String send(String cmd, long timeout);
    String send(String cmd);
}
