package MeltedBackend.ResponseParser.Parsers;

import MeltedBackend.ResponseParser.Responses.Response;

/**
 * Simple parser for melted commands with only a status line response
 * (load, apnd, play, pause, stop, rew, ff, remove, clean, wipe, ... )
 *
 * @author rombus
 */
public class StatusParser extends AbstractMeltedParser {

    public StatusParser(Response response) {
        super(response);
    }
    
    @Override
    public synchronized Response parse(String rawResponse){
        String[] lines = rawResponse.split("\n");
        response.setData(lines[0], null, null);
        
        return response;
    }
}
