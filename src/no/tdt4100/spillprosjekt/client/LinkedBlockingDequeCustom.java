package no.tdt4100.spillprosjekt.client;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by eiriksylliaas on 04.04.14.
 */
public class LinkedBlockingDequeCustom<E> extends LinkedBlockingDeque<E> {

    GameListener client;

    public void setListener(GameListener client) {
        this.client = client;
    }


    @Override
    public boolean add(E e) {
        super.add(e);
        SendObject sendObject;
        if (e instanceof SendObject) {
            sendObject = (SendObject) e;
            client.sendClientCommand(sendObject);
        }
        else {
            throw new IllegalArgumentException();
        }

        return true;
    }


}
