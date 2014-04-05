package no.tdt4100.spillprosjekt.client;

import no.tdt4100.spillprosjekt.objects.Game;
import no.tdt4100.spillprosjekt.objects.OpenGames;
import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.objects.UserList;

import java.net.InetAddress;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by eiriksylliaas on 04.04.14.
 */
public class GameListener implements ClientListener, Runnable {

    GameClient client;

    protected BlockingDeque clientDeque;
    protected BlockingDeque serverDeque;
    private String username = "";

    public GameListener(BlockingDeque clientDeque, BlockingDeque serverDeque){

        this.clientDeque = clientDeque;
        this.serverDeque = serverDeque;

        client = new GameClient() ;
        client.addListener(this);

        try {
            this.username = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e) {
            this.username = "Unknown";
        }
        client.connect(this.username);

    }


    public void userList(UserList users) {

    }

    public void userLoggedIn(User user) {
        System.out.println(user.getName() + " logged in");
    }

    public void userLoggedOut(User user) {
        System.out.println(user.getName() + " logged out");
    }

    public void receiveNewGame(Game game) {

    }

    public void receiveOpenGames(OpenGames openGames) {

    }

    public void sendClientCommand(SendObject sendObject) {
        switch (sendObject.getType()) {
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
