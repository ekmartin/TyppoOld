package no.tdt4100.spillprosjekt.game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TypeGameGUI extends StateBasedGame {

    public static void main (String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new TypeGameGUI());
            app.setTitle("Typpo?!");
            app.setDisplayMode(480, 640, false);
            app.setTargetFrameRate(200);
            app.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }


    public TypeGameGUI() {
        super("TypeGameGUI");
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new Menu());
        addState(new MultiPlayerGUI());
        addState(new SinglePlayerGUI());
        addState(new FinishedScreen());
        addState(new OpponentLeft());
    }
}
