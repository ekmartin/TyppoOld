package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.Word;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ek on 09/02/14.
 */
public class Block {

    private ArrayList<Cell> cells = new ArrayList<Cell>();
    private String wordString;
    private Word.colors color;

    private Font font;
    private UnicodeFont uFont;

    private boolean locked;

    private int xPos;
    private int yPos;

    public Block (int yPos) {
        this.xPos = 0;
        this.yPos = yPos;
        this.color = Word.colors.LOCKED;
        locked = true;
        for (int i = 0; i < Config.boardWidth; i++) {
            try {
                cells.add(new Cell('\0', Word.colors.LOCKED, xPos + i, yPos));
            }
            catch (Exception e) {
                Logger.log(e);
            }
        }
    }

    public Block (Word word) {
        try {
            font = new Font("Verdana", Font.BOLD, 32);
            uFont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
            uFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            uFont.addNeheGlyphs();
            uFont.loadGlyphs();
        }
        catch (Exception e) {
            Logger.log(e);
        }

        this.wordString = word.getWordString();
        this.color = word.getColor();

        locked = false;

        this.xPos = word.getX();
        this.yPos = 0;

        for (int i = 0; i < wordString.length(); i++) {
            try {
                cells.add(new Cell(wordString.charAt(i), color, xPos + i, yPos));
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

    public void lockBlock() {
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).isFaded()) {
                cells.remove(i);
            }
            else {
                cells.get(i).lock();
            }
        }

    }

    public void dropBlock(boolean[][] blocked) {
        for (Cell cell : cells) {
            if (blocked[cell.getY()+1][cell.getX() + 1] == true) {
                for (Cell cell2 : cells) {
                    locked = true;
                    cell.lock();
                    cell.dropCell(blocked);
                }
                break;
            }
            else {
                cell.dropCell(blocked);
            }
        }

    }

    public boolean isLocked() {
        return locked;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void draw(Graphics g) {
        g.setFont(uFont);
        for (Cell cell : cells) {
            g.drawImage(cell.getCellImage(), cell.getX() * Config.cellWidth, cell.getY() * Config.cellHeight);
            g.drawString(String.valueOf(cell.getLetter()), cell.getX() * Config.cellWidth, cell.getY() * Config.cellHeight);
        }
    }

    public boolean up() {
        for (Cell cell : cells) {
            if (cell.up() == true)
                return true;
        }
        return false;
    }
}
