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