package MeltedBackend.Commands;

import MeltedBackend.Common.MeltedCommandException;
import MeltedBackend.MeltedClient;
import MeltedBackend.ResponseParser.Parsers.MeltedParser;
import MeltedBackend.ResponseParser.Responses.GenericResponse;

/**
 * Represents a melted command as an object.
 *
 * @author rombus
 */
public class MeltedCmd {
    private final MeltedClient melted;
    private final MeltedParser parser;
    private String command;

    public MeltedCmd(String cmd, String unit, String args, MeltedClient melted, MeltedParser parser){
        this.parser = parser;
        this.melted = melted;

        command = cmd+" "+unit;        
        if(!args.isEmpty()){
            command = command.concat(" "+args);
        }
    }

    public GenericResponse exec() throws MeltedCommandException{
        return parser.parse(melted.send(command));
    }
}
