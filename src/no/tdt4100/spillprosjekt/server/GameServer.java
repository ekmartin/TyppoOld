package no.tdt4100.spillprosjekt.server;

/**
 * Created by eiriksylliaas on 08.02.14.
 *
 * Game Server
 */

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;

public class GameServer {

    public static void main (String[] args) {

        // Start server
        Server server = new Server();
        server.start();

        // Bind to ports
        // TCP, UDP
        try {
            server.bind(Config.ServerTCPPort, Config.ServerUDPPort);
        }
        catch (Exception e) {
            Logger.Log(e);
        }







    }

}
