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
package io.github.menelbot.commandIndex.Permissions;

public enum Flags {
    SUPPORTS_BLOCK(0),
    SUPPORTS_CONSOLE(1),
    SUPPORTS_PLAYER(2),
    REQUIRES_PERMISSION(3),
    HAS_ALIASES(4),
    HAS_SUBCOMMANDS(5);

    private final int bitPosition;

    Flags(int bitPosition) {
        this.bitPosition = bitPosition;
    }

    public int getBitPosition() {
        return bitPosition;
    }
}

