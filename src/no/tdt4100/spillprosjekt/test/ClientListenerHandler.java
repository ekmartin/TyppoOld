package no.tdt4100.spillprosjekt.test;

import no.tdt4100.spillprosjekt.client.ClientListener;
import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.objects.UserList;

/**
 * Created by eiriksylliaas on 09.02.14.
 */
public class ClientListenerHandler implements ClientListener {

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
}
