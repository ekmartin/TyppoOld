package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.Word;
import no.tdt4100.spillprosjekt.utils.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

public class Cell {
    private char letter;
    private Word.colors color;
    private Image cellImage;

    private boolean faded;
    private boolean locked;
    private boolean grey;

    private int xPos;
    private int yPos;
    private float xPosFloat;
    private float yPosFloat;

    public Cell(int xPos, int yPos) throws SlickException {
        grey = true;
        this.letter = '\0';

        this.xPos = xPos;
        this.yPos = yPos;

        cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/locked.png"), "locked.png", false);
    }

    public Cell(char letter, Word.colors color, int xPos, int yPos) {
        grey = false;
        faded = false;
        locked = false;
        this.letter = letter;
        this.color = color;

        this.xPos = xPos;
        this.yPos = yPos;
        try {
            switch (color) {
                case BLUE:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/bluecell.png"), "bluecell.png", false);
                    break;
                case RED:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/redcell.png"), "redcell.png", false);
                    break;
                case GREEN:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/greencell.png"), "greencell.png", false);
                    break;
                case TEAL:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/tealcell.png"), "tealcell.png", false);
                    break;
                case PURPLE:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/purplecell.png"), "purplecell.png", false);
                    break;
                case ORANGE:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/orangecell.png"), "orangecell.png", false);
                    break;
            }
        }
        catch (Exception e) {
            Logger.log(e);
        }
    }

    public void dropCell(boolean[][] blocked) {
        if (blocked[yPos + 1][xPos] == false) {
            yPos++;
            if (isLocked() == true) {
                dropCell(blocked);
            }
        }
    }

    public Image getCellImage() {
        return cellImage;
    }

    public char getLetter() {
        return letter;
    }

    public void unFade() {
        try {
            switch (color) {
                case BLUE:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/bluecell.png"), "bluecell.png", false);
                    break;
                case RED:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/redcell.png"), "redcell.png", false);
                    break;
                case GREEN:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/greencell.png"), "greencell.png", false);
                    break;
                case TEAL:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/tealcell.png"), "tealcell.png", false);
                    break;
                case PURPLE:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/purplecell.png"), "purplecell.png", false);
                    break;
                case ORANGE:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/orangecell.png"), "orangecell.png", false);
                    break;
            }
        }
        catch (Exception e) {
            Logger.log(e);
        }
    }
    public void fade() {
        try {
            switch (color) {
                case BLUE:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/bluefaded.png"), "bluefaded.png", false);
                    break;
                case RED:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/redfaded.png"), "redfaded.png", false);
                    break;
                case GREEN:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/greenfaded.png"), "greenfaded.png", false);
                    break;
                case TEAL:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/tealfaded.png"), "tealfaded.png", false);
                    break;
                case PURPLE:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/purplefaded.png"), "purplefaded.png", false);
                    break;
                case ORANGE:
                    cellImage = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/orangefaded.png"), "orangefaded.png", false);
                    break;
            }
        }
        catch (Exception e) {
            Logger.log(e);
        }
        faded = true;
    }

    public void lock() {
        unFade();
        letter = '\0';
        locked = true;
    }

    public boolean isDead() {
        return locked || grey;
    }
    public  boolean isGrey() {
        return grey;
    }

    public boolean isFaded() {
        return faded;
    }

    public boolean isLocked() {
        return locked;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public boolean up() {
        if (yPos == 0) {
            return true;
        }
        else {
            yPos--;
            return false;
        }
    }
}
