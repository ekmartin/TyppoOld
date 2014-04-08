package no.tdt4100.spillprosjekt.client;

import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;

/**
 * Created by ek on 04/04/14.
 */
public class SendObject {

    Config.commands type;

    private WordList wordList;
    private boolean connectionStatus = false;

    public SendObject(Config.commands type) {
        this.type = type;
    }

    public SendObject(WordList wordList) {
        this.type = Config.commands.foundGame;
        this.wordList = wordList;
    }

    public SendObject(boolean connectionStatus) {
        this.type = Config.commands.connectionStatus;
        this.connectionStatus = connectionStatus;
    }

    public Config.commands getType() {
        return type;
    }

    public WordList getWordlist() {
        return wordList;
    }

    public boolean getConnectionStatus() {
        return connectionStatus;
    }
}

