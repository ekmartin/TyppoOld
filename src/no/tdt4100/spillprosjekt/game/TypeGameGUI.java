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
    Font font;
    UnicodeFont uFont;
    private TiledMap typeMap = null;
    Rectangle testRect;
    private float x = 32;
    private float y = 32;
    TextField text;
    public TypeGameGUI() {
        super("TypeGame game");
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
        font = new Font("Verdana", Font.BOLD, 32);
        uFont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
        uFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        uFont.addNeheGlyphs();
        uFont.loadGlyphs();
        typeMap = new TiledMap("data/TypeMap.tmx", true);
        testRect = new Rectangle(32,32, 64, 32);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Input input = container.getInput();

        if (input.isKeyDown(input.KEY_DOWN)) {
            y += 5;
            testRect.setY(testRect.getY() + 5);
        }
        else if (input.isKeyDown(input.KEY_UP)) {
            y -= 5;
            testRect.setY(testRect.getY() - 5);
        }
        else if (input.isKeyDown(input.KEY_RIGHT)) {
            x += 5;
            testRect.setX(testRect.getX() + 5);
        }
        else if (input.isKeyDown(input.KEY_LEFT)) {
            x -= 5;
            testRect.setX(testRect.getX() - 5);
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        typeMap.render(0, 0);
        g.setColor(new Color(100, 100, 100));
        g.fill(testRect);
        g.draw(testRect);
        g.setColor(new Color(0, 0, 0));
        g.setFont(uFont);
        g.drawString("T E", x, y);
    }

}
