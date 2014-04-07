package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.client.GameListener;
import no.tdt4100.spillprosjekt.client.LinkedBlockingDequeCustom;
import no.tdt4100.spillprosjekt.client.SendObject;
import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Scanner;

public class SinglePlayerGUI extends BasicGameState {

    private TypeGame game;
    private WordList wordList;

    private int runTime;
    private int failCounter;
    private boolean foundGame;

    public static final int ID = 3;
    private StateBasedGame stateGame;

    public static int score = 0;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame stateGame) throws SlickException {
        super.enter(container, stateGame);

        score = 0;
        runTime = 0;
        failCounter = 0;
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
        game = new TypeGame(wordList);

    }

    public int getID() {
        return ID;
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ENTER) {
            game.addDead();
        } else {
            boolean wrote = false;
            c = Character.toLowerCase(c);
            ArrayList<AllowedCharacter> allowedChars = new ArrayList<AllowedCharacter>();
            System.out.println("New key pressed, currently writing: " + game.hasStartedWriting());
            if (game.hasStartedWriting() && !game.getCurrentBlock().isLocked()) {
                for (Cell cell : game.getCurrentBlock().getCells()) {
                    if (!cell.isFaded()) {
                        allowedChars.add(new AllowedCharacter(Character.toLowerCase(cell.getLetter()), game.getCurrentBlock()));
                        break;
                    }
                }
            } else {
                System.out.println("kom inn i else");
                for (Block block : game.getBlocks()) {
                    allowedChars.add(new AllowedCharacter(Character.toLowerCase(block.getCells().get(0).getLetter()), block));
                }
            }

            for (AllowedCharacter allowed : allowedChars) {
                System.out.println("Allowed char: " + allowed.getChar());
                if (allowed.getChar() == c) {
                    wrote = true;
                    failCounter = 0;
                    Menu.typeSoundGood.play(); // temp sound, should be replaced
                    System.out.println("fading next, which is: " + allowed.getChar());
                    game.startedWriting(allowed.getBlock());
                    game.fadeNext();
                    break;
                }
            }

            if (!wrote) {
                failCounter++;
                Menu.typeSoundFail.play();
                if (failCounter >= 5) {
                    game.addDead();
                    failCounter = 0;
                    // should play another sound here
                }
            }
        }
    }

    public void update(GameContainer container, StateBasedGame stateGame, int delta) throws SlickException {
        runTime += delta;
        int n = runTime / game.getDelay();

        if (game.isLost()) {
            Menu.loseSound.play();
            System.out.println("Game lost.");
            score = -game.getScore();
            stateGame.enterState(5, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }

        if (runTime > game.getDelay()) {
            for (int i = 0; i < n; i++) {
                game.dropBlocks();
            }
            runTime = 0;
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        Menu.typeMap.render(0, 0);
        game.render(g);
    }

}
