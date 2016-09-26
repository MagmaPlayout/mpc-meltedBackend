package MeltedBackend;

import MeltedBackend.Common.MeltedCommandException;

/**
 * 
 * @author rombus
 */
public interface MeltedClient {
    String send(String cmd, long timeout) throws MeltedCommandException;
    String send(String cmd) throws MeltedCommandException;
}
