package no.tdt4100.spillprosjekt.client;

import no.tdt4100.spillprosjekt.objects.Game;
import no.tdt4100.spillprosjekt.objects.OpenGames;
import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.objects.UserList;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by eiriksylliaas on 04.04.14.
 */
public class GameListener implements ClientListener, Runnable {

    GameClient client;

    protected BlockingDeque clientDeque;
    protected BlockingDeque serverDeque;

    public GameListener(BlockingDeque clientDeque, BlockingDeque serverDeque){

        this.clientDeque = clientDeque;
        this.serverDeque = serverDeque;

        client = new GameClient() ;
        client.addListener(this);
        client.connect("Test1");
        client.createGame();

    }


    public void userList(UserList users) {

    }

    public void userLoggedIn(User user) {

    }

    public void userLoggedOut(User user) {

    }

    public void receiveNewGame(Game game) {

    }

    public void receiveOpenGames(OpenGames openGames) {

    }

    public void sendClientCommand(SendObject sendObject) {
        System.out.println("GOT IT BIATCH " + sendObject);
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("her: " + clientDeque.poll());
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
