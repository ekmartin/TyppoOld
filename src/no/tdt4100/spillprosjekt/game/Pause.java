package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.Font;

public class Pause extends BasicGameState {

    public static final int ID = 3;
    private StateBasedGame stateGame;

    private String alphabet = "abcdefghijklmnopqrstuvwxyzæøå";

    private Font font;
    private UnicodeFont uFont;

    public static Sound typeSoundGood;
    public static Sound typeSoundFail;
    public static Sound loseSound;
    public static Sound lockSound;


    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        font = new Font("Verdana", java.awt.Font.BOLD, 24);
        uFont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
        uFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        uFont.addGlyphs(alphabet.toUpperCase() + alphabet + "()"); // Hacky shite, need to find out what part of the charset to add instead (range).
        uFont.loadGlyphs();
    }

    public int getID() {
        return ID;
    }

    public void update(GameContainer container, StateBasedGame stateGame, int delta) throws SlickException {
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            stateGame.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        g.setFont(uFont);
        g.drawString("Press Escape to Resume", 80, container.getHeight()/2);
    }

}
