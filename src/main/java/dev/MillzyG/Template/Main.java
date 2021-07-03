package dev.MillzyG.Template;

import com.google.gson.JsonParser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.*;

public class Main {
    public static Config config;
    public JDA jda;

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            resultStringBuilder.append(br.read());
        }
        return resultStringBuilder.toString();
    }

    private Main() throws LoginException, IOException {
        config = new Config();

        jda = JDABuilder
                .createDefault(config.discordToken)
                .addEventListeners(new Listener())
                .build()
        ;
    }

    public static void main(String[] args) throws LoginException, IOException {
        new Main();
    }
}
