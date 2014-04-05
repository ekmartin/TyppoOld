package no.tdt4100.spillprosjekt.client;

/**
 * Created by ek on 04/04/14.
 */
public class SendObject {

    Type type;

    public enum Type {
        grey, lost, wordlist, findGame, foundGame, test
    };

    private String[] wordlist;

    public SendObject(Type type) {
        this.type = type;
    }

    public SendObject(String[] wordlist) {

        this.wordlist = wordlist;
    }

    public Type getType() {
        return type;
    }

    public String[] getWordlist() {
        return wordlist;
    }
}
