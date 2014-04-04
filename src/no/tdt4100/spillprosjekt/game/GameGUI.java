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

public class GameGUI extends BasicGameState {

    private TypeGame game;
    private WordList wordList;
    private TiledMap typeMap = null;
    private int runTime;
    private int failCounter;
    private int successfulWordCounter;

    public static final int ID = 2;
    private StateBasedGame stateGame;

    public static Sound typeSoundGood;
    public static Sound typeSoundFail;
    public static Sound loseSound;
    public static Sound lockSound;

    private LinkedBlockingDequeCustom serverDeque;
    private LinkedBlockingDequeCustom clientDeque;

    private GameListener gameListener;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        failCounter = 0;
        successfulWordCounter = 0;

        this.stateGame = stateGame;
        runTime = 0;
        ArrayList<String> words = new ArrayList<String>();
        String[] wordsArray;

        try {
            typeSoundGood = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/click.aif"));
            typeSoundFail = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/Bottle.aif"));
            lockSound = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/Tink.aif"));
            loseSound = new Sound(Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/Basso.aif"));
            Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/wordlist.txt"));
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
            scanner.close();
            wordsArray = words.toArray(new String[0]);
            Config.wordlist = wordsArray;

            serverDeque = new LinkedBlockingDequeCustom<SendObject>();
            clientDeque = new LinkedBlockingDequeCustom<SendObject>();
            gameListener = new GameListener(clientDeque, serverDeque);

            serverDeque.setListener(gameListener);
            clientDeque.setListener(gameListener);

            new Thread(gameListener).start();

        }
        catch (Exception e) {
            Logger.log(e);
        }
        wordList = new WordList(Config.wordlist, 500);
        game = new TypeGame(wordList);
        typeMap = new TiledMap(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/TiledMap.tmx"), Thread.currentThread().getContextClassLoader().getResource("no/tdt4100/spillprosjekt/res/").getPath());
    }

    public int getID() {
        return ID;
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            stateGame.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        else if (key == Input.KEY_ENTER) {
            game.addDead();
        }
        else {
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
            }
            else {
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
                    typeSoundGood.play(); // temp sound, should be replaced
                    System.out.println("fading next, which is: " + allowed.getChar());
                    game.startedWriting(allowed.getBlock());
                    if (game.fadeNext()) {
                        successfulWordCounter++;
                        if (successfulWordCounter >= 5) {
                            serverDeque.add(new SendObject("grey"));
                            successfulWordCounter = 0;
                        }
                    }
                    break;
                }
            }

            if (!wrote) {
                failCounter++;
                typeSoundFail.play();
                if (failCounter >= 5) {
                    game.addDead();
                    failCounter = 0;
                    // should play another sound here
                }
            }
        }
    }

    public void doNextAction() {
        SendObject nextAction;

        if (serverDeque.getFirst() instanceof SendObject) {
            nextAction = (SendObject) serverDeque.poll();
            switch (nextAction.getType()) {
                case "wordlist":
                    Config.wordlist = nextAction.getWordlist();
                    break;
                case "grey":
                    game.addDead();
                    break;
                case "lost":
                    gameWon();
                    break;
                default:
                    System.out.println("Unkown SendObject type");
            }
        }
        else {
            //TODO: Need to handle this.
            throw new IllegalArgumentException();
        }
    }

    public void gameWon() {
        //TODO: Add victory state, stats etc.
        System.out.println("Won, FIXME");
        stateGame.enterState(4, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
    }

    public void update(GameContainer container, StateBasedGame stateGame, int delta) throws SlickException {
        clientDeque.add(new SendObject("grey"));
        runTime += delta;
        int n = runTime / game.getDelay();


        if (runTime > game.getDelay()) {
            for (int i = 0; i < n; i++) {
                game.dropBlocks();
            }
            runTime = 0;
        }
        if (game.isLost()) {
            loseSound.play();
            System.out.println("Game lost.");
            serverDeque.add("lost");
            stateGame.enterState(4, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        typeMap.render(0, 0);
        game.render(g);
    }

}
