package no.tdt4100.spillprosjekt.test;

import no.tdt4100.spillprosjekt.client.GameClient;

/**
 * Created by eiriksylliaas on 09.02.14.
 */

import java.io.*;
import java.util.Scanner;

class TestClient implements Runnable{
    static BufferedReader in ;  static int quit=0;

    public void run(){

        try {

        GameClient client = new GameClient() ;

        ClientListenerHandler cl = new ClientListenerHandler(client);

        client.addListener(cl);

        Scanner scanner = new Scanner(System.in);

        client.connect(scanner.next());

        client.createGame();

        client.getOpenGames();

        } catch (Exception e) {

        System.out.println("System Client Error");

        }

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