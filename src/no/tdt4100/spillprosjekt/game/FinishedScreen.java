package no.tdt4100.spillprosjekt.game;


import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Color;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class FinishedScreen extends BasicGameState {

    public static final int ID = 4;
    private StateBasedGame stateGame;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;
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

    @Override
    public void mousePressed(int button, int x, int y) {
        if (button == 0 && Menu.menuHower.isMouseOver()) {
            stateGame.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        g.drawImage(Menu.backgroundImage, 0, 0);

        g.setFont(Menu.typeFontBig.getFont());
        if (Score.getStatus()) {
            g.drawString("YOU WON!", 100, 100);
            g.drawString("" + Score.getScore(), 220 - (String.valueOf(Score.getScore()).length() - 1) * 16, container.getHeight() / 2);
        }
        else if (Score.isMultiplayer()) {
                g.drawString("YOU LOST!", 90, 100);
        }
        else {
                g.drawString("DONE!", 155, 100);
        }
        g.drawString("" + Score.getScore(), 220 - (String.valueOf(Score.getScore()).length() - 1) * 16, container.getHeight() / 2);

        g.setFont(Menu.typeFont.getFont());
        g.drawString("Your score:", 170, 250);

        Menu.menuHower.render(container, g);
    }

}
