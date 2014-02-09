package no.tdt4100.spillprosjekt.game;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.tiled.TiledMap;
import java.awt.Font;
/**
 * Created by eiriksylliaas on 08.02.14.
 */
public class TypeGameGUI extends BasicGame {

    private TiledMap typeMap = null;

    public TypeGameGUI() {
        super("TypeGameGUI game");
    }

    public static void main (String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new TypeGameGUI());
            app.setDisplayMode(480, 640, false);
            app.setTargetFrameRate(60);
            app.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        typeMap = new TiledMap("data/TypeMap.tmx", true);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Input input = container.getInput();
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        typeMap.render(0, 0);
    }

}
