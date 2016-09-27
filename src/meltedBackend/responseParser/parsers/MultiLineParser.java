package meltedBackend.responseParser.parsers;

import meltedBackend.responseParser.responses.GenericResponse;
import java.util.ArrayList;

/**
 * Parser for melted commands with multi line responses (aside from status line)
 * (LIST, CLS, ULS ... )
 *
 * @author rombus
 */
public class MultiLineParser extends AbstractMeltedParser{

    public MultiLineParser(GenericResponse response) {
        super(response);
    }
    
    @Override
    public GenericResponse parse(String rawResponse) {
        String[] lines = rawResponse.split("\n");
        String status = lines[0];
        ArrayList<String[]> data = null;
        
        if(status.split(" ")[1].equals(OK)){
            data = new ArrayList<>();

            for(int i=1; i< lines.length; i++){            
                data.add(lines[i].split(" "));
            }
        }

        response.setData(status, null, data);
        return response;
    }
}
