package no.tdt4100.spillprosjekt.game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by ek on 09/02/14.
 */
public class Cell extends Image {
    private char letter;
    private Block.colors color;
    private Image cellImage;
    private boolean faded;

    public Cell(char letter, Block.colors color) throws SlickException {
        faded = false;
        this.letter = letter;
        this.color = color;
        switch (color) {
            case BLUE:
                cellImage = new Image("data/bluecell.png");
                break;
            case RED:
                cellImage = new Image("data/redcell.png");
                break;
            case YELLOW:
                cellImage = new Image("data/yellowcell.png");
                break;
            case ORANGE:
                cellImage = new Image("data/orangecell.png");
                break;
            default:
                cellImage = new Image("data/bluecell.png");
                break;
        }
    }

    public Image getCellImage() {
        return cellImage;
    }

    public char getLetter() {
        return letter;
    }

    public void fade() throws SlickException {
        switch (color) {
            case BLUE:
                cellImage = new Image("data/bluefaded.png");
                break;
            case RED:
                cellImage = new Image("data/redfaded.png");
                break;
            case YELLOW:
                cellImage = new Image("data/yellowfaded.png");
                break;
            case ORANGE:
                cellImage = new Image("data/orangefaded.png");
                break;
            default:
                cellImage = new Image("data/bluefaded.png");
                break;
        }
        faded = true;
    }

    public void lock() {
        letter = '\0';
    }

    public boolean isFaded() {
        return faded;
    }
}
