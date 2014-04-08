package no.tdt4100.spillprosjekt.client;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.ws.resources.SenderMessages;
import no.tdt4100.spillprosjekt.objects.*;
import no.tdt4100.spillprosjekt.utils.Config;

import java.net.InetAddress;

/**
 * Created by eiriksylliaas on 04.04.14.
 */
public class GameListener implements ClientListener, Runnable {

    GameClient client;

    private LinkedBlockingDequeCustom serverDeque;
    private String username = "";
    private boolean connectionState = false;

    public GameListener(LinkedBlockingDequeCustom serverDeque){

        this.serverDeque = serverDeque;

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
            SendObject sendObject = new SendObject(game.getWordList());
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
        if (this.connectionState) {
            switch (sendObject.getType()) {
                case gray:
                    client.sendGrayLine();
                    break;
                case lost:
                    client.sendLoss();
                    break;
                case findGame:
                    joinGameRequest();
                    break;
                case deleteMyGames:
                    client.deleteMyGames();
                    break;
            }
        }
    }

    @Override
    public void youWin() {
        serverDeque.add(new SendObject(Config.commands.won));
    }

    @Override
    public void addGrayLine() {
        serverDeque.add(new SendObject(Config.commands.gray));
    }

    @Override
    public void startMultiplayerGame() {
        serverDeque.add(new SendObject(Config.commands.startMultiplayerGame));
    }

    @Override
    public void opponentLeft() {
        serverDeque.add(new SendObject(Config.commands.opponentLeft));
    }

    @Override
    public void run() {

        serverDeque.add(new SendObject(false));

        connectToServer();

        while (true) {
            try {
                Thread.sleep(10);
                if (!client.client.isConnected()) {
                    connectToServer();
                }
                if (this.connectionState != client.client.isConnected()) {
                    serverDeque.add(new SendObject(client.client.isConnected()));
                }
                this.connectionState = client.client.isConnected();
            } catch (Exception e) {

            }

        }
    }


    private void joinGameRequest() {
        client.getOpenGames();
    }

    public void connectToServer() {
        client = new GameClient();
        client.addListener(this);
        client.connect(this.username);
    }

}

