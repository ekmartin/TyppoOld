package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Menu extends BasicGameState {

    public static final int ID = 1;
    private StateBasedGame stateGame;

    private Animation loading;

    private Image backgroundImage;
    private Image playGameImage;
    private Image playGameHoverImage;
    private Image joinGameImage;
    private Image joinGameHoverImage;

    private Image[] animationImage;
    private Image[] buttonImages;
    private Image[] buttonHoverImages;

    private MouseOverArea playGameHover;
    private MouseOverArea joinGameHover;
    private MouseOverArea[] mouseOverAreas;

    private TypeFont typeFont;

    private int buttonX = 83;
    private int playGameY = 150;
    private int joinGameY = 300;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        typeFont = new TypeFont("Verdana", 32, true, java.awt.Color.white);


        backgroundImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/background.png"), "background.png", false);
        playGameImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/play_game.png"), "play_game.png", false);
        playGameHoverImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/play_game_hover.png"), "play_game_hover.png", false);
        joinGameImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/join_game.png"), "join_game.png", false);
        joinGameHoverImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/join_game_hover.png"), "join_game_hover.png", false);

        animationImage = new Image[] {
                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/anim1.png"), "anim1.png", false),
                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/anim2.png"), "anim2.png", false),
                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/anim3.png"), "anim3.png", false),
                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/anim4.png"), "anim4.png", false),
        };
        int[] duration = {150, 150, 150, 150};

        loading = new Animation(animationImage, duration, true);

        buttonImages = new Image[] {playGameImage, joinGameImage};
        buttonHoverImages = new Image[] {playGameHoverImage, joinGameHoverImage};

        playGameHover = new MouseOverArea(container, playGameImage, buttonX, playGameY);
        joinGameHover = new MouseOverArea(container, joinGameImage, buttonX, joinGameY);
        mouseOverAreas = new MouseOverArea[] {playGameHover, joinGameHover};
        for (int i = 0; i < mouseOverAreas.length; i++) {
            mouseOverAreas[i].setMouseOverImage(buttonHoverImages[i]);
        }

    }

    public int getID() {
        return ID;
    }

    public void update(GameContainer container, StateBasedGame stateGame, int delta) throws SlickException {
        if (LoadingList.get().getRemainingResources() > 0) {
            loading.draw();
        }
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
                stateGame.enterState(6, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            else if (joinGameHover.isMouseOver()) {
                stateGame.enterState(5, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        // temporary buttons.
        g.drawImage(backgroundImage, 0, 0);
        for (int i = 0; i < mouseOverAreas.length; i++) {
            mouseOverAreas[i].render(container, g);
        }
        loading.draw(200, 500);

    }

}
