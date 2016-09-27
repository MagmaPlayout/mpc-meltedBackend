package meltedBackend.responseParser.parsers;

import meltedBackend.responseParser.responses.GenericResponse;

/**
 * Parser for melted commands with one line response (aside from status line)
 * (USTA, UGET ... )
 * 
 * @author rombus
 */
public class SingleLineParser extends AbstractMeltedParser{

    public SingleLineParser(GenericResponse response) {
        super(response);
    }

    @Override
    public GenericResponse parse(String rawResponse) {
        String[] lines = rawResponse.split("\n");
        String status = lines[0];
        String[] singleData = null;

        if(status.split(" ")[1].equals(OK)){
            singleData = lines[1].split(" ");
        }

        response.setData(status, singleData, null);
        return response;
    }
}
