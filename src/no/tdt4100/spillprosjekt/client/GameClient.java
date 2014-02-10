package no.tdt4100.spillprosjekt.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.objects.UserList;
import no.tdt4100.spillprosjekt.objects.UserMessage;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;
import no.tdt4100.spillprosjekt.utils.ServerInit;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eiriksylliaas on 09.02.14.
 *
 * Game Client
 */
public class GameClient {

    // Define the client object
    Client client = new Client();

    ArrayList<ClientListener> listeners = new ArrayList<ClientListener>();

    // Connect to server with username
    public void connect(String username) {

        // Register objects
        ServerInit.mapClasses(client.getKryo());

        // Start client
        client.start();

        // Connect
        try {
            client.connect(5000, Config.ServerAddress, Config.ServerTCPPort, Config.ServerUDPPort);
            // Send user
            User me = new User();
            me.setName(username);
            client.sendTCP(me);

            // Get UserList
            client.sendTCP(Config.commands.getUserList  );

        }
        catch (Exception e) {
            Logger.log(e);
        }


        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof UserList) {

                    UserList users = (UserList) object;

                    for (ClientListener cl : listeners){
                        cl.userList(users);
                    }

                }

                if (object instanceof UserMessage) {

                    UserMessage message = (UserMessage) object;

                    switch (message.command) {
                        case userConnected: {
                            for (ClientListener cl : listeners){
                                cl.userLoggedIn(message.user);
                            }
                            break;
                        }
                        case userDisconnected: {
                            for (ClientListener cl : listeners){
                                cl.userLoggedOut(message.user);
                            }
                            break;
                        }
                    }

                }
            }
        });

    }


    public void addListener(ClientListener toAdd) {
        listeners.add(toAdd);
    }


}
