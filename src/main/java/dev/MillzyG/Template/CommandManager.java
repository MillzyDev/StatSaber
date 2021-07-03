package dev.MillzyG.Template;

import dev.MillzyG.Template.command.CommandContext;
import dev.MillzyG.Template.command.ICommand;
import dev.MillzyG.Template.command.commands.Ping;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private static List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new Ping());
    }

    public static List<ICommand> getCommandClasses() {
        return commands;
    }

    private void addCommand(ICommand cmd)
    {
        boolean nameFound = commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound)
        {
            throw new IllegalArgumentException("Command already exists...");
        }

        commands.add(cmd);
    }

    @Nullable
    private ICommand getCommand(String search)
    {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands)
        {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower))
            {
                return cmd;
            }
        }

        return null;
    }

    void handle(GuildMessageReceivedEvent event) throws IOException {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Main.config.prefix), "")
                .split("\\s")
                ;

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);
        User user = event.getAuthor();

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            if (cmd.getArgsRequired() && args.isEmpty())
            {
                String reply = "<@!" + event.getAuthor().getId() + ">" + ", You didn't provide any arguments"
                        + "\n Usage: `" + Main.config.prefix + cmd.getName() + " " + cmd.getUsage() + "`";

                event.getChannel().sendMessage(reply).queue();
                return;
            }

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }
}
