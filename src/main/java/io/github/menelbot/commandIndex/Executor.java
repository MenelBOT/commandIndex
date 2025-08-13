package io.github.menelbot.commandIndex;

import io.github.menelbot.commandIndex.Permissions.BitField;
import io.github.menelbot.commandIndex.Permissions.Flags;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Executor {
    /**
     * Returns whether the command is registered.<br>If this function returns false
     * Bukkit will automatically send the usage message defined in plugin.yml to the sender
     * @param instance An instance of a {@link org.bukkit.plugin.java.JavaPlugin}
     * @param sender The sender of the command.
     * @param command An instance of org.bukkit.command.Command.
     * @param label The name of the command.
     * @param args The arguments passed to the command. In the command box, each individual argument is separated by a space.
     * @return Whether Bukkit should <strong>NOT</strong> send the usage of the command to the sender.
     */
    public static boolean execute(@NotNull JavaPlugin instance, @NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        @Nullable LibCommand cmd = CommandIndex.getCommand(instance, command.getName());

        if (Objects.isNull(cmd)) {
            return false;
        }

        BitField cmdData = cmd.getData();

        // Test sender instance
        if (!checkSenderType(instance, sender, cmdData)) return true;

        // Test sender permissions
        if (!isSenderPermitted(sender, cmd)) {
            sender.sendMessage(ChatColor.RED + "You are not permitted to run this command!");
            return true;
        }

        return cmd.getExecutor().onCommand(sender, command, label, args);
    }

    /**
     * Returns whether the passed sender is supported by the command.
     * Note that this does not mean that the sender is PERMITTED to run the command, only that
     * the command supports being run by that sender.
     * <br>
     * This function will also send a message to the sender if the command does not support them
     * so there is no need to do it again.
     *
     * @param instance An instance of a {@link org.bukkit.plugin.java.JavaPlugin}
     * @param sender The sender of the command.
     * @param cmdData The BitField data of the invoked command.
     * @return Whether the command supports the specified sender
     */
    private static boolean checkSenderType(@NotNull JavaPlugin instance, CommandSender sender, BitField cmdData) {
        if (sender instanceof BlockCommandSender blockSender && !cmdData.getFlag(Flags.SUPPORTS_BLOCK)) {
            instance.getLogger().info("This command cannot be ran from a command block!");
            instance.getLogger().info("Ran from command block at " + blockSender.getBlock().getX() + " " + blockSender.getBlock().getY() + " " + blockSender.getBlock().getZ());
            return false;
        }

        if (sender instanceof ConsoleCommandSender && !cmdData.getFlag(Flags.SUPPORTS_CONSOLE)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be ran from the console!");
            return false;
        }

        if (sender instanceof Player && !cmdData.getFlag(Flags.SUPPORTS_PLAYER)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be ran as a player!");
            return false;
        }

        return true;
    }

    /**
     * Returns whether the sender of the command is permitted to run the command. Can only return false for `Player`s
     * <br>
     * Unlike {@link Executor#checkSenderType}, this function does not notify the sender of insufficient permissions.
     * This has to be done manually depending on the value returned.
     * @param sender The sender of the command.
     * @param cmd The invoked {@link LibCommand Command}.
     * @return Whether the sender is permitted to run the command.
     */
    private static boolean isSenderPermitted(CommandSender sender, LibCommand cmd) {
        // If the command is not allowed to run as a command block it should not support command blocks
        // and thus this code should only be reached by a permitted command block.
        //
        // Also, if the command is run as console, no permission checks should happen, as the console
        // always has permission to run any command by design.
        //
        // Thus, the only sender instance that should have its permissions checked is a Player.

        return (
                !(sender instanceof Player)
                        || (!cmd.getData().getFlag(Flags.REQUIRES_PERMISSION) || sender.hasPermission(cmd.getPermission()))
        );
    }
}

