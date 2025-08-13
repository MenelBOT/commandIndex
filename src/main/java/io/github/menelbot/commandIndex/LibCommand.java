package io.github.menelbot.commandIndex;

import io.github.menelbot.commandIndex.Permissions.BitField;

import java.util.List;

public abstract class LibCommand {
    protected String name;
    protected List<String> aliases;
    protected String usage;
    protected String permission;
    protected LambdaExecutor executor;
    protected LambdaTabCompleter tabCompleter;
    protected BitField data;

    protected LibCommand(
            String name,
            List<String> aliases,
            String usage,
            String permission,
            LambdaExecutor executor,
            LambdaTabCompleter tabCompleter,
            BitField data
    ) {
        this.name = name;
        this.aliases = aliases;
        this.usage = usage;
        this.permission = permission;
        this.executor = executor;
        this.tabCompleter = tabCompleter;
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public String getUsage() {
        return this.usage;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public String getPermission() {
        return this.permission;
    }

    public LambdaExecutor getExecutor() {
        return this.executor;
    }

    public LambdaTabCompleter getTabCompleter() {
        return this.tabCompleter;
    }

    public BitField getData() {
        return this.data;
    }
}
