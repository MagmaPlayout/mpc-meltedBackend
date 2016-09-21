package MeltedBackend.ResponseParser.Parsers;

import MeltedBackend.ResponseParser.Responses.Response;

/**
 * Simple parsers interface.
 *
 * @author rombus
 */
public interface MeltedParser {
    public Response parse(String rawResponse);
}
