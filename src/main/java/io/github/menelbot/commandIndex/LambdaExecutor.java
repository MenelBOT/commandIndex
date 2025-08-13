package io.github.menelbot.commandIndex;

import io.github.menelbot.commandIndex.Predicates.CommandPredicate;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;

public class LambdaExecutor implements CommandExecutor {
    private final CommandPredicate<CommandSender, Command, String, String[]> executor;

    public LambdaExecutor(CommandPredicate<CommandSender, Command, String, String[]> executor) {
        this.executor = executor;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        return this.executor.test(sender, command, label, args);
    }
}