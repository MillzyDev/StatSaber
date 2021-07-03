package dev.MillzyG.Template;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;

public class Listener extends ListenerAdapter {
    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.printf("Logged in as %#s", event.getJDA().getSelfUser());

        JDA jda = event.getJDA();
    }

    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        JDA jda = event.getJDA();

        Message message = event.getMessage();

        String prefix = Main.config.prefix;
        String raw = message.getContentRaw();

        if (!raw.startsWith(prefix) || (message.getAuthor().getId() == jda.getSelfUser().getId())) return;

        try {
            manager.handle(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
