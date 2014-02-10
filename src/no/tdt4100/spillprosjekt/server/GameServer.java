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
import no.tdt4100.spillprosjekt.objects.*;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;
import no.tdt4100.spillprosjekt.utils.ServerInit;

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
        ServerInit.mapClasses(server.getKryo());



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
                    userLoggedIn(connection);


                    return;
                }

                // Read commands
                if (object instanceof Config.commands) {

                    Config.commands command = (Config.commands) object;

                    switch (command) {
                        case getUserList: { sendUserList(connection) ; break;}
                    }

                }


            }
            public void disconnected (Connection c) {
                ServerConnection connection = (ServerConnection)c;
                if (connection.user != null) {
                    // User disconnected
                    userLoggedOut(connection);
                }
            }


        });

    }



    // Server functions
    private void sendUserList(ServerConnection c) {
        UserList userList = new UserList();
        for (Connection connection : server.getConnections()) {
            ServerConnection serverconnection = (ServerConnection) connection;

            userList.userList.add(serverconnection.getUser());

        }
        c.sendTCP(userList);
    }

    // Notify all users, new user connected
    private void userLoggedIn(ServerConnection c) {
        UserMessage message = new UserMessage(Config.commands.userConnected, c.getUser());

        for (Connection connection : server.getConnections()) {
            ServerConnection serverconnection = (ServerConnection) connection;
            serverconnection.sendTCP(message);
        }
    }

    // Notify all users, new user connected
    private void userLoggedOut(ServerConnection c) {
        UserMessage message = new UserMessage(Config.commands.userDisconnected, c.getUser());

        for (Connection connection : server.getConnections()) {
            ServerConnection serverconnection = (ServerConnection) connection;
            serverconnection.sendTCP(message);
        }
    }

}
