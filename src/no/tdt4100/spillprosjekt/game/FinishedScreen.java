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

    private TypeFont typeFont;
    private TypeFont typeFontBig;

    private Image backgroundImage;
    private Image backToMenu;
    private Image backToMenuHower;

    private MouseOverArea menuHower;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        typeFont = new TypeFont("Consolas", 32, true, java.awt.Color.white);
        typeFontBig = new TypeFont("Consolas", 64, true, java.awt.Color.white);

        backgroundImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/Background.png"), "Background.png", false);

        backToMenu = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/menu.png"), "menu.png", false);
        backToMenuHower = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/menu_hower.png"), "menu_hower.png", false);

        menuHower = new MouseOverArea(container, backToMenu, (container.getWidth() / 2) - 96, 450);
        menuHower.setMouseOverImage(backToMenuHower);
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
        if (button == 0 && menuHower.isMouseOver()) {
            stateGame.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        g.drawImage(backgroundImage, 0, 0);
        g.setFont(typeFont.getFont());
        menuHower.render(container, g);
        if (Score.getStatus()) {
            g.setFont(typeFontBig.getFont());
            g.drawString("YOU WON!", 90, 100);

            g.setFont(typeFont.getFont());
            g.drawString("Your score: " + Score.getScore(), 125, 180);
        }
        else {
            g.setFont(typeFontBig.getFont());
            g.drawString("YOU LOST!", 90, 100);

            g.setFont(typeFont.getFont());
            g.drawString("Your score: " + Score.getScore(), 125, 180);
        }
    }

}
