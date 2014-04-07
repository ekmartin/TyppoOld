package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.Word;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class Block {

    private ArrayList<Cell> cells = new ArrayList<Cell>();
    private String wordString;
    private Word.colors color;

    private TypeFont typeFont;

    private boolean locked;
    private boolean grey;

    private int fadeIndex;

    public Block (int y) {
        grey = true;
        locked = false;

        typeFont = new TypeFont("Verdana", 32, true, java.awt.Color.white);

        for (int i = 0; i < Config.boardWidth; i++) {
            try {
                cells.add(new Cell(i, y));
            }
            catch (Exception e) {
                Logger.log(e);
            }
        }
    }

    public Block (Word word) {
        this.wordString = word.getWordString();
        this.color = word.getColor();

        locked = false;

        int x = word.getX();

        typeFont = new TypeFont("Verdana", 32, true, java.awt.Color.white);

        for (int i = 0; i < wordString.length(); i++) {
            try {
                cells.add(new Cell(wordString.charAt(i), color, x + i, 0));
            }
            catch (Exception e) {
                Logger.log(e);
            }
        }
    }

    public void removeFaded() {
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).isFaded()) {
                cells.remove(i);
            }
        }
    }

    public void dropBlock(boolean[][] blocked) {
        for (Cell cell : cells) {
            cell.dropCell(blocked);
            if (blocked[cell.getY()+1][cell.getX()] == true) {
                Menu.lockSound.play();
                locked = true;
                for (Cell cell2 : cells) {
                    cell2.lock();
                    cell2.dropCell(blocked);
                }
                break;
            }
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isGrey() {
        return grey;
    }

    public boolean isDead() {
        return locked || grey;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void draw(Graphics g) {
        g.setFont(typeFont.getFont());

        for (Cell cell : cells) {
            g.drawImage(cell.getCellImage(), (cell.getX() + 1) * Config.cellWidth, cell.getY() * Config.cellHeight);
            g.drawString(String.valueOf(cell.getLetter()), ((cell.getX() + 1) * Config.cellWidth)+3, (cell.getY() * Config.cellHeight)-4);
        }
    }

    public int getY() {
        // This is pointless to look at if the block is locked, as the cells might be split over multiple rows.
        return cells.get(0).getY();
    }

    public boolean up() {
        for (Cell cell : cells) {
            if (cell.up() == true)
                return true;
        }
        return false;
    }

    public boolean fadeNext() {
        try {
            System.out.println("Fading: " + cells.get(fadeIndex).getLetter());
            cells.get(fadeIndex).fade();
            fadeIndex++;
        }
        catch (Exception e) {
            Logger.log(e);
        }
        return fadeIndex == wordString.length();
    }
}
