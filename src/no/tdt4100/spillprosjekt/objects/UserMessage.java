package no.tdt4100.spillprosjekt.objects;

import no.tdt4100.spillprosjekt.utils.Config;

public class UserMessage {

    public Config.commands command;
    public User user;

    public UserMessage() {

    }

    public UserMessage(Config.commands command, User user) {
        this.command = command;
        this.user = user;
    }
}
