package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.utils.Config;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by ek on 09/02/14.
 */
public class Block {

    public enum colors {
        YELLOW, RED, BLUE, ORANGE
    }

    private ArrayList<Cell> cells = new ArrayList<Cell>();
    private String word;
    private colors color;

    private float xPos;
    private float yPos;

    public void removeFaded() {
        ArrayList<Cell> rest = new ArrayList<Cell>();
        for (Cell cell : cells) {
            if (!cell.isFaded()) {
                rest.add(cell);
            }
        }
        cells = rest;
    }

    public boolean dropBlock() {
        yPos += Config.cellHeight;
        return yPos == Config.boardHeight;
    }

    public void draw(Graphics g) {
        for (Cell cell : cells) {

        }
    }
}
