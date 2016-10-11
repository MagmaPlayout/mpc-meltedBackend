package meltedBackend.commands;

import meltedBackend.common.MeltedClient;
import meltedBackend.responseParser.parsers.MeltedParser;
import meltedBackend.responseParser.parsers.MultiLineParser;
import meltedBackend.responseParser.parsers.SingleLineParser;
import meltedBackend.responseParser.parsers.StatusParser;
import meltedBackend.responseParser.responses.ClsResponse;
import meltedBackend.responseParser.responses.GenericResponse;
import meltedBackend.responseParser.responses.ListResponse;
import meltedBackend.responseParser.responses.UstaResponse;

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
    private final MeltedParser clsParser = new MultiLineParser(new ClsResponse());

    public MeltedCmdFactory(MeltedClient melted){
        this.melted = melted;
    }

    // Unit Commands -----------------------------------------------------------
    public MeltedCmd getNewUstaCmd(String unit){
        return new MeltedCmd(false, "USTA", unit, "", melted, ustaParser);
    }

    public MeltedCmd getNewListCmd(String unit){
        return new MeltedCmd(false, "LIST", unit, "", melted, listParser);
    }

    public MeltedCmd getNewPlayCmd(String unit){
        return new MeltedCmd(false, "PLAY", unit, "", melted, simpleParser);
    }
    
    public MeltedCmd getNewStopCmd(String unit){
        return new MeltedCmd(false, "STOP", unit, "", melted, simpleParser);
    }

    public MeltedCmd getNewPauseCmd(String unit){
        return new MeltedCmd(false, "PAUSE", unit, "", melted, simpleParser);
    }

    public MeltedCmd getNewCleanCmd(String unit){
        return new MeltedCmd(false, "CLEAN", unit, "", melted, simpleParser);
    }

    public MeltedCmd getNewRemoveCmd(String unit){
        return new MeltedCmd(false, "REMOVE", unit, "", melted, simpleParser);
    }

    public MeltedCmdApnd getNewApndCmd(String unit, String file){
        return new MeltedCmdApnd(false, "APND", unit, file, melted, simpleParser);
    }

    // TODO create a class of GotoCmd for enabling pooling
    public MeltedCmd getNewGotoCmd(String unit, int clipId, int framePosition){
        return new MeltedCmd(false, "GOTO", unit, clipId+" "+framePosition, melted, simpleParser);
    }

    /**
     * Allows creating an ApndCmd without arguments.
     * Before calling exec() on this object be sure to set it's argument with setFileName(file) method,
     * otherwise it will 404.
     *
     * @param unit
     * @return
     */
    public MeltedCmdApnd getNewApndCmd(String unit){
        return new MeltedCmdApnd(false, "APND", unit, "", melted, simpleParser);
    }

    
    // Global Commands ---------------------------------------------------------
    public MeltedCmd getNewClsCmd(String path){
        String quoted = "\"".concat(path).concat("\"");
        return new MeltedCmd(true, "CLS", "", quoted, melted, clsParser);
    }
}
