package no.tdt4100.spillprosjekt.client;

import no.tdt4100.spillprosjekt.game.GameGUI;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by eiriksylliaas on 04.04.14.
 */
public class LinkedBlockingDequeCustom<E> extends LinkedBlockingDeque<E> {

    GameListener client;
    GameGUI gameGUI;

    public void setListener(GameListener client, GameGUI gameGUI) {
        this.client = client;
        this.gameGUI = gameGUI;
    }

    public void addFromGame(E e) {
        super.add(e);
        if (e instanceof SendObject) {
            SendObject sendObject = (SendObject) e;
            client.sendClientCommand(sendObject);
        }
        else {
            // TODO: Add correct error handling and stuff
            System.out.println("Not sendObject (addFromGame) " + e);
        }
    }

    public void addFromClient(E e) {
        super.add(e);
        if (e instanceof SendObject) {
            SendObject sendObject = (SendObject) e;
            gameGUI.doNextAction();
        }
        else {
            // TODO: Add correct error handling and stuff
            System.out.println("Not sendObject (addFromGame) " + e);
        }
    }
}
