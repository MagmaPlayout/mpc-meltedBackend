package MeltedBackend;

import MeltedBackend.Commands.ApndCmd;
import MeltedBackend.Commands.MeltedCmd;
import MeltedBackend.Commands.MeltedCmdFactory;
import MeltedBackend.Common.MeltedCommandException;
import MeltedBackend.ResponseParser.Responses.ClsResponse;
import MeltedBackend.ResponseParser.Responses.ListResponse;
import MeltedBackend.ResponseParser.Responses.UstaResponse;
import MeltedBackend.TelnetClient.MeltedTelnetClient;
import java.util.ArrayList;

/**
 * Sample program to demonstrate MeltedClient and Parsers classes usage.
 * 
 * @author rombus
 */
public class Main {
    private static final String U0 = "U0";
    private static final String VID1 = "/home/rombus/Documentos/PROYECTO_FINAL/videos/died.mkv";
    private static final String VID2 = "/home/rombus/Documentos/PROYECTO_FINAL/videos/a.m4v";

    public static void main(String[] args) {
        new Main();
    }
    
    public Main(){
        MeltedTelnetClient melted = new MeltedTelnetClient();
        
        if(connect(melted)){
            /*
        // Uso de MeltedBackend sin Parsers, imprimen los strings pelados, como vienen.
            try{
                System.out.println("----- USTA U0\n"+melted.send("USTA U0"));
                System.out.println("----- LIST U0\n"+melted.send("LIST U0", 30));
                System.out.println("----- PLAY U0\n"+melted.send("PLAY U0", 120));
                System.out.println("----- STOP U0\n"+melted.send("STOP U0", 70));
            }
            catch(MeltedCommandException e){
                e.printStackTrace();
            }
        // ----------------------------------------------------------------------------
            */

            /*
        // Manda comandos a mano pero usa los parsers para el output
            MeltedParser simpleParser = new StatusParser(new GenericResponse());
            MeltedParser ustaParser = new SingleLineParser(new UstaResponse());
            MeltedParser listParser = new MultiLineParser(new ListResponse());

            String[] playlist = null;
            try{
                playlist = (  (ListResponse)listParser.parse(  melted.send("LIST U0")  )   ).getMeltedPlaylist();                }
            catch(MeltedCommandException e){
                e.printStackTrace();                    
            }

            if(playlist.length < 3){
                System.out.println("Cargá 3 videos DISTINTOS a melted!!!");
            }
            else {
                printPlaylist(playlist);
                try{
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
                catch(MeltedCommandException e){
                    e.printStackTrace();
                }
            }
        //-------------------------------------------------------------
            */
            
            // Uso de command factory + parsers
            MeltedCmdFactory cf = new MeltedCmdFactory(melted);
            try{
                MeltedCmd usta = cf.getNewUstaCmd(U0);
                MeltedCmd play = cf.getNewPlayCmd(U0);
                MeltedCmd pause = cf.getNewPauseCmd(U0);
                MeltedCmd list = cf.getNewListCmd(U0);
                MeltedCmd cls = cf.getNewClsCmd("/home");
                ApndCmd apnd = cf.getNewApndCmd(U0);
                MeltedCmd clean = cf.getNewCleanCmd(U0);
                MeltedCmd remove = cf.getNewRemoveCmd(U0);

                clean.exec();
                remove.exec();

                System.out.println(apnd.exec().getStatus());    // Si ejecutás un ApndCmd que no tiene argumento de video, simplemente falla el status de melted
                apnd.setFileName(VID1).exec();
                apnd.setFileName(VID2).exec();
                apnd.setFileName(VID1).exec();

                System.out.println("Play cmd was ok?: "+play.exec().cmdOk());
                System.out.println("PlayingClip: "+((UstaResponse)usta.exec()).getPlayingClipPath());
                System.out.println("Pause cmd was ok?: "+pause.exec().cmdOk());

                ListResponse playlist = (ListResponse)list.exec();
                if(playlist.cmdOk()){
                    String[] vids = playlist.getMeltedPlaylist();
                    System.out.println("Playlist:");
                    for(String s: vids){
                        System.out.println("\t"+s);
                    }
                    System.out.println("\n");
                }

                ClsResponse r = (ClsResponse)cls.exec();
                if(r.cmdOk()){
                    ArrayList<String[]> files = r.getFileList();

                    System.out.println("List: ");
                    for (String[] file: files){
                        System.out.println("\t"+file[0]);
                    }
                    System.out.println("\n");
                }
                else {
                    System.out.println(r.getStatus());
                }
            }
            catch(MeltedCommandException e){
                e.printStackTrace();
            }
            
            
            disconnect(melted);
        }
        else {
            System.out.println("Couldn't connect to melted server!!!");
        }
        
        System.out.println("Program exited.");
    }
    

    private boolean connect(MeltedTelnetClient melted){
        boolean connected = melted.connect("localhost", 5250);

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


