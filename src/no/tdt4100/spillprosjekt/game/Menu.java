package no.tdt4100.spillprosjekt.game;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

public class Menu extends BasicGameState {

    public static final int ID = 1;
    private StateBasedGame stateGame;


    public static Image backgroundImage;

    private Image singlePlayerImage;
    private Image singlePlayerHowerImage;
    private Image multiPlayerImage;
    private Image multiPlayerHowerImage;

    public static Sound typeSoundGood;
    public static Sound typeSoundFail;
    public static Sound lockSound;
    public static Sound loseSound;
    public static TiledMap typeMap;

    private Image[] buttonHoverImages;

    private MouseOverArea singlePlayerHower;
    private MouseOverArea multiPlayerHower;
    private MouseOverArea[] mouseOverAreas;

    private TypeFont typeFont;

    private int buttonX = 83;
    private int playGameY = 150;
    private int joinGameY = 300;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        typeFont = new TypeFont("Verdana", 32, true, java.awt.Color.white);

        typeSoundGood = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/click.aif"));
        typeSoundFail = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/Bottle.aif"));
        lockSound = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/Tink.aif"));
        loseSound = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/Basso.aif"));

        typeMap = new TiledMap(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/TiledMap.tmx"), Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/").getPath());

        backgroundImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/background.png"), "background.png", false);
        singlePlayerImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/single_player.png"), "single_player.png", false);
        singlePlayerHowerImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/single_player_hover.png"), "single_player_hover.png", false);
        multiPlayerImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/multi_player.png"), "multi_player.png", false);
        multiPlayerHowerImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/multi_player_hover.png"), "multi_player_hover.png", false);

        buttonHoverImages = new Image[] {singlePlayerHowerImage, multiPlayerHowerImage};

        singlePlayerHower = new MouseOverArea(container, singlePlayerImage, buttonX, playGameY);
        multiPlayerHower = new MouseOverArea(container, multiPlayerImage, buttonX, joinGameY);
        mouseOverAreas = new MouseOverArea[] {singlePlayerHower, multiPlayerHower};
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
            // quit
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if (button == 0) {
            if (singlePlayerHower.isMouseOver()) {
                System.out.println("starting single");
                stateGame.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            else if (multiPlayerHower.isMouseOver()) {
                stateGame.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

            }
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        // temporary buttons.
        g.drawImage(backgroundImage, 0, 0);
        for (int i = 0; i < mouseOverAreas.length; i++) {
            mouseOverAreas[i].render(container, g);
        }
    }

}
