/*
 * SPDX-License-Identifier: MPNRL-1.0
 *
 * Copyright (c) 2025 Menel
 *
 * This file is part of the CommandIndex library.
 *
 * Licensed under the Menel Permissive Non-Resale License, Version 1.0.
 * You may use, modify, and distribute this software freely, but you may not
 * sell or license it for a fee, except as a dependency of a larger work that
 * substantially extends its functionality.
 *
 * Full license text: https://github.com/MenelBOT/commandIndex/blob/main/LICENSE
 */
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
