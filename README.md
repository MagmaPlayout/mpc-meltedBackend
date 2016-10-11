# java-melted-backend
Melted backend library for Magma Playout.

Basically it provides a way of sending MVCP commands to a running Melted telnet server.

You create *MeltedCmd*s through the *MeltedCmdFactory* class, execute them with *exec()* method 
and handle it's response by quering the response object returned by *exec()*.

Command execution blocks until a response is received or until the time out expires.

Here's an example class that demonstrates the ussage of the lib:
```java
import java.util.logging.Logger;
import meltedBackend.commands.MeltedCmd;
import meltedBackend.commands.MeltedCmdApnd;
import meltedBackend.commands.MeltedCmdFactory;
import meltedBackend.common.MeltedCommandException;
import meltedBackend.responseParser.responses.GenericResponse;
import meltedBackend.responseParser.responses.ListResponse;
import meltedBackend.telnetClient.MeltedTelnetClient;

public class LibTest {
    public static void main(String[] args) {
        new LibTest();
    }

    public LibTest(){
        MeltedTelnetClient melted = new MeltedTelnetClient();
        MeltedCmdFactory cmdFactory = new MeltedCmdFactory(melted);

        // Connects to a Melted server
        boolean connected = melted.connect("localhost", 5250, Logger.getLogger(getClass().getName()));
        while(!connected){
            connected = melted.reconnect(3, 1000);
        }

        if(connected){
            // Create some commands
            String unit = "U0";
            MeltedCmdApnd apnd = cmdFactory.getNewApndCmd(unit);
            MeltedCmd play = cmdFactory.getNewPlayCmd(unit);
            MeltedCmd list = cmdFactory.getNewListCmd(unit);
            
            try {
                // Configure and execute commands
                apnd.setFileName("/home/user/videos/vid1.mkv");
                apnd.exec(); // Not parsing response here...

                // apnd object is being reused here.
                // It's response is stored for further use.
                GenericResponse apndResponse = apnd.setFileName("/home/user/videos/a.m4v").exec();
                System.out.println("apnd status is: "+apndResponse.getStatus());

                // Parsing a more complex response
                ListResponse playlist = (ListResponse)list.exec();
                if(playlist.cmdOk()){
                    String[] clips = playlist.getMeltedPlaylist();
                    for(String clip: clips){
                        System.out.println("Playlist: \t"+clip);
                    }
                }

                play.exec();                
            } catch (MeltedCommandException e) {
                e.printStackTrace();
            }
        }
    }
}
```
