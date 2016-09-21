package MeltedBackend.ResponseParser.Parsers;

import MeltedBackend.ResponseParser.Responses.Response;
import java.util.ArrayList;

/**
 * Parser for melted commands with multi line responses (aside from status line)
 * (LIST, CLS, ULS ... )
 *
 * @author rombus
 */
public class MultiLineParser extends AbstractMeltedParser{

    public MultiLineParser(Response response) {
        super(response);
    }
    
    @Override
    public Response parse(String rawResponse) {
        String[] lines = rawResponse.split("\n");
        String status = lines[0];
        ArrayList<String[]> data = new ArrayList<String[]>();
        
        for(int i=1; i< lines.length; i++){            
            data.add(lines[i].split(" "));
        }

        response.setData(status, null, data);
        
        return response;
    }
}
