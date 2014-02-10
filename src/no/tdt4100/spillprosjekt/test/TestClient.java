package no.tdt4100.spillprosjekt.test;

import no.tdt4100.spillprosjekt.client.GameClient;

/**
 * Created by eiriksylliaas on 09.02.14.
 */

import java.io.*;

class TestClient implements Runnable{
    static BufferedReader in ;  static int quit=0;

    public void run(){
        GameClient client = new GameClient() ;

        ClientListenerHandler cl = new ClientListenerHandler();

        client.addListener(cl);

        client.connect("Test1");
    }


    public static void main(String args[]) throws Exception{
        in=new BufferedReader(new InputStreamReader(System.in));

        Thread t1=new Thread(new TestClient());
        t1.start();

        System.out.println("press Q THEN ENTER to terminate");

        while(true){
            t1.sleep(10);
            if(quit==1) break;
        }
    }
}