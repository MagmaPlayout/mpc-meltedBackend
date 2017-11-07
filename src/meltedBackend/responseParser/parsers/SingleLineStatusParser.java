package meltedBackend.responseParser.parsers;

import meltedBackend.responseParser.responses.GenericResponse;

/**
 * Parser for melted's STATUS command line (process a single line from the STATUS output)
 * (STATUS)
 * 
 * @author rombus
 */
public class SingleLineStatusParser extends AbstractMeltedParser{

    public SingleLineStatusParser(GenericResponse response) {
        super(response);
    }

    @Override
    public GenericResponse parse(String rawResponse) {
        String status = ""; // There's no status flag here
        String[] singleData = null;
        singleData = rawResponse.split(" ");

        response.setData(status, singleData, null);
        return response;
    }
}
