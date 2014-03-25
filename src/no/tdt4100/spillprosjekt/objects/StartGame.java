package no.tdt4100.spillprosjekt.objects;

/**
 * Created by eiriksylliaas on 17.02.14.
 */
public class StartGame {

    private Game game;


    public StartGame() {}

    public StartGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
