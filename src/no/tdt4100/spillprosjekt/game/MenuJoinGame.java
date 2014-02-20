package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MenuJoinGame extends BasicGameState {

    public static final int ID = 5;
    private StateBasedGame stateGame;


    private Image backgroundImage;
    private Image joinGameImage;
    private Image joinGameHoverImage;

    private Image[] buttonImages;
    private Image[] buttonHoverImages;

    private MouseOverArea joinGameHover;
    private MouseOverArea[] mouseOverAreas;

    private TextField field;
    private TypeFont typeFont;

    private int buttonX = 83;
    private int fieldY = 250;
    private int joinGameY = 350;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        typeFont = new TypeFont("Myriad Pro", 40, true, new java.awt.Color(28, 28, 31));

        backgroundImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/background.png"), "background.png", false);
        joinGameImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/join_game.png"), "join_game.png", false);
        joinGameHoverImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/join_game_hover.png"), "join_game_hover.png", false);

        buttonImages = new Image[] {joinGameImage};
        buttonHoverImages = new Image[] {joinGameHoverImage};

        field = new TextField(container, typeFont.getFont(), buttonX, fieldY, joinGameImage.getWidth(), 40);

        field.setBackgroundColor(Color.white);
        joinGameHover = new MouseOverArea(container, joinGameImage, buttonX, joinGameY);
        mouseOverAreas = new MouseOverArea[] {joinGameHover};
        for (int i = 0; i < mouseOverAreas.length; i++) {
            mouseOverAreas[i].setMouseOverImage(buttonHoverImages[i]);
        }

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
        if (button == 0) {
            if (joinGameHover.isMouseOver()) {
                // do shit with field.getText()
                System.out.println("Nick: " + field.getText());
                stateGame.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        // temporary buttons.
        g.drawImage(backgroundImage, 0, 0);
        field.setCursorVisible(true);

        field.render(container, g);

        for (int i = 0; i < mouseOverAreas.length; i++) {
            mouseOverAreas[i].render(container, g);
        }
    }

}
