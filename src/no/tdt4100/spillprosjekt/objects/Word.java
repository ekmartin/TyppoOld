package no.tdt4100.spillprosjekt.objects;

/**
 * Created by eiriksylliaas on 08.02.14.
 *
 * Words. Position and queue.
 */
public class Word {
    private String wordString;
    private float xPos;
    private colors color;

    public enum colors {
        BLACK, BLUE, ORANGE, RED, GREEN, PURPLE, TEAL
    }

    public Word(String wordString, float xPos, colors color) {
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

    public float getxPos() {
        return xPos;
    }
}
