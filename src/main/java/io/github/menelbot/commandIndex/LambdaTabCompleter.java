package io.github.menelbot.commandIndex;


import io.github.menelbot.commandIndex.Predicates.TabCompletePredicate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LambdaTabCompleter implements TabCompleter {
    private final TabCompletePredicate<CommandSender, Command, String, String[]> tabCompleter;

    public LambdaTabCompleter(TabCompletePredicate<CommandSender, Command, String, String[]> tabCompleter) {
        this.tabCompleter = tabCompleter;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        return this.tabCompleter.test(sender, command, label, args);
    }
}
