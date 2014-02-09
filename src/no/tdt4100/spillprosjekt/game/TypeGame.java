package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.Word;
import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;

import java.util.ArrayList;

/**
 * Created by ek on 09/02/14.
 */
public class TypeGame {

    private float cellHeight;
    private float cellWidth;
    private int boardWidth;
    private int boardHeight;
    private float boardWidthFloat;
    private float boardHeightFloat;

    private Word[] wordList;
    private int wordListIndex;

    private ArrayList<Block> blocks = new ArrayList<Block>();

    public TypeGame(WordList wordList) {
        this.cellWidth = Config.cellWidth;
        this.cellHeight = Config.cellHeight;
        this.boardWidth = Config.boardWidth;
        this.boardHeight = Config.boardHeight;
        this.boardWidthFloat = Config.boardWidthFloat;
        this.boardHeightFloat = Config.boardHeightFloat;

        this.wordList = wordList.getWordList();
        this.wordListIndex = 0;
    }

    public void addBlock() {
        blocks.add(new Block(wordList[wordListIndex]));
        blocks.get(blocks.size());
        if (wordListIndex < wordList.length - 1) {
            wordListIndex++;
        }
        else {
            wordListIndex = 0;
        }
    }

    public void dropBlocks() {
        for (Block block : blocks) {
            if (block.dropBlock()) {
                block.lockBlock();
            }
        }
    }
}
