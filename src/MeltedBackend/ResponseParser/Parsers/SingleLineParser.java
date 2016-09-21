package MeltedBackend.ResponseParser.Parsers;

import MeltedBackend.ResponseParser.Responses.Response;

/**
 * Parser for melted commands with one line response (aside from status line)
 * (USTA, UGET ... )
 * 
 * @author rombus
 */
public class SingleLineParser extends AbstractMeltedParser{

    public SingleLineParser(Response response) {
        super(response);
    }

    @Override
    public Response parse(String rawResponse) {
        String[] lines = rawResponse.split("\n");
        String[] singleData = lines[1].split(" ");
        String status = lines[0];

        response.setData(status, singleData, null);
        return response;
    }
}
