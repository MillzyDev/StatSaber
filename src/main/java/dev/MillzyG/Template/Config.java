package dev.MillzyG.Template;

import com.google.gson.JsonObject;

public class Config {
    public String discordToken;
    public String prefix;

    public Config() {
        this.discordToken = "";
        this.prefix = "!";
    }
}
