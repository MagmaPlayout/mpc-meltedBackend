package MeltedBackend.Commands;

import MeltedBackend.Common.MeltedClient;
import MeltedBackend.Common.MeltedCommandException;
import MeltedBackend.ResponseParser.Parsers.MeltedParser;
import MeltedBackend.ResponseParser.Responses.GenericResponse;

/**
 * Represents a melted command as an object.
 *
 * @author rombus
 */
public class MeltedCmd {
    protected final MeltedClient melted;
    protected final MeltedParser parser;
    protected String command;

    public MeltedCmd(boolean isGlobal, String cmd, String unit, String args, MeltedClient melted, MeltedParser parser){
        this.parser = parser;
        this.melted = melted;
        command = cmd;
        
        if(!isGlobal){
            command = command.concat(" "+unit);
        }
        
        if(!args.isEmpty()){
            command = command.concat(" "+args);
        }
    }

    public GenericResponse exec() throws MeltedCommandException{
        return parser.parse(melted.send(command));
    }
}
