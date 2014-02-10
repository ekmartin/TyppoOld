package no.tdt4100.spillprosjekt.utils;

import com.esotericsoftware.kryo.Kryo;
import no.tdt4100.spillprosjekt.objects.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by eiriksylliaas on 09.02.14.
 */
public class ServerInit {

    public static void mapClasses(Kryo kryo){

        kryo.register(Config.commands.class);
        kryo.register(User.class);
        kryo.register(UserList.class);
        kryo.register(UserMessage.class);
        kryo.register(ArrayList.class);
        kryo.register(Game.class);
        kryo.register(WordList.class);
        kryo.register(Word.class);
        kryo.register(Word.colors.class);
        kryo.register(Random.class);
        kryo.register(AtomicLong.class);
        kryo.register(Word[].class);
        kryo.register(OpenGames.class);
        kryo.register(JoinGameRequest.class);

    }

}
