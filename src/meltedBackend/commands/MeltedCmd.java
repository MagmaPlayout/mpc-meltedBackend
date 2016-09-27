package meltedBackend.commands;

import meltedBackend.common.MeltedClient;
import meltedBackend.common.MeltedCommandException;
import meltedBackend.responseParser.parsers.MeltedParser;
import meltedBackend.responseParser.responses.GenericResponse;

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
