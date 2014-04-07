package no.tdt4100.spillprosjekt.game;

import org.newdawn.slick.Input;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class FinishedScreen extends BasicGameState {

    public static final int ID = 5;
    private StateBasedGame stateGame;

    private TypeFont typeFont;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        typeFont = new TypeFont("Verdana", 18, true, java.awt.Color.white);
    }

    public int getID() {
        return ID;
    }

    public void update(GameContainer container, StateBasedGame stateGame, int delta) throws SlickException {
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            stateGame.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        g.setFont(typeFont.getFont());
        if (MultiPlayerGUI.score > 0) {
            g.drawString("Congratulations! Score: " + MultiPlayerGUI.score, 80, container.getHeight()/2);
        }
        else {
            g.drawString("You reached " + MultiPlayerGUI.score + " points before losing.", 80, container.getHeight() / 2);
        }
    }

}
