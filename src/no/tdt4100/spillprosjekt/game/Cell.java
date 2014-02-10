package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.Word;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Created by ek on 09/02/14.
 */
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

        cellImage = new Image("locked.png");
    }

    public Cell(char letter, Word.colors color, int xPos, int yPos) throws SlickException {
        faded = false;
        locked = false;
        this.letter = letter;
        this.color = color;

        this.xPos = xPos;
        this.yPos = yPos;

        switch (color) {
            case BLUE:
                cellImage = new Image("bluecell.png");
                break;
            case RED:
                cellImage = new Image("redcell.png");
                break;
            case GREEN:
                cellImage = new Image("greencell.png");
                break;
            case TEAL:
                cellImage = new Image("tealcell.png");
                break;
            case PURPLE:
                cellImage = new Image("purplecell.png");
                break;
            case ORANGE:
                cellImage = new Image("orangecell.png");
                break;
            default:
                cellImage = new Image("bluecell.png");
                break;
        }
    }

    public void dropCell(boolean[][] blocked) {
        System.out.println("yPos: " + yPos + " xPos: " + xPos);
        if (blocked[yPos + 1][xPos] == false) {
            yPos++;
            if (isLocked() == true) {
                System.out.println("Got into locked cell " + blocked.length);
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

    public void fade() throws SlickException {
        switch (color) {
            case BLUE:
                cellImage = new Image("bluefaded.png");
                break;
            case RED:
                cellImage = new Image("redfaded.png");
                break;
            case GREEN:
                cellImage = new Image("yellowfaded.png");
                break;
            case PURPLE:
                cellImage = new Image("purplefaded.png");
                break;
            case TEAL:
                cellImage = new Image("tealfaded.png");
                break;
            case ORANGE:
                cellImage = new Image("orangefaded.png");
                break;
            default:
                cellImage = new Image("bluefaded.png");
                break;
        }
        faded = true;
    }

    public void lock() {
        letter = '\0';
        locked = true;
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
            yPos++;
            return false;
        }
    }
}
