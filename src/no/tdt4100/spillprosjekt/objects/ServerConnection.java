package no.tdt4100.spillprosjekt.objects;

import com.esotericsoftware.kryonet.Connection;

/**
 * Created by eiriksylliaas on 09.02.14.
 */
public class ServerConnection extends Connection {

    public User user;


    public ServerConnection() {
        this.user = new User();
    }

    public User getUser(){
        return this.user;
    }


}
