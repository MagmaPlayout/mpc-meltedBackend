package meltedBackend.responseParser.parsers;

import meltedBackend.responseParser.responses.GenericResponse;

/**
 * Simple parser for melted commands with only a status line response
 * (load, apnd, play, pause, stop, rew, ff, remove, clean, wipe, ... )
 *
 * @author rombus
 */
public class StatusParser extends AbstractMeltedParser {

    public StatusParser(GenericResponse response) {
        super(response);
    }
    
    @Override
    public synchronized GenericResponse parse(String rawResponse){
        String[] lines = rawResponse.split("\n");
        response.setData(lines[0], null, null);
        
        return response;
    }
}
