package no.tdt4100.spillprosjekt.client;

import no.tdt4100.spillprosjekt.objects.*;

import java.net.InetAddress;
import java.util.concurrent.BlockingDeque;

/**
 * Created by eiriksylliaas on 04.04.14.
 */
public class GameListener implements ClientListener, Runnable {

    GameClient client;

    private LinkedBlockingDequeCustom clientDeque;
    private LinkedBlockingDequeCustom serverDeque;
    private String username = "";

    public GameListener(LinkedBlockingDequeCustom clientDeque, LinkedBlockingDequeCustom serverDeque){

        this.clientDeque = clientDeque;
        this.serverDeque = serverDeque;

        client = new GameClient() ;
        client.addListener(this);

        try {
            this.username = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e) {
            this.username = "Unknown";
        }


    }


    public void userList(UserList users) {

    }

    public void userLoggedIn(User user) {
        System.out.println(user.getName() + " logged in");
    }

    public void userLoggedOut(User user) {
        System.out.println(user.getName() + " logged out");
    }

    public void receiveNewGame(Game game) {
        if (game.getRunning()) {
            System.out.println("STARTING SHITTTTTT");
            SendObject sendObject = new SendObject(SendObject.Type.foundGame);
            serverDeque.add(sendObject);
        }
    }

    public void receiveOpenGames(OpenGames openGames) {
        System.out.println("KOM TIL OPEN GAMES");
        if (openGames.openGames.size() == 0) {
            client.createGame();
        }
        else {
            client.joinGame(openGames.openGames.get(0));
        }
    }

    public void sendClientCommand(SendObject sendObject) {
        switch (sendObject.getType()) {
            case grey:
                client.sendGrayLine();
                break;
            case lost:
                client.sendLoss();
                break;
            case findGame:
                joinGameRequest();
                break;
        }
    }

    @Override
    public void run() {

        client.connect(this.username);

        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    private void joinGameRequest() {
        client.getOpenGames();
    }
}
