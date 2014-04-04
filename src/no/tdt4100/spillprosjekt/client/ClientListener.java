package no.tdt4100.spillprosjekt.client;

import no.tdt4100.spillprosjekt.objects.Game;
import no.tdt4100.spillprosjekt.objects.OpenGames;
import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.objects.UserList;

import java.util.ArrayList;
import java.util.List;

public interface ClientListener {

    public void userList(UserList users);

    public void userLoggedIn(User user);

    public void userLoggedOut(User user);

    public void receiveNewGame(Game game);

    public void receiveOpenGames (OpenGames openGames);

    public void sendClientCommand(SendObject sendObject);
}
