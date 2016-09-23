package MeltedBackend;

import MeltedBackend.Commands.MeltedCmdFactory;
import MeltedBackend.ResponseParser.Parsers.MeltedParser;
import MeltedBackend.ResponseParser.Parsers.MultiLineParser;
import MeltedBackend.ResponseParser.Parsers.SingleLineParser;
import MeltedBackend.ResponseParser.Parsers.StatusParser;
import MeltedBackend.ResponseParser.Responses.GenericResponse;
import MeltedBackend.ResponseParser.Responses.ListResponse;
import MeltedBackend.ResponseParser.Responses.UstaResponse;

/**
 * Sample program to demonstrate MeltedClient and Parsers classes usage.
 * 
 * @author rombus
 */
public class Main {
    private static final boolean TEST_PARSERS = true;
    private static final boolean TEST_COMMANDS = true;
    private static final String U0 = "U0";

    public static void main(String[] args) {
        new Main();
    }
    
    public Main(){
        MeltedTelnetClient melted = new MeltedTelnetClient();
        
        if(connect(melted)){
            if(!TEST_PARSERS){
                // Uso de MeltedBackend sin Parsers, imprimen los strings pelados, como vienen.
                System.out.println("----- USTA U0\n"+melted.send("USTA U0"));
                System.out.println("----- LIST U0\n"+melted.send("LIST U0", 30));
                System.out.println("----- PLAY U0\n"+melted.send("PLAY U0", 120));
                System.out.println("----- STOP U0\n"+melted.send("STOP U0", 70));
            }
            else if(!TEST_COMMANDS){
                MeltedParser simpleParser = new StatusParser(new GenericResponse());
                MeltedParser ustaParser = new SingleLineParser(new UstaResponse());
                MeltedParser listParser = new MultiLineParser(new ListResponse());

                String[] playlist = (  (ListResponse)listParser.parse(  melted.send("LIST U0")  )   ).getMeltedPlaylist();
                if(playlist.length < 3){
                    System.out.println("Cargá 3 videos DISTINTOS a melted!!!");
                }
                else {
                    printPlaylist(playlist);

                    melted.send("PLAY U0");
                    melted.send("CLEAN U0");
                    melted.send("APND U0 "+playlist[2]);
                    melted.send("APND U0 "+playlist[1]);
                    System.out.println("Reproduciendo: "+((UstaResponse) ustaParser.parse(melted.send("USTA U0"))  ).getPlayingClipPath());
                    melted.send("STOP U0");
                    melted.send("REW U0");

                    System.out.println("Invertí los últimos 2 videos.");
                    playlist = (  (ListResponse)listParser.parse(  melted.send("LIST U0")  )   ).getMeltedPlaylist();
                    printPlaylist(playlist);
                }
            }
            else {
                MeltedCmdFactory cf = new MeltedCmdFactory(melted);
                System.out.println(((UstaResponse)cf.getNewUstaCmd(U0).exec()).getPlayingClipPath());
                System.out.println(cf.getNewPlayCmd(U0).exec().cmdOk());
            }
            
            disconnect(melted);
        }
        else {
            System.out.println("Couldn't connect to melted server!!!");
        }
        
        System.out.println("Program exited.");
    }
    

    private boolean connect(MeltedTelnetClient melted){
        boolean connected = melted.connect("localhost", 5250, 320);

        if(connected == false){
            System.out.println("Fail connecting to melted server!!!");
            connected = melted.reconnect(3, 2000);
        }
        else {
            System.out.println("Connected to melted!");
        }
        
        return connected;
    }

    private void disconnect(MeltedTelnetClient melted){
        try {
            melted.disconnect();
            melted.getListenerThread().join();
        } catch (InterruptedException ex) {}
    }

    private void printPlaylist(String[] playlist){
        System.out.println("... Playlist ...");
        
        for(String clip: playlist){
            System.out.println(clip);
        }
    }
}


