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

    private boolean[][] blocked;

    private Word[] wordList;
    private int wordListIndex;

    private int bottom;

    private boolean startedWriting;
    private Block currentBlock;

    private ArrayList<Block> blocks = new ArrayList<Block>();

    public TypeGame(WordList wordList) {
        blocked = new boolean[Config.boardHeight + 1][Config.boardWidth];
        for (int i = 0; i < blocked[Config.boardHeight].length; i++) {
            blocked[Config.boardHeight][i] = true;
        }
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

    public void gameLost() {
        // do stuff here
        TypeGameGUI.loseSound.play();
        System.out.println("2bad4u");
    }

    public boolean addBlock() {
        Block addBlock = new Block(wordList[wordListIndex]);
        for (Cell cell : addBlock.getCells()) {
            if (blocked[cell.getY()][cell.getX()]) {
                return false;
            }
        }
        blocks.add(addBlock);
        if (wordListIndex < wordList.length - 1) {
            wordListIndex++;
        }
        else {
            wordListIndex = 0;
        }
        return true;
    }

    public void dropBlocks() {
        // TODO: Fix delay/treshold, should use something like (the derivative) of 1200 / (1 + ℯ^b x) + 300 ((-89.1283/Math.pow((0.0742736*counter)+1, 2)))
        delay -= 10;
        if (delay < 800) {
            addBlockTreshold = 1;
        }
        else if (delay < 1300) {
            addBlockTreshold = 2;
        }
        for (Block block : blocks) {
            if (!block.isLocked())
                block.dropBlock(getBlocked());
        }
        if (addBlockCounter >= addBlockTreshold) {
            if (!addBlock())
                gameLost();
            addBlockCounter = 0;
        }
        addBlockCounter++;
    }

    public boolean addDead() {
        boolean returnVal = false;
        for (Block block : blocks) {
            if (block.up())
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
                if (cell.isLocked())
                    blocked[cell.getY()][cell.getX()] = true;
            }
        }
        return blocked;
    }

    public boolean hasStartedWriting() {
        return startedWriting;
    }

    public void startedWriting(Block currentBlock) {
        startedWriting = true;
        this.currentBlock = currentBlock;
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void fadeNext() {
        if (startedWriting) {
            System.out.println("fading next!!!");
            if (currentBlock.fadeNext()) {
                System.out.println("Deleting block, cuz yolo. ");
                blocks.remove(currentBlock);
                startedWriting = false;
            }
        }
    }
}
