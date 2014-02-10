package no.tdt4100.spillprosjekt.utils;

import com.esotericsoftware.kryo.Kryo;
import no.tdt4100.spillprosjekt.objects.User;
import no.tdt4100.spillprosjekt.objects.UserList;
import no.tdt4100.spillprosjekt.objects.UserMessage;

import java.util.ArrayList;

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

    }

}
