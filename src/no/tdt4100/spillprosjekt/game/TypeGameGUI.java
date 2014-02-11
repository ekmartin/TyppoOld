package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.objects.WordList;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by eiriksylliaas on 08.02.14.
 */
public class TypeGameGUI extends BasicGame {

    private TypeGame game;
    private WordList wordList;
    private TiledMap typeMap = null;
    private int runTime;
    private int counter;

    public TypeGameGUI() {
        super("TypeGameGUI game");
    }

    public static void main (String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new TypeGameGUI());
            app.setDisplayMode(480, 640, false);
            app.setTargetFrameRate(200);
            app.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        runTime = 0;
        counter = 0;
        ArrayList<String> words = new ArrayList<String>();
        String[] wordsArray;
        try {
            Scanner scanner = new Scanner(new File("wordlist.txt"));
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
            scanner.close();
            wordsArray = words.toArray(new String[0]);
            Config.wordList = wordsArray;
        }
        catch (Exception e) {
            Logger.log(e);
        }
        wordList = new WordList(Config.wordList, 500);
        game = new TypeGame(wordList);
        typeMap = new TiledMap("/TypeMap.tmx", true);
    }

    @Override
    public void keyPressed(int key, char c) {
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
                System.out.println("fading next, which is: " + allowed.getChar());
                game.startedWriting(allowed.getBlock());
                game.fadeNext();
                break;
            }
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        runTime += delta;
        int n = runTime / game.getDelay();
        if (runTime > game.getDelay()) {
            for (int i = 0; i < n; i++) {
                game.dropBlocks();
            }
            runTime = 0;
        }

    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        typeMap.render(0, 0);
        game.render(g);
    }

}
