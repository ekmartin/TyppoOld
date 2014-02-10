package no.tdt4100.spillprosjekt.objects;

/**
 * Created by eiriksylliaas on 09.02.14.
 *
 * Created by user. Send username to server.
 */
public class User {

    private String name;
    private int uid;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUID() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
