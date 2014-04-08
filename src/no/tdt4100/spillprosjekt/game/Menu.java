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

    private Animation banner;

    public static Image backgroundImage;
    public static Image backToMenu;
    public static Image backToMenuHower;
    public static MouseOverArea menuHower;

    private Image singlePlayerImage;
    private Image singlePlayerHowerImage;
    private Image multiPlayerImage;
    private Image multiPlayerHowerImage;

    public static Sound typeSoundGood;
    public static Sound typeSoundFail;
    public static Sound lockSound;
    public static Sound loseSound;
    public static Sound greySound;

    public static TiledMap typeMap;

    public static Animation loadingAnimation;

    private Image[] animationImage;
    private Image[] buttonHoverImages;
    private Image[] bannerAnimationImages;

    private MouseOverArea singlePlayerHower;
    private MouseOverArea multiPlayerHower;
    private MouseOverArea[] mouseOverAreas;

    public static TypeFont typeFont;
    public static TypeFont typeFontBig;

    private int buttonX;
    private int buttonY = 350;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        this.buttonX = container.getWidth() / 2;

        typeFont = new TypeFont("Consolas", 24, true, java.awt.Color.white);
        typeFontBig = new TypeFont("Consolas", 64, true, java.awt.Color.white);

        typeSoundGood = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/click.aif"));
        typeSoundFail = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/Bottle.aif"));
        lockSound = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/Tink.aif"));
        greySound = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/Frog.aif"));
        loseSound = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/Basso.aif"));

        typeMap = new TiledMap(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/TiledMap.tmx"), "no/tdt4100/spillprosjekt/res");

        backgroundImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/background.png"), "background.png", false);

        animationImage = new Image[] {
                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/anim1.png"), "anim1.png", false),
                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/anim2.png"), "anim2.png", false),
                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/anim3.png"), "anim3.png", false),
                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/anim4.png"), "anim4.png", false),
        };
        int[] duration = {150, 150, 150, 150};
        loadingAnimation = new Animation(animationImage, duration, true);

        bannerAnimationImages = new Image[] {
                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/bAnim0.png"), "bAnim0.png", false),
                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/bAnim5.png"), "bAnim5.png", false),
        };

        int[] bannerDuration = {500, 500};
        banner = new Animation(bannerAnimationImages, bannerDuration, true);

        backToMenu = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/menu.png"), "menu.png", false);
        backToMenuHower = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/menu_hower.png"), "menu_hower.png", false);

        menuHower = new MouseOverArea(container, backToMenu, (container.getWidth() / 2) - 96, 450);
        menuHower.setMouseOverImage(backToMenuHower);

        singlePlayerImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/single_player.png"), "single_player.png", false);
        singlePlayerHowerImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/single_player_hover.png"), "single_player_hover.png", false);
        multiPlayerImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/multi_player.png"), "multi_player.png", false);
        multiPlayerHowerImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/multi_player_hover.png"), "multi_player_hover.png", false);

        buttonHoverImages = new Image[] {singlePlayerHowerImage, multiPlayerHowerImage};

        singlePlayerHower = new MouseOverArea(container, singlePlayerImage, buttonX - 192, buttonY + 70);
        multiPlayerHower = new MouseOverArea(container, multiPlayerImage, buttonX - 176, buttonY);
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
        banner.draw((container.getWidth() / 2) - 146, 200);
    }

}
