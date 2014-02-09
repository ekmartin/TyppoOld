package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.Word;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by ek on 09/02/14.
 */
public class Cell {
    private char letter;
    private Word.colors color;
    private Image cellImage;
    private boolean faded;
    private boolean locked;
    private boolean black;

    public Cell(char letter, Word.colors color) throws SlickException {
        faded = false;
        locked = false;
        black = false;
        this.letter = letter;
        this.color = color;
        switch (color) {
            case BLUE:
                cellImage = new Image("data/bluecell.png");
                break;
            case RED:
                cellImage = new Image("data/redcell.png");
                break;
            case GREEN:
                cellImage = new Image("data/greencell.png");
                break;
            case TEAL:
                cellImage = new Image("data/tealcell.png");
                break;
            case PURPLE:
                cellImage = new Image("data/purplecell.png");
                break;
            case ORANGE:
                cellImage = new Image("data/orangecell.png");
                break;
            case BLACK:
                cellImage = new Image("data/blackcell.png");
                black = true;
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
            case GREEN:
                cellImage = new Image("data/yellowfaded.png");
                break;
            case PURPLE:
                cellImage = new Image("data/purplefaded.png");
                break;
            case TEAL:
                cellImage = new Image("data/tealfaded.png");
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
        locked = true;
        letter = '\0';
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isBlack() {
        return black;
    }

    public boolean isFaded() {
        return faded;
    }
}
