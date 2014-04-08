package no.tdt4100.spillprosjekt.game;

import no.tdt4100.spillprosjekt.client.GameListener;
import no.tdt4100.spillprosjekt.client.LinkedBlockingDequeCustom;
import no.tdt4100.spillprosjekt.client.SendObject;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;

public class MultiPlayerGUI extends BasicGameState {

    private TypeGame game;

    private int countDown;
    private int countDownTimer;
    private int runTime;
    private int failCounter;
    private int successfulWordCounter;
    private boolean foundGame;

    public static final int ID = 2;
    private StateBasedGame stateGame;

    private LinkedBlockingDequeCustom serverDeque;

    private GameListener gameListener;

    private TypeFont smallFont;
    private TypeFont largeFont;

    private int dots;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        foundGame = false;

        smallFont = new TypeFont("Consolas", 30, true, java.awt.Color.white);
        largeFont = new TypeFont("Consolas", 40, true, java.awt.Color.white);

        try {
            serverDeque = new LinkedBlockingDequeCustom<SendObject>();
            gameListener = new GameListener(serverDeque);

            serverDeque.setListener(gameListener, this);

            new Thread(gameListener).start();

        }
        catch (Exception e) {
            Logger.log(e);
        }
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);

        countDownTimer = 0;
        countDown = 3;
        dots = 0;
        runTime = 0;
        failCounter = 0;
        successfulWordCounter = 0;
        foundGame = false;

        SendObject sendObject = new SendObject(Config.commands.findGame);
        serverDeque.sendToServer(sendObject);
    }

    public int getID() {
        return ID;
    }

    @Override
    public void keyPressed(int key, char c) {
        if (foundGame) {
            if (key == Input.KEY_ENTER) {
                if (game.hasStartedWriting() && !game.getCurrentBlock().isLocked()) {
                    game.destroyFadedAndLock(game.getCurrentBlock());
                }
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
                        if (game.fadeNext()) {
                            successfulWordCounter++;
                            if (successfulWordCounter >= 5) {
                                serverDeque.sendToServer(new SendObject(Config.commands.gray));
                                successfulWordCounter = 0;
                            }
                        }
                        break;
                    }
                }

                if (!wrote) {
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

    public void doNextAction() {
        while (!serverDeque.isEmpty()) {
            Object nextActionObject = serverDeque.peekFirst();
            if (nextActionObject != null) {
                SendObject nextAction = (SendObject) serverDeque.poll();
                switch (nextAction.getType()) {
                    case gray:
                        if (foundGame) game.addDead();
                        break;
                    case won:
                        if (foundGame) gameWon();
                        break;
                    case foundGame:
                        runTime = 0;
                        game = new TypeGame(nextAction.getWordlist());
                        Score.newGame();
                        break;
                    case startMultiplayerGame:
                        foundGame = true;
                        break;
                    case opponentLeft:
                        stateGame.enterState(5, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                        break;
                    default:
                        System.out.println("Unkown SendObject-type: " + nextAction.getType());
                }
            }
        }
    }

    public void gameWon() {
        Score.won();
        stateGame.enterState(4, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
    }

    public void update(GameContainer container, StateBasedGame stateGame, int delta) throws SlickException {
        doNextAction();
        if (foundGame) {
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
                    System.out.println("Game lost.");
                    serverDeque.sendToServer(new SendObject(Config.commands.lost));
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
        else {
            runTime += delta;
            if (runTime > 500) {
                if (dots < 3) dots++;
                else dots = 1;
                runTime = 0;
            }
        }
    }

    public void render(GameContainer container, StateBasedGame stateGame, Graphics g) throws SlickException {
        Menu.typeMap.render(0, 0);
        if (foundGame) {
            game.render(g);
        }
        else {
            g.setFont(smallFont.getFont());
            String drawString = "Searching for players";
            for (int i = 0; i < dots; i++) {
                drawString += ".";
            }
            g.drawString(drawString, 100, container.getHeight()/2 - 50);
            Menu.loadingAnimation.draw(container.getWidth()/2 - Menu.loadingAnimation.getWidth()/2,
                                       container.getHeight()/2 - Menu.loadingAnimation.getHeight()/2 + 50);
        }

        if (countDown > 0) {
            g.setFont(smallFont.getFont());
            g.drawString("Type the falling words!", 65, 200);
            g.setFont(largeFont.getFont());
            g.drawString("" + countDown, container.getWidth()/2 - 10, 300);
        }
    }

}
