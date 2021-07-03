package dev.MillzyG.Template.command;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx) throws IOException;

    String getName();
    String getDescription();

    default Boolean getArgsRequired() { return false; }

    default String getUsage() {return null;}

    default Boolean getAccountRequired() { return false; }

    default List<String> getAliases() {
        return Arrays.asList();
    }
}
