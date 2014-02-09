package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.Word;
import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by ek on 09/02/14.
 */
public class TypeGame {

    private int delay;
    private int addBlockTreshold;
    private int addBlockCounter;

    public boolean[][] blocked;

    private Word[] wordList;
    private int wordListIndex;

    private int bottom;

    private ArrayList<Block> blocks = new ArrayList<Block>();

    public TypeGame(WordList wordList) {
        blocked = new boolean[Config.boardHeight][Config.boardWidth];

        bottom = Config.boardHeight;

        delay = 1500;
        addBlockCounter = 3;
        addBlockTreshold = 3;

        this.wordList = wordList.getWordList();
        wordListIndex = 0;
    }

    public int getDelay() {
        return delay;
    }

    public void addBlock() {
        //delay += (-89.1283/Math.pow((0.0742736*counter)+1, 2));
        blocks.add(new Block(wordList[wordListIndex]));
        if (wordListIndex < wordList.length - 1) {
            wordListIndex++;
        }
        else {
            wordListIndex = 0;
        }
    }

    public void dropBlocks() {
        // TODO: Fix delay/treshold, should use something like (the derivative) of 1200 / (1 + ℯ^b x) + 300 ((-89.1283/Math.pow((0.0742736*counter)+1, 2)))
        delay -= 50;
        if (delay < 800) {
            addBlockTreshold = 1;
        }
        else if (delay < 1300) {
            addBlockTreshold = 2;
        }
        System.out.println("Add: " + addBlockCounter + " : " + addBlockTreshold);
        if (addBlockCounter >= addBlockTreshold) {
            System.out.println("Adding block.");
            addBlock();
            addBlockCounter = 0;
        }
        addBlockCounter++;
        for (Block block : blocks) {
            if (block.isLocked() == false)
                block.dropBlock(blocked);
        }
    }

    public boolean addDead() {
        boolean returnVal = false;
        for (Block block : blocks) {
            if (block.up() == true)
                returnVal = true;
        }
        blocks.add(new Block(bottom));
        bottom--;
        return returnVal;
    }

    public void render(Graphics g) {
        for (Block block : blocks) {
            block.draw(g);
        }
    }

    public boolean[][] getBlocked() {
        for (Block block : blocks) {
            for (Cell cell : block.getCells()) {
                if (cell.isLocked() == true)
                    blocked[(int) cell.getY() / 32][(int) cell.getX() / 32] = true;
            }
        }
        return blocked;
    }
}
