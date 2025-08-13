package io.github.menelbot.commandIndex.Permissions;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class BitField {
    private int bitfield;

    public BitField(@Nullable List<Flags> initialFlags) {
        this.bitfield = 0b000000;
        if (!Objects.isNull(initialFlags)) {
            for (Flags flag : initialFlags)
                setFlag(flag, true);
        }
    }

    /**
     * Get the current state of the specified flag.
     *
     * @param flag The flag to check
     * @return True if the flag is set, false otherwise
     */
    public boolean getFlag(Flags flag) {
        return (bitfield & (1 << flag.getBitPosition())) != 0;
    }

    /**
     * Flips the state of the specified flag and returns its new value.
     *
     * @param flag The flag to flip.
     * @return The new state of the flag.
     */
    public boolean flipFlag(Flags flag) {
        bitfield ^= (1 << flag.getBitPosition());
        return getFlag(flag);
    }

    /**
     * Set the state of the specified flag.
     *
     * @param flag The flag to set.
     * @param newValue The new value for the flag.
     */
    public void setFlag(Flags flag, boolean newValue) {
        if (newValue) bitfield |= (1 << flag.getBitPosition());
        else bitfield &= ~(1 << flag.getBitPosition());
    }

    /**
     * Set the state of the specified flag.
     *
     * @param flag The flag to set.
     * @param newValue The new value for the flag.
     */
    public void setFlag(Flags flag, int newValue) {
        if (0 == newValue) bitfield &= ~(1 << flag.getBitPosition());
        else bitfield |= (1 << flag.getBitPosition());
    }

    /**
     * Clear all flags (set all bits to 0)
     */
    public void clear() {
        this.bitfield = 0b000000;
    }

    /**
     * Get the current bitfield value as an integer.
     *
     * @return The bitfield as an integer
     */
    public int getBitfieldValue() {
        return bitfield;
    }
}
