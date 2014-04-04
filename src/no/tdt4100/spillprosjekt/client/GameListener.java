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
public class GameListener implements ClientListener {

    GameClient client;

    protected BlockingDeque client_deque;
    protected BlockingDeque game_deque;

    public GameListener(BlockingDeque client_deque, BlockingDeque game_deque){

        client = new GameClient() ;
        client.addListener(this);
        client.connect("Test1");
        client.createGame();

    }


    @Override
    public void userList(UserList users) {

    }

    @Override
    public void userLoggedIn(User user) {

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
