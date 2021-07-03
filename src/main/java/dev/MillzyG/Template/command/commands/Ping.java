package dev.MillzyG.Template.command.commands;

import dev.MillzyG.Template.command.CommandContext;
import dev.MillzyG.Template.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

import java.awt.*;
import java.io.IOException;

public class Ping implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws IOException {
        JDA jda = ctx.getJDA();

        EmbedBuilder eb = new EmbedBuilder();

        eb.setAuthor(ctx.getAuthor().getAsTag(), null, ctx.getAuthor().getAvatarUrl());
        eb.setColor(Color.orange);
        eb.setFooter("\uD83C\uDFD3 Pong!");

        jda.getRestPing().queue( (time) -> ctx.getChannel()
                .sendMessage(eb.setDescription("⏳ API Latency: `" + time + "ms`\n \uD83D\uDC93 WS Heartbeat: `"+ jda.getGatewayPing() + "ms`").build()).queue()
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Round trip delay";
    }
}
