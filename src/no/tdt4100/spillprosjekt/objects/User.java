package no.tdt4100.spillprosjekt.objects;

/**
 * Created by eiriksylliaas on 09.02.14.
 *
 * Created by user. Send username to server.
 */
public class User {

    private String name;

    public User (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
