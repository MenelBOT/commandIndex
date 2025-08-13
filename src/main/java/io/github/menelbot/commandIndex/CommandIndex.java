package io.github.menelbot.commandIndex;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.*;

import java.util.*;

public class CommandIndex {
    private static final WeakHashMap<JavaPlugin, Map<String, LibCommand>> isolatedCommands = new WeakHashMap<>();
    private static final Set<JavaPlugin> loaded = new HashSet<>();

    private static void loadCommands(@NotNull JavaPlugin plugin, Map<String, LibCommand> target, LibCommand @NotNull [] libCommands) {
        plugin.getLogger().info("Loading commands...");

        for (LibCommand libCommand : libCommands) {
            loadCommand(plugin, target, libCommand);
        }

        plugin.getLogger().info("Loaded all commands!");
        CommandIndex.loaded.add(plugin);
    }

    @ApiStatus.AvailableSince("1.0.0")
    @SuppressWarnings("unused")
    public static void registerCommands(JavaPlugin plugin, LibCommand... libCommands) {
        Map<String, LibCommand> commandMap = isolatedCommands.computeIfAbsent(plugin, _ -> new HashMap<>());

        plugin.getLogger().info(loaded.contains(plugin)
                ? "Commands loaded already, new commands will shadow if any name collisions happen"
                : "No commands loaded, loading...");

        loadCommands(plugin, commandMap, libCommands);

        plugin.getLogger().info("Registering loaded commands...");

        for (Map.Entry<String, LibCommand> entry : commandMap.entrySet()) {
            String name = entry.getKey();
            LibCommand libCommand = entry.getValue();

            PluginCommand cmd = plugin.getCommand(name);

            if (Objects.isNull(cmd)) {
                plugin.getLogger().severe("Command " + name + " could not be registered, as it is missing from plugin.yml");
                continue;
            }

            cmd.setExecutor((sender, bukkitCommand, label, args) -> Executor.execute(plugin, sender, bukkitCommand, label, args));
            cmd.setTabCompleter(libCommand.getTabCompleter());
        }
        plugin.getLogger().info("Loaded commands registered successfully!");
    }

    @ApiStatus.AvailableSince("1.0.0")
    @SuppressWarnings("unused")
    public static @Nullable LibCommand getCommand(JavaPlugin target, String name) {
        @Nullable Map<String, LibCommand> commandsForTarget = isolatedCommands.get(target);
        if (commandsForTarget == null)
            return null;

        return commandsForTarget.get(name.toLowerCase(Locale.ROOT));
    }

    @ApiStatus.AvailableSince("1.0.0")
    @SuppressWarnings("unused")
    @Contract(pure = true)
    public static @NotNull @UnmodifiableView Map<String, LibCommand> getCommands(JavaPlugin target) {
        Map<String, LibCommand> map = isolatedCommands.get(target);
        return map == null ? Collections.emptyMap() : Collections.unmodifiableMap(map);
    }

    @ApiStatus.AvailableSince("1.0.0")
    @SuppressWarnings("unused")
    public static void unregisterAll(JavaPlugin plugin) {
        @Nullable Map<String, LibCommand> commandsForTarget = isolatedCommands.get(plugin);

        if (commandsForTarget == null || commandsForTarget.isEmpty()) {
            plugin.getLogger().warning("Plugin " + plugin.getName() + " tried to unregister its commands, but none were registered!");
            return;
        }

        plugin.getLogger().info("Unregistering commands...");
        // Using iterator/while instead of for:of to allow removing current value without a ConcurrentModificationException
        Iterator<Map.Entry<String, LibCommand>> iterator = commandsForTarget.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, LibCommand> commandEntry = iterator.next();

            String name = commandEntry.getKey();

            PluginCommand pluginCommand = plugin.getCommand(name);
            if (pluginCommand == null) {
                plugin.getLogger().severe("Command " + name + " is missing from plugin.yml");
                continue;
            }

            pluginCommand.setTabCompleter(null);
            pluginCommand.setExecutor(null);
            plugin.getLogger().info("Unregistered command " + name);

            iterator.remove();
        }

        isolatedCommands.remove(plugin);
        loaded.remove(plugin);

        plugin.getLogger().info("Commands unregistered successfully!");
    }

    @ApiStatus.AvailableSince("1.0.0")
    @SuppressWarnings("unused")
    public static boolean isLoaded(@NotNull JavaPlugin target) {
        return loaded.contains(target);
    }

    @ApiStatus.AvailableSince("1.0.0")
    @SuppressWarnings("unused")
    public static @UnmodifiableView Set<JavaPlugin> getLoadedPlugins() {
        return Collections.unmodifiableSet(loaded);
    }

    private static void loadCommand(@NotNull JavaPlugin plugin, Map<String, LibCommand> target, LibCommand libCommand) {
        String key = libCommand.getName().toLowerCase(Locale.ROOT);
        @Nullable LibCommand prev =  target.put(key, libCommand);
        if (prev != null) {
            // Maybe make this an error instead, but for me a log works.
            plugin.getLogger().severe("The command " + key + " was already loaded before, overwriting with new command with the same name");
            plugin.getLogger().severe("Make sure this is intended!");
        }
        plugin.getLogger().info("Loaded /" + key);
    }
}
