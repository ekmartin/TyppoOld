package no.tdt4100.spillprosjekt.game;

/**
 * Created by ek on 07/04/14.
 */
public class Score {

    private static int score;
    private static int charScore = 1;
    private static int wordScore = 5;
    private static boolean wonGame;
    private static boolean multiplayer;
    private static int multiplier;

    public static void newGame(boolean multiplayer) {
        wonGame = false;
        score = 0;
        multiplier = 1;
        Score.multiplayer = multiplayer;
    }

    public static void minusCharScore() {
        score -= charScore;
    }

    public static void charScore() {
        score += (charScore*multiplier);
    }

    public static void wordScore() {
        score += (wordScore*multiplier);
    }

    public static int getScore() {
        return Math.abs(score);
    }

    public static void won() {
        wonGame = true;
    }

    public static boolean getStatus() {
        return wonGame;
    }

    public static boolean isMultiplayer() {
        return multiplayer;
    }

    public static int getMultiplier() {
        return multiplier;
    }

    public static void upMultiplier() {
        multiplier++;
    }

    public static void resetMultiplier() {
        multiplier = 1;
    }
}
