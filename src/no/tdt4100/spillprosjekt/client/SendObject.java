package no.tdt4100.spillprosjekt.client;

import no.tdt4100.spillprosjekt.objects.WordList;

/**
 * Created by ek on 04/04/14.
 */
public class SendObject {

    Type type;

    public enum Type {
        grey, lost, wordlist, findGame, foundGame, test
    };

    private WordList wordList;

    public SendObject(Type type) {
        this.type = type;
    }

    public SendObject(WordList wordList) {
        this.type = Type.foundGame;
        this.wordList = wordList;
    }

    public Type getType() {
        return type;
    }

    public WordList getWordlist() {
        return wordList;
    }
}
