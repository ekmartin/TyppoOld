package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.Word;
import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class TypeGame {

    private TypeFont typeFont;

    private int delay;
    private int addBlockTreshold;
    private int addBlockCounter;

    private boolean gameLost;

    private boolean[][] blocked;

    private Word[] wordList;
    private int wordListIndex;

    private int bottom;

    private boolean startedWriting;
    private Block currentBlock;

    private ArrayList<Block> blocks = new ArrayList<Block>();

    public TypeGame(WordList wordList) {
        Menu.song.loop();
        gameLost = false;

        typeFont = new TypeFont("Consolas", 20, true, java.awt.Color.lightGray);
        blocked = new boolean[Config.boardHeight + 1][Config.boardWidth];
        for (int i = 0; i < blocked[Config.boardHeight].length; i++) {
            blocked[Config.boardHeight][i] = true;
        }
        bottom = Config.boardHeight-1;

        delay = 1200;
        addBlockCounter = 3;
        addBlockTreshold = 3;

        this.wordList = wordList.getWordList();
        wordListIndex = 0;
    }

    public int getDelay() {
        return delay;
    }

    public boolean addBlock() {
        Block addBlock = new Block(wordList[wordListIndex]);
        for (Cell cell : addBlock.getCells()) {
            if (getBlocked()[cell.getY()][cell.getX()]) {
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
        if (delay > 300)
            delay -= 9;
        /*
        if (delay < 800) {
            addBlockTreshold = 1;
        }
        else if (delay < 1300) {
            addBlockTreshold = 2;
        }*/
        for (Block block : blocks) {
            if (!block.isLocked() && !block.isGrey())
                block.dropBlock(getBlocked());
        }
        if (addBlockCounter >= addBlockTreshold) {
            if (!addBlock())
                gameLost = true;
            addBlockCounter = 0;
        }
        addBlockCounter++;
    }

    public boolean addDead() {
        boolean returnVal = false;
        for (Block block : blocks) {
            System.out.println("locked, " + block.isLocked());
            if (block.isLocked()) {
                if (block.up())
                    returnVal = true;
            }
        }
        Menu.typeSoundFail.play();
        blocks.add(new Block(bottom));
        bottom--;
        return returnVal;
    }

    public void render(Graphics g) {
        for (Block block : blocks) {
            block.draw(g);
        }
        g.setFont(typeFont.getFont());
        String scoreString = Score.getScore() + "";
        g.drawString(scoreString, Config.boardWidthFloat/2-(scoreString.length()*6.3f), Config.boardHeightFloat-32);
    }

    public boolean[][] getBlocked() {
        for (Block block : blocks) {
            for (Cell cell : block.getCells()) {
                if (cell.isDead())
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

    public void destroyFadedAndLock(Block block) {
        startedWriting = false;
        block.destroyFadedAndLock();
    }

    public void unFadeBlock(Block block) {
        startedWriting = false;
        block.unFade();
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public boolean fadeNext() {
        if (startedWriting) {
            if (currentBlock.fadeNext()) {
                Score.wordScore();
                blocks.remove(currentBlock);
                startedWriting = false;
                return true;
            }
            else {
                Score.charScore();
            }
        }
        return false;
    }

    public int getBottom() {
        return bottom;
    }

    public boolean isLost() {
        return gameLost;
    }
}
