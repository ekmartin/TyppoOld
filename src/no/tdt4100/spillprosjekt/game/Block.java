package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.Word;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
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
    private float xPos;
    private float yPos;

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

        this.xPos = word.getxPos();
        this.yPos = 0;

        for (char letter : wordString.toCharArray()) {
            try {
                cells.add(new Cell(letter, color));
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
        for (Cell cell : cells) {
            cell.lock();
        }
    }

    public boolean dropBlock() {
        yPos += Config.cellHeight;
        return yPos == Config.boardHeight;
    }

    public void draw(Graphics g) {
        float x = xPos;
        g.setFont(uFont);
        for (Cell cell : cells) {
            g.drawImage(cell.getCellImage(), x, yPos);
            g.drawString(String.valueOf(cell.getLetter()), x, yPos);
            x += 32;
        }
    }
}
