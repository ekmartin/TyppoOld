package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Menu extends BasicGameState {

    public static final int ID = 1;
    private StateBasedGame stateGame;


    private Image playGameImage;
    private Image playGameHoverImage;
    private Image highscoresImage;
    private Image highscoresHoverImage;
    private Image quitImage;
    private Image quitHoverImage;
    private Image[] buttonImages;
    private Image[] buttonHoverImages;

    private MouseOverArea playGameHover;
    private MouseOverArea highscoresHover;
    private MouseOverArea quitHover;
    private MouseOverArea[] mouseOverAreas;

    private TypeFont typeFont;

    private int buttonX = 75;
    private int playGameY = 150;
    private int highscoresY = 250;
    private int quitY = 350;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        playGameImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/play_game.png"), "play_game.png", false);
        playGameHoverImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/play_game_hover.png"), "play_game_hover.png", false);
        highscoresImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/highscores.png"), "highscores.png", false);
        highscoresHoverImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/highscores_hover.png"), "highscores_hover.png", false);
        quitImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/quit.png"), "quit.png", false);
        quitHoverImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/quit_hover.png"), "quit_hover.png", false);
        buttonImages = new Image[] {playGameImage, highscoresImage, quitImage};
        buttonHoverImages = new Image[] {playGameHoverImage, highscoresHoverImage, quitHoverImage};

        playGameHover = new MouseOverArea(container, playGameImage, buttonX, playGameY);
        highscoresHover = new MouseOverArea(container, highscoresImage, buttonX, highscoresY);
        quitHover = new MouseOverArea(container, quitImage, buttonX, quitY);
        mouseOverAreas = new MouseOverArea[] {playGameHover, highscoresHover, quitHover};
        for (int i = 0; i < mouseOverAreas.length; i++) {
            mouseOverAreas[i].setMouseOverImage(buttonHoverImages[i]);
        }

        typeFont = new TypeFont("Verdana", 32, true, java.awt.Color.white);
    }

    public int getID() {
        return ID;
    }

    public void update(GameContainer container, StateBasedGame stateGame, int delta) throws SlickException {
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            // quit
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if (button == 0) {
            if (playGameHover.isMouseOver()) {
                stateGame.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            else if (highscoresHover.isMouseOver()) {
                // enter highscore state
            }
            else if (quitHover.isMouseOver()) {
                // quit
            }
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        // temporary buttons.
        g.fillRect(0, 0, container.getWidth(), container.getHeight());
        for (int i = 0; i < mouseOverAreas.length; i++) {
            mouseOverAreas[i].render(container, g);
        }
    }

}
