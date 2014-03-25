package no.tdt4100.spillprosjekt.test;

import no.tdt4100.spillprosjekt.client.ClientListener;
import no.tdt4100.spillprosjekt.client.GameClient;
import no.tdt4100.spillprosjekt.objects.Game;
import no.tdt4100.spillprosjekt.objects.OpenGames;
import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.objects.UserList;

/**
 * Created by eiriksylliaas on 09.02.14.
 */

import java.io.*;

class TestClient implements Runnable, ClientListener {


    static BufferedReader in ;
    static int quit=0;
    GameClient client;


    public void run(){

        client = new GameClient() ;

        client.addListener(this);

        client.connect("Test1");

        client.createGame();

    }


    public static void main(String args[]) throws Exception{

        Thread t1=new Thread(new TestClient());
        t1.start();
        while(true){
            t1.sleep(10);
        }

    }



    @Override
    public void userList(UserList users) {

    }

    @Override
    public void userLoggedIn(User user) {

        System.out.println(user);

    }

    @Override
    public void userLoggedOut(User user) {

    }

    @Override
    public void receiveNewGame(Game game) {

    }

    @Override
    public void receiveOpenGames(OpenGames openGames) {

    }
}