package MeltedBackend.ResponseParser.Parsers;

import MeltedBackend.ResponseParser.Responses.Response;

/**
 * This class is to enforce that each parser has it's own Response object.
 *
 * @author rombus
 */
public abstract class AbstractMeltedParser implements MeltedParser{
    protected Response response;

    public AbstractMeltedParser(Response response){
        this.response = response;
    }
}
