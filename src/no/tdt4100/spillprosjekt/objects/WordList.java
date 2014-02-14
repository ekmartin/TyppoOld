package no.tdt4100.spillprosjekt.objects;

import no.tdt4100.spillprosjekt.utils.Config;

import java.util.Random;

/**
 * The word list each user receives.
 */
public class WordList {

    private Word[] wordList;
    private Random randomGenerator = new Random();

    public WordList(String[] words, int length) {

       // Generate randorm wordlist for a game.
       // Words, position and queue

       this.wordList = new Word[length];

       for (int i = 0; i < length; i++) {

           int wordIndex = randomGenerator.nextInt(words.length);
           String word = words[wordIndex];

           int positionRange = (Config.boardWidth) - word.length();
           int position = randomGenerator.nextInt(positionRange + 1);
           Word.colors color = Word.colors.values()[(int) randomGenerator.nextInt(Word.colors.values().length)];
           Word wordObject = new Word(word, position, color);

           this.wordList[i] = wordObject;

       }

    }

    public WordList() {}

    public Word[] getWordList() {
        return this.wordList;
    }

}
