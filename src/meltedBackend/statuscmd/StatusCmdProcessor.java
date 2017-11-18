package meltedBackend.statuscmd;

import meltedBackend.responseParser.responses.UstaResponse;

/**
 * This interfaces is used to process the data of the STATUS Melted cmd.
 * 
 * @author rombus
 */
public interface StatusCmdProcessor {
    void eventHandler(UstaResponse currentFrame, UstaResponse previousFrame, String line);
    void meltedDisonnected();
}
