package no.tdt4100.spillprosjekt.client;

import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;

/**
 * Created by ek on 04/04/14.
 */
public class SendObject {

    Config.commands type;

    private WordList wordList;

    public SendObject(Config.commands type) {
        this.type = type;
    }

    public SendObject(WordList wordList) {
        this.type = Config.commands.foundGame;
        this.wordList = wordList;
    }

    public Config.commands getType() {
        return type;
    }

    public WordList getWordlist() {
        return wordList;
    }
}
