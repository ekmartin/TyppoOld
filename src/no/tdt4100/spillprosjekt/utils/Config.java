package no.tdt4100.spillprosjekt.utils;

import java.util.ArrayList;

/**
 * Created by eiriksylliaas on 08.02.14.
 */
public class Config {

    public static int ServerTCPPort = 54555;
    public static int ServerUDPPort = 54777;
    public static String ServerAddress = "127.0.0.1";

    public static float cellHeight = 32;
    public static float cellWidth = 32;

    public static float boardWidth = 13*cellHeight;
    public static float boardHeight = 19*cellWidth;

    public static String[] WordList(){
        String[] list = {"AIDS", "Lollypop", "Mordi", "Råtte"};
        return list;
    }



}
