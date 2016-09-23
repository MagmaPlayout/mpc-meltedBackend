package MeltedBackend.ResponseParser.Parsers;

import MeltedBackend.ResponseParser.Responses.GenericResponse;

/**
 * Simple parsers interface.
 *
 * @author rombus
 */
public interface MeltedParser {
    public GenericResponse parse(String rawResponse);
}
