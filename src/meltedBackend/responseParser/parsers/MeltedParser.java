package meltedBackend.responseParser.parsers;

import meltedBackend.responseParser.responses.GenericResponse;

/**
 * Simple parsers interface.
 *
 * @author rombus
 */
public interface MeltedParser {
    public GenericResponse parse(String rawResponse);
}
