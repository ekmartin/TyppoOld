package no.tdt4100.spillprosjekt.objects;

/**
 * Created by eiriksylliaas on 10.02.14.
 */
public class JoinGameRequest {


    private Game game;


    public JoinGameRequest() {}

    public JoinGameRequest(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


}
