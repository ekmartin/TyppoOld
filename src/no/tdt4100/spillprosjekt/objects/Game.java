package no.tdt4100.spillprosjekt.objects;

import no.tdt4100.spillprosjekt.utils.Config;

public class Game {

    private User creator;
    private User participant;

    private Boolean running;

    private WordList wordList;

    private int id;

    public Game(User creator, int id) {
        this.creator = creator;
        setParticipant(null);
        setRunning(false);
        this.wordList = new WordList(Config.wordlist, 1000);
        this.id = id;
    }

    public Game () {

    }


    public User getCreator() {
        return creator;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public WordList getWordList() {
        return wordList;
    }

    public int getID () {
        return this.id;
    }
}
