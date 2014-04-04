package no.tdt4100.spillprosjekt.test;

import com.esotericsoftware.kryonet.Client;
import no.tdt4100.spillprosjekt.client.ClientListener;
import no.tdt4100.spillprosjekt.client.GameClient;
import no.tdt4100.spillprosjekt.client.SendObject;
import no.tdt4100.spillprosjekt.objects.Game;
import no.tdt4100.spillprosjekt.objects.OpenGames;
import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.objects.UserList;

/**
 * Created by eiriksylliaas on 09.02.14.
 */
public class ClientListenerHandler implements ClientListener {

    private GameClient client;

    public ClientListenerHandler (GameClient client) {
        this.client = client;
    }

    @Override
    public void userList(UserList users) {

        for (User user : users.userList) {
            System.out.println(user.getName());
        }

    }

    @Override
    public void userLoggedIn(User user) {
        System.out.println("Logged inn: " + user.getName());
    }

    @Override
    public void userLoggedOut(User user) {
        System.out.println("Logged out: " + user.getName());
    }

    @Override
    public void receiveNewGame(Game game) {
        System.out.println("Received new game: " + game.getCreator().getName() + Boolean.toString(game.getRunning()));

        client.joinGame(game);

    }

    @Override
    public void receiveOpenGames(OpenGames openGames) {
        System.out.println("Received open games:");
        for (Game game : openGames.openGames) {
            System.out.println(game.getCreator().getName() + Boolean.toString(game.getRunning()));
        }
    }

    @Override
    public void sendClientCommand(SendObject sendObject) {

    }


}
