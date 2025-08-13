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
package io.github.menelbot.commandIndex.Predicates;

@FunctionalInterface
public interface CommandPredicate<CS, C, S, SA> {
    boolean test(CS sender, C command, S label, SA args);
}
