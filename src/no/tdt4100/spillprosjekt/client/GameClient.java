package no.tdt4100.spillprosjekt.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import no.tdt4100.spillprosjekt.objects.*;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;
import no.tdt4100.spillprosjekt.utils.ServerInit;

import java.util.ArrayList;

/**
 * Game Client
 */
public class GameClient {

    // Define the client object
    Client client = new Client(40000, 40000);

    ArrayList<ClientListener> listeners = new ArrayList<ClientListener>();

    // Connect to server with username
    public boolean connect(String username) {

        // Register objects
        ServerInit.mapClasses(client.getKryo());

        // Start client
        client.start();


        // Connect
        try {
            client.connect(5000, Config.ServerAddress, Config.ServerTCPPort, Config.ServerUDPPort);
            // Send user
            User me = new User();
            me.setName(username);
            client.sendTCP(me);

            // Get UserList
            client.sendTCP(Config.commands.getUserList  );

        }
        catch (Exception e) {
            Logger.log(e);
        }


        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof UserList) {

                    UserList users = (UserList) object;

                    for (ClientListener cl : listeners){
                        cl.userList(users);
                    }

                }

                if (object instanceof UserMessage) {

                    UserMessage message = (UserMessage) object;

                    switch (message.command) {
                        case userConnected: {
                            for (ClientListener cl : listeners){
                                cl.userLoggedIn(message.user);
                            }
                            break;
                        }
                        case userDisconnected: {
                            for (ClientListener cl : listeners){
                                cl.userLoggedOut(message.user);
                            }
                            break;
                        }
                        case opponentLeft: {
                            for (ClientListener ci : listeners) {
                                ci.opponentLeft();
                            }
                            break;
                        }
                        case won: {
                            for (ClientListener ci : listeners) {
                                ci.youWin();
                            }
                            break;
                        }
                        case gray:{
                            for (ClientListener ci : listeners) {
                                ci.addGrayLine();
                            }
                            break;
                        }
                        case startMultiplayerGame:
                            for (ClientListener ci : listeners) {
                                ci.startMultiplayerGame();
                            }
                            break;
                    }

                }

                if (object instanceof Game) {
                    Game game = (Game) object;

                    for (ClientListener ci : listeners) {
                        ci.receiveNewGame(game);
                    }

                }

                if (object instanceof OpenGames) {
                    OpenGames openGames = (OpenGames) object;
                    for (ClientListener cl : listeners) {
                        cl.receiveOpenGames(openGames);
                    }
                }
            }
        });

        return client.isConnected();

    }

    public void addListener(ClientListener toAdd) {
        listeners.add(toAdd);
    }


    public void getUsers () {
        client.sendTCP(Config.commands.getUserList);
    }

    public void getOpenGames() {
        client.sendTCP(Config.commands.getOpenGames);
    }

    public void joinGame(Game game) {
        JoinGameRequest join = new JoinGameRequest(game);
        client.sendTCP(join);
    }

    public void createGame() {
        client.sendTCP(Config.commands.startGame);
    }



    public void sendGrayLine() {
        client.sendTCP(Config.commands.gray);
    }

    public void sendLoss() {
        client.sendTCP(Config.commands.lost);
    }

    public void deleteMyGames() {
        client.sendTCP(Config.commands.deleteMyGames);
    }

}
