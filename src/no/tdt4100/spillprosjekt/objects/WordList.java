package no.tdt4100.spillprosjekt.objects;

import no.tdt4100.spillprosjekt.utils.Config;

import java.util.Random;

/**
 * Created by eiriksylliaas on 08.02.14.
 */
public class WordList {

    private WordSend[] wordList;
    private Random randomGenerator;

    public WordList(String[] words, int length) {

       // Generate randorm wordlist for a game.
       // Words, position and queue

       this.wordList = new WordSend[length];

       for (int i = 0; i < length; i++) {

           int wordIndex = randomGenerator.nextInt(words.length);
           String word = words[wordIndex];

           int positionRange = Config.BoardWidth - word.length();
           int position = randomGenerator.nextInt(positionRange);

           WordSend wordObject = new WordSend(word, position, i);

           this.wordList[i] = wordObject;

       }

    }

    public WordSend[] getWordList() {
        return this.wordList;
    }
}
