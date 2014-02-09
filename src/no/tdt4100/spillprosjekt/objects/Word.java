package no.tdt4100.spillprosjekt.objects;

/**
 * Created by eiriksylliaas on 08.02.14.
 *
 * Words. Position and queue.
 */
public class Word {
    private String wordString;
    private int xPos;
    private colors color;

    public enum colors {
        BLUE, GREEN, LOCKED, ORANGE, RED, PURPLE, TEAL, YELLOW
    }

    public Word(String wordString, int xPos, colors color) {
        this.wordString = wordString;
        this.xPos = xPos;
        this.color = color;
    }

    public colors getColor() {
        return color;
    }

    public String getWordString() {
        return wordString;
    }

    public int getX() {
        return xPos;
    }
}
