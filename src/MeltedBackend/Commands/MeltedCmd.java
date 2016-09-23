package MeltedBackend.Commands;

import MeltedBackend.MeltedClient;
import MeltedBackend.ResponseParser.Parsers.MeltedParser;
import MeltedBackend.ResponseParser.Responses.GenericResponse;

/**
 * Represents a melted command as an object.
 *
 * @author rombus
 */
public class MeltedCmd {
    private MeltedClient melted;
    private String command;
    private MeltedParser parser;

    public MeltedCmd(String cmd, String unit, String args, MeltedClient melted, MeltedParser parser){
        this.parser = parser;
        this.melted = melted;

        command = cmd+" "+unit;        
        if(!args.isEmpty()){
            command.concat(" "+args);
        }
    }

    public GenericResponse exec(){
        return parser.parse(melted.send(command));
    }
}
