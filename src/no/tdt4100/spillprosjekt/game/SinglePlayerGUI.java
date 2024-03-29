package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.Scanner;

public class SinglePlayerGUI extends BasicGameState {

    private TypeGame game;
    private WordList wordList;

    private TypeFont scoreFont;
    private TypeFont smallFont;
    private TypeFont largeFont;

    private int countDown;
    private int countDownTimer;
    private int runTime;
    private int failCounter;
    private int successfulWordCounter;
    private int totalSuccessfulWordCounter;

    private boolean foundGame;

    public static final int ID = 3;
    private StateBasedGame stateGame;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        scoreFont = new TypeFont("Consolas", 20, true, java.awt.Color.lightGray);
        smallFont = new TypeFont("Consolas", 25, true, java.awt.Color.white);
        largeFont = new TypeFont("Consolas", 40, true, java.awt.Color.white);

    }

    @Override
    public void enter(GameContainer container, StateBasedGame stateGame) throws SlickException {
        super.enter(container, stateGame);

        runTime = 0;
        failCounter = 0;
        countDown = 3;
        successfulWordCounter = 0;
        totalSuccessfulWordCounter = 0;
        foundGame = false;

        ArrayList<String> words = new ArrayList<String>();
        String[] wordsArray;

        try {
            Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/wordlist.txt"));
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
            scanner.close();
            wordsArray = words.toArray(new String[0]);
            Config.wordlist = wordsArray;

        }
        catch (Exception e) {
            Logger.log(e);
        }
        wordList = new WordList(Config.wordlist, 1000);
        Score.newGame(false);
        game = new TypeGame(wordList);

    }

    public int getID() {
        return ID;
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            Menu.loseSound.play();
            stateGame.enterState(4, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        else if (key == Input.KEY_ENTER) {
            if (game.hasStartedWriting() && !game.getCurrentBlock().isLocked()) {
               game.destroyFadedAndLock(game.getCurrentBlock());
            }
        }
        else {
            if (Character.isLetter(c)) {
                boolean wrote = false;
                c = Character.toLowerCase(c);
                ArrayList<AllowedCharacter> allowedChars = new ArrayList<AllowedCharacter>();
                if (game.hasStartedWriting() && !game.getCurrentBlock().isLocked()) {
                    for (Cell cell : game.getCurrentBlock().getCells()) {
                        if (!cell.isFaded()) {
                            allowedChars.add(new AllowedCharacter(Character.toLowerCase(cell.getLetter()), game.getCurrentBlock()));
                            break;
                        }
                    }
                } else {
                    for (Block block : game.getBlocks()) {
                        allowedChars.add(new AllowedCharacter(Character.toLowerCase(block.getCells().get(0).getLetter()), block));
                    }
                }

                for (AllowedCharacter allowed : allowedChars) {
                    if (allowed.getChar() == c) {
                        wrote = true;
                        failCounter = 0;
                        Menu.typeSoundGood.play();
                        game.startedWriting(allowed.getBlock());
                        if (game.fadeNext()) {
                            successfulWordCounter++;
                            totalSuccessfulWordCounter++;
                            if (successfulWordCounter >= 5) {
                                Score.upMultiplier();
                                successfulWordCounter = 0;
                            }
                        }
                        break;
                    }
                }

                if (!wrote) {
                    totalSuccessfulWordCounter = 0;
                    successfulWordCounter = 0;
                    failCounter++;
                    Menu.typeSoundFail.play();
                    if (failCounter >= 5) {
                        game.addDead();
                        failCounter = 0;
                    }
                }
            }
        }
    }

    public void update(GameContainer container, StateBasedGame stateGame, int delta) throws SlickException {
        if (countDown > 0) {
            countDownTimer += delta;
            if (countDownTimer > 1000) {
                countDown--;
                countDownTimer = 0;
            }
        }
        else {
            runTime += delta;
            int n = runTime / game.getDelay();

            if (game.isLost()) {
                Menu.loseSound.play();
                stateGame.enterState(4, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }

            if (runTime > game.getDelay()) {
                for (int i = 0; i < n; i++) {
                    game.dropBlocks();
                }
                runTime = 0;
            }
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        Menu.typeMap.render(0, 0);
        game.render(g);
        String successString = "" + totalSuccessfulWordCounter;
        String multiplierString = Score.getMultiplier() + "x";
        g.setFont(scoreFont.getFont());
        g.drawString(successString, Config.cellWidth*1.3f, Config.boardHeightFloat-32);
        g.drawString(multiplierString,
                container.getWidth() - (Config.cellWidth*2) + 5,
                Config.boardHeightFloat-32);

        if (countDown > 0) {
            g.setFont(smallFont.getFont());
            g.drawString("Type the falling words!", 80, 200);
            g.setFont(largeFont.getFont());
            g.drawString("" + countDown, container.getWidth()/2 - 10, 300);
        }
    }

}
