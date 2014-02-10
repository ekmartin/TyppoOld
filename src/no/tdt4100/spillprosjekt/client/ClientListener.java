package no.tdt4100.spillprosjekt.client;

import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.objects.UserList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eiriksylliaas on 09.02.14.
 */
public interface ClientListener {

    public void userList(UserList users);

    public void userLoggedIn(User user);

    public void userLoggedOut(User user);

}
