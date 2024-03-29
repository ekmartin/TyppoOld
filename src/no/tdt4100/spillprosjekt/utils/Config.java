package no.tdt4100.spillprosjekt.utils;

import java.util.ArrayList;

/**
 * Created by eiriksylliaas on 08.02.14.
 */
public class Config {

    public static int ServerTCPPort = 54555;
    public static int ServerUDPPort = 54777;

    public static String ServerAddress = "ekmartin.no";

    public static float cellHeight = 32;
    public static float cellWidth = 32;

    public static int boardWidth = 13;
    public static int boardHeight = 19;

    public static float boardWidthFloat = (boardWidth+2)*cellWidth;
    public static float boardHeightFloat = (boardHeight+1)*cellHeight;

    public enum commands {
        getUserList,
        userConnected,
        userDisconnected,
        startGame,
        getOpenGames,
        findGame,
        foundGame,
        lost,
        won,
        gray,
        opponentLeft,
        startMultiplayerGame,
        deleteMyGames,
        connectionStatus
    }
    public static String[] wordlist;


}
