package no.tdt4100.spillprosjekt.client;

/**
 * Created by ek on 04/04/14.
 */
public class SendObject {

    private String type;
    private String data;

    public SendObject(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
