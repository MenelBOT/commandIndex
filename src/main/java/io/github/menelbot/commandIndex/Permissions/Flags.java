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

