package no.tdt4100.spillprosjekt.objects;

import com.esotericsoftware.kryonet.Connection;

public class ServerConnection extends Connection {

    public User user;


    public ServerConnection() {
        this.user = new User();
    }

    public User getUser(){
        return this.user;
    }


}
