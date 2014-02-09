package no.tdt4100.spillprosjekt.server;

/**
 * Created by eiriksylliaas on 08.02.14.
 *
 * Game Server
 */

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import no.tdt4100.spillprosjekt.client.GameClient;
import no.tdt4100.spillprosjekt.objects.ServerConnection;
import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;

import java.util.ArrayList;

public class GameServer {

    // Define server object
    Server server;

    // Start server, create new server object
    public static void main (String[] args) {
        GameServer gameserver = new GameServer();
        gameserver.start();
    }

    // Start Server
    public void start() {

        server = new Server(){
            protected Connection newConnection () {
                return new ServerConnection();
            }
        };

        Kryo kryo = server.getKryo();
        kryo.register(User.class);

        // Start server
        server.start();

        // Bind to ports
        // TCP, UDP
        try {
            server.bind(Config.ServerTCPPort, Config.ServerUDPPort);
        }
        catch (Exception e) {
            Logger.log(e);
        }

        // Listeners
        server.addListener(new Listener() {
            public void received (Connection c, Object object) {
                ServerConnection connection = (ServerConnection)c;

                if (object instanceof User) {

                    if (connection.user.getName() != null) return;

                    String name = ((User)object).getName();
                    if (name == null) return;

                    name = name.trim();
                    if (name.length() == 0) return;

                    connection.user = (User) object;

                    // Send message to all users. User connected.

                    // Just get connections
                    test();

                    return;
                }


            }
            public void disconnected (Connection c) {
                ServerConnection connection = (ServerConnection)c;
                if (connection.user != null) {
                    // User disconnected

                }
            }
        });


        // Test Client
        GameClient cli = new GameClient();
        cli.connect("Eirik Martiniussen Sylliaas");

    }

    public void test() {

        for (Connection con : server.getConnections()) {
            ServerConnection servercon = (ServerConnection) con;

            System.out.print(servercon.user.getName() + " hello");

        }

    }

}
