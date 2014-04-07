package no.tdt4100.spillprosjekt.game;

/**
 * Created by ek on 07/04/14.
 */
public class Score {

    private static int score;
    private static int charScore = 1;
    private static int wordScore = 5;
    private static boolean wonGame;

    public static void newGame() {
        score = 0;
    }

    public static void minusCharScore() {
        score -= charScore;
    }

    public static void charScore() {
        score += charScore;
    }

    public static void wordScore() {
        score += wordScore;
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
}
