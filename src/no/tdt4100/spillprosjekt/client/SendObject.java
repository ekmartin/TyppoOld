package no.tdt4100.spillprosjekt.client;

/**
 * Created by ek on 04/04/14.
 */
public class SendObject {

    private String type;
    private String[] wordlist;

    public SendObject(String type) {
        this.type = type;
    }

    public SendObject(String[] wordlist) {
        this.type = "wordlist";
        this.wordlist = wordlist;
    }

    public String getType() {
        return type;
    }

    public String[] getWordlist() {
        return wordlist;
    }
}
