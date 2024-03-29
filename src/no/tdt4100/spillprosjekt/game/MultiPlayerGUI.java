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
    private int totalSuccessfulWordCounter;
    private boolean foundGame;

    private boolean connected;

    public static final int ID = 2;
    private StateBasedGame stateGame;

    private LinkedBlockingDequeCustom serverDeque;

    private GameListener gameListener;

    private TypeFont scoreFont;
    private TypeFont smallFont;
    private TypeFont largeFont;

    private int dots;

    @Override
    public void init(GameContainer container, StateBasedGame stateGame) throws SlickException {
        this.stateGame = stateGame;

        foundGame = false;
        connected = false;

        scoreFont = new TypeFont("Consolas", 20, true, java.awt.Color.lightGray);
        smallFont = new TypeFont("Consolas", 25, true, java.awt.Color.white);
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
        totalSuccessfulWordCounter = 0;
        foundGame = false;

        if (connected) {
            SendObject sendObject = new SendObject(Config.commands.findGame);
            serverDeque.sendToServer(sendObject);
        }
    }

    public int getID() {
        return ID;
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            if (foundGame) {
                serverDeque.sendToServer(new SendObject(Config.commands.lost));
                Menu.loseSound.play();
                stateGame.enterState(4, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            else {
                serverDeque.sendToServer(new SendObject(Config.commands.deleteMyGames));
                stateGame.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
        else if (foundGame) {
            if (key == Input.KEY_ENTER) {
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
                            Menu.typeSoundGood.play(); // temp sound, should be replaced
                            game.startedWriting(allowed.getBlock());
                            if (game.fadeNext()) {
                                successfulWordCounter++;
                                totalSuccessfulWordCounter++;
                                if (successfulWordCounter >= 5) {
                                    serverDeque.sendToServer(new SendObject(Config.commands.gray));
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
                        Score.resetMultiplier();
                        if (failCounter >= 5) {
                            game.addDead();
                            failCounter = 0;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if (button == 0 && Menu.menuHower.isMouseOver() && !foundGame) {
            serverDeque.sendToServer(new SendObject(Config.commands.deleteMyGames));
            stateGame.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
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
                        Score.newGame(true);
                        break;
                    case startMultiplayerGame:
                        foundGame = true;
                        break;
                    case opponentLeft:
                        stateGame.enterState(5, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                        break;
                    case connectionStatus:
                        connected = nextAction.getConnectionStatus();
                        if (connected && !foundGame) {
                            SendObject sendObject = new SendObject(Config.commands.findGame);
                            serverDeque.sendToServer(sendObject);
                        }
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
            if (connected) {
                if (countDown > 0) {
                    countDownTimer += delta;
                    if (countDownTimer > 1000) {
                        countDown--;
                        countDownTimer = 0;
                    }
                } else {
                    runTime += delta;
                    int n = runTime / game.getDelay();

                    if (game.isLost()) {
                        Menu.loseSound.play();
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
                stateGame.enterState(6, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
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
            if (countDown > 0) {
                g.setFont(smallFont.getFont());
                g.drawString("Type the falling words!", 80, 200);
                g.setFont(largeFont.getFont());
                g.drawString("" + countDown, container.getWidth()/2 - 10, 300);
            }
            game.render(g);
            String successString = "" + totalSuccessfulWordCounter;
            String multiplierString = Score.getMultiplier() + "x";
            g.setFont(scoreFont.getFont());
            g.drawString(successString, Config.cellWidth*1.3f, Config.boardHeightFloat-32);
            g.drawString(multiplierString,
                    container.getWidth() - (Config.cellWidth*2) + 5,
                    Config.boardHeightFloat-32);
        }
        else {
            g.setFont(smallFont.getFont());
            String drawString;
            if (connected) {
                drawString = "Searching for players";
            }
            else {
                drawString = "Trying to connect";
            }
            for (int i = 0; i < dots; i++) {
                drawString += ".";
            }
            g.drawString(drawString, 100, container.getHeight()/2 - 50);
            Menu.menuHower.render(container, g);
            Menu.loadingAnimation.draw(container.getWidth()/2 - Menu.loadingAnimation.getWidth()/2,
                    container.getHeight()/2 - Menu.loadingAnimation.getHeight()/2 + 50);
        }

    }

}
