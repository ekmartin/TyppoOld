package no.tdt4100.spillprosjekt.server;

/**
 * Created by eiriksylliaas on 08.02.14.
 *
 * Game Server
 */

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import no.tdt4100.spillprosjekt.objects.*;
import no.tdt4100.spillprosjekt.utils.Config;
import no.tdt4100.spillprosjekt.utils.Logger;
import no.tdt4100.spillprosjekt.utils.ServerInit;

import java.util.ArrayList;
import java.util.Scanner;

public class GameServer {

    // Define server object
    Server server;
    ArrayList<Game> games = new ArrayList<Game>();
    int GameIDs = 0;


    // Start server, create new server object
    public static void main (String[] args) {
        ArrayList<String> words = new ArrayList<String>();
        String[] wordsArray;
        try {
            Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("no/tdt4100/spillprosjekt/res/wordlist.txt"));            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
            scanner.close();
            wordsArray = words.toArray(new String[0]);
            Config.wordlist = wordsArray;
        }
        catch (Exception e) {
            Logger.log(e);
        }
        GameServer gameserver = new GameServer();
        gameserver.start();
    }



    // Start Server
    public void start() {

        server = new Server(40000, 40000){
            protected Connection newConnection () {
                return new ServerConnection();
            }
        };
        ServerInit.mapClasses(server.getKryo());


        // Start server
        server.start();

        // Bind to ports
        // TCP, UDP
        try {
            server.bind(Config.ServerTCPPort, Config.ServerUDPPort);
        }
        catch (Exception e) {
            Logger.log(e);
        }

        // Listeners
        server.addListener(new Listener() {
            public void received (Connection c, Object object) {
                ServerConnection connection = (ServerConnection)c;

                if (object instanceof User) {

                    if (connection.user.getName() != null) return;

                    String name = ((User)object).getName();
                    if (name == null) return;

                    name = name.trim();
                    if (name.length() == 0) return;

                    connection.user = (User) object;

                    connection.user.setUid(connection.getID());

                    // Send message to all users. User connected.
                    userLoggedIn(connection);


                    return;
                }

                // Read commands
                if (object instanceof Config.commands) {

                    Config.commands command = (Config.commands) object;

                    switch (command) {
                        case getUserList:
                            sendUserList(connection);
                            break;
                        case startGame:
                            startNewGame(connection);
                            break;
                        case getOpenGames:
                            returnOpenGames(connection);
                            break;
                        case lost:
                            sendGameCommand(connection, Config.commands.won);
                            break;
                        case gray:
                            sendGameCommand(connection, Config.commands.gray);
                            break;
                    }
                }

                // Join game
                if (object instanceof JoinGameRequest) {
                    JoinGameRequest joinGame = (JoinGameRequest) object;
                    joinGame(connection, joinGame);
                }


            }
            public void disconnected (Connection c) {
                ServerConnection connection = (ServerConnection)c;
                if (connection.user != null) {
                    // User disconnected
                    userLoggedOut(connection);
                }
            }


        });

    }


    /*******************************************************************************************************************
     *
     * Server Functions
     *
     ******************************************************************************************************************/


    // Send User list
    private void sendUserList(ServerConnection c) {
        UserList userList = new UserList();
        for (Connection connection : server.getConnections()) {
            ServerConnection serverconnection = (ServerConnection) connection;

            userList.userList.add(serverconnection.getUser());

        }
        c.sendTCP(userList);
    }

    // Notify all users, new user connected
    private void userLoggedIn(ServerConnection c) {
        UserMessage message = new UserMessage(Config.commands.userConnected, c.getUser());

        for (Connection connection : server.getConnections()) {
            ServerConnection serverconnection = (ServerConnection) connection;
            serverconnection.sendTCP(message);
        }
    }

    // Notify all users, new user connected
    private void userLoggedOut(ServerConnection c) {
        UserMessage message = new UserMessage(Config.commands.userDisconnected, c.getUser());

        for (Connection connection : server.getConnections()) {
            ServerConnection serverconnection = (ServerConnection) connection;
            serverconnection.sendTCP(message);
        }
    }

    private void startNewGame(ServerConnection connection) {


        ArrayList<Game> gamesCopy = new ArrayList<Game>(games);
        for (Game game : gamesCopy) {
            if (game.getCreator() == connection.getUser() || game.getParticipant() == connection.getUser()) {
                games.remove(game);
            }
        }

        Game newGame = new Game(connection.getUser(), getGameID());
        this.games.add(newGame);

        connection.sendTCP(newGame);

    }

    private void returnOpenGames(ServerConnection connection) {

        OpenGames openGames = new OpenGames();

        for (Game game : games) {
            if (!game.getRunning() && game.getParticipant() == null) {
                openGames.openGames.add(game);
            }
        }

        connection.sendTCP(openGames);

    }

    private  void joinGame(ServerConnection connection, JoinGameRequest joinGameRequest) {
        for (Game game : games) {
            if (game.getID() == joinGameRequest.getGame().getID()) {
                if (!game.getRunning() && game.getParticipant() == null) {
                    game.setParticipant(connection.getUser());
                    game.setRunning(true);

                    for (Connection serverConnection : server.getConnections()) {
                        ServerConnection serverconnection = (ServerConnection) serverConnection;
                        if (serverconnection.getUser().getUID() == game.getParticipant().getUID() || serverconnection.getUser().getUID() == game.getCreator().getUID()) {
                            serverconnection.sendTCP(game);
                        }
                    }

                }
            }
        }
    }

    private void sendGameCommand(ServerConnection connection, Config.commands inputMessage) {
        UserMessage message = new UserMessage(inputMessage, connection.getUser());
        for (Game game : games) {
            if (game.getRunning() && (game.getCreator().getUID() == connection.getUser().getUID() || game.getParticipant().getUID() == connection.getUser().getUID())) {

                for (Connection serverConnection : server.getConnections()) {
                    ServerConnection serverconnection = (ServerConnection) serverConnection;

                    if (game.getCreator().getUID() == connection.getUser().getUID()){
                        // Send to part
                        if (serverconnection.getUser().getUID() == game.getParticipant().getUID()) {
                            serverconnection.sendTCP(message);
                        }
                    }
                    else {
                        //sent to creator
                        if (serverconnection.getUser().getUID() == game.getCreator().getUID()) {
                            serverconnection.sendTCP(message);
                        }
                    }
                }
            }
        }
    }



    /*******************************************************************************************************************
     *
     * Other Functions
     *
     ******************************************************************************************************************/

    private int getGameID() {
        this.GameIDs ++;
        return this.GameIDs;
    }

}
