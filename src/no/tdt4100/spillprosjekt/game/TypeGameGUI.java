package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.WordList;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by eiriksylliaas on 08.02.14.
 */
public class TypeGameGUI extends BasicGame {

    private TypeGame game;
    private WordList wordList;
    private TiledMap typeMap = null;
    private int runTime;
    private int counter;

    public TypeGameGUI() {
        super("TypeGameGUI game");
    }

    public static void main (String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new TypeGameGUI());
            app.setDisplayMode(480, 640, false);
            app.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        runTime = 0;
        counter = 0;
        String[] words = {"HEI", "HEI2", "HEI3"};
        wordList = new WordList(words, 500);
        game = new TypeGame(wordList);
        typeMap = new TiledMap("/TypeMap.tmx", true);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        runTime += delta;
        int n = runTime / game.getDelay();
        //System.out.println("N: " + n + " runTime: " + runTime + " speed: "+ game.getDelay());
        if (runTime > game.getDelay()) {
            for (int i = 0; i < n; i++) {
                game.dropBlocks();
                System.out.println("Adding/dropping blocks, delay: " + game.getDelay());
            }
            runTime = 0;
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        typeMap.render(0, 0);
        game.render(g);
    }

}
