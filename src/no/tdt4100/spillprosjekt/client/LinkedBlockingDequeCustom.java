package no.tdt4100.spillprosjekt.client;

import no.tdt4100.spillprosjekt.game.MultiPlayerGUI;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by eiriksylliaas on 04.04.14.
 */
public class LinkedBlockingDequeCustom<E> extends LinkedBlockingDeque<E> {

    GameListener client;
    MultiPlayerGUI gameGUI;

    public void setListener(GameListener client, MultiPlayerGUI gameGUI) {
        this.client = client;
        this.gameGUI = gameGUI;
    }

    public void sendToServer(SendObject sendObject) {
        client.sendClientCommand(sendObject);
    }

    @Override
    public boolean add(E e) {
        if (e instanceof SendObject) {
            super.add(e);
            return true;
        }
        // TODO: Add correct error handling and stuff
        System.out.println("Not sendObject (addFromGame) " + e);
        return false;
    }
}
