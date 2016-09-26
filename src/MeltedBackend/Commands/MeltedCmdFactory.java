package MeltedBackend.Commands;

import MeltedBackend.MeltedClient;
import MeltedBackend.ResponseParser.Parsers.MeltedParser;
import MeltedBackend.ResponseParser.Parsers.MultiLineParser;
import MeltedBackend.ResponseParser.Parsers.SingleLineParser;
import MeltedBackend.ResponseParser.Parsers.StatusParser;
import MeltedBackend.ResponseParser.Responses.GenericResponse;
import MeltedBackend.ResponseParser.Responses.ListResponse;
import MeltedBackend.ResponseParser.Responses.UstaResponse;

/**
 * Factory for melted commands.
 * A single command can be used more than once.
 * 
 * @author rombus
 */
public class MeltedCmdFactory {
    private final MeltedClient melted;
    private final MeltedParser simpleParser = new StatusParser(new GenericResponse());
    private final MeltedParser ustaParser = new SingleLineParser(new UstaResponse());
    private final MeltedParser listParser = new MultiLineParser(new ListResponse());


    public MeltedCmdFactory(MeltedClient melted){
        this.melted = melted;
    }

    public MeltedCmd getNewUstaCmd(String unit){
        return new MeltedCmd("USTA", unit, "", melted, ustaParser);
    }

    public MeltedCmd getNewListCmd(String unit){
        return new MeltedCmd("LIST", unit, "", melted, listParser);
    }

    public MeltedCmd getNewPlayCmd(String unit){
        return new MeltedCmd("PLAY", unit, "", melted, simpleParser);
    }
    
    public MeltedCmd getNewStopCmd(String unit){
        return new MeltedCmd("STOP", unit, "", melted, simpleParser);
    }

    public MeltedCmd getNewPauseCmd(String unit){
        return new MeltedCmd("PAUSE", unit, "", melted, simpleParser);
    }
}
