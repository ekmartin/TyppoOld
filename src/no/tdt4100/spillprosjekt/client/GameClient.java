package no.tdt4100.spillprosjekt.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;

/**
 * Created by eiriksylliaas on 09.02.14.
 */
public class GameClient {

    Client client = new Client();

    public void connect(String username) {

        Kryo kryo = client.getKryo();
        kryo.register(User.class);

        client.start();
        try {
            client.connect(5000, Config.ServerAddress, Config.ServerTCPPort, Config.ServerUDPPort);
            User me = new User();

            me.setName(username);

            client.sendTCP(me);
        }
        catch (Exception e) {
            Logger.Log(e);
        }
    }


}
