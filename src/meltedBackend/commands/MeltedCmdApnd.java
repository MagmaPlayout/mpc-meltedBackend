package meltedBackend.commands;

import meltedBackend.common.MeltedClient;
import meltedBackend.responseParser.parsers.MeltedParser;

/**
 * This subclass of MeltedCmd allows ApndCmd objects to be reused by enabling the modification of it's arguments.
 * 
 * @author rombus
 */
public class MeltedCmdApnd extends MeltedCmd {

    public MeltedCmdApnd(boolean isGlobal, String cmd, String unit, String args, MeltedClient melted, MeltedParser parser) {
        super(isGlobal, cmd, unit, args, melted, parser);
    }

    public MeltedCmdApnd setFileName(String file){
        String prev = command;
        command = command.split(" ")[0] + " " + command.split(" ")[1] + " " + file;
        
        return this;
    }
}
