# CommandIndex

A lightweight command registration API for Paper/Spigot Minecraft plugins.
Easily create and manage commands without boilerplate.

---

## Importing

### Maven

Add the JitPack repository to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
    <!-- Your other repositories -->
</repositories>
```

Then add the dependency **(remember to shadow it into your plugin)**:

```xml
<dependencies>
    <dependency>
        <groupId>com.github.MenelBOT</groupId>
        <artifactId>commandIndex</artifactId>
        <version>1.0.2</version> <!-- Check https://github.com/MenelBOT/commandIndex/releases for latest -->
    </dependency>
    <!-- Your other dependencies -->
</dependencies>
```

---

### Gradle (Kotlin DSL)

Add the JitPack repository:

```kts
repositories {
    maven("https://jitpack.io")
    // Your other repositories
}
```

Add the dependency **(remember to shadow it into your plugin)**:

```kts
dependencies {
    implementation("com.github.MenelBOT:commandIndex:1.0.2") // Check releases for latest
    // Your other dependencies
}
```

*(If you use Groovy syntax, simply adapt these snippets — it’s a one-to-one translation.)*

---

## Usage

### Creating commands

To create a command with this library:

* Create a new class with a **protected constructor** and a **public static factory method**.
* Implement `onTabComplete` and `onCommand`.

Example:

```java
package your.domain.yourPlugin.commandsPackage;

import io.github.menelbot.commandIndex.*;
import io.github.menelbot.commandIndex.Permissions.BitField;
import io.github.menelbot.commandIndex.Permissions.Flags;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class FooCommand extends LibCommand {
    protected FooCommand(String name, List<String> aliases, String usage, String permission,
                         LambdaExecutor executor, LambdaTabCompleter tabCompleter, BitField data) {
        super(name, aliases, usage, permission, executor, tabCompleter, data);
    }

    public static FooCommand create() {
        FooCommand instance = new FooCommand(
                "commandname",
                List.of(), // or List.of("foo", "bar") for aliases
                "/usagehere <required> [optional]",
                "full.permission.string",
                null,
                null,
                new BitField(Arrays.asList(
                        Flags.SUPPORTS_PLAYER,
                        Flags.REQUIRES_PERMISSION
                ))
        );

        instance.tabCompleter = new LambdaTabCompleter(instance::onTabComplete);
        instance.executor = new LambdaExecutor(instance::onCommand);

        return instance;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                      @NotNull String label, String[] args) {
        return List.of();
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        return true;
    }
}
```

---

### Registering commands

In your main plugin class:

```java
@Override
public void onEnable() {
    CommandIndex.registerCommands(this,
        FooCommand.create() // and your other commands
    );
}

@Override
public void onDisable() {
    CommandIndex.unregisterAll(this);
}
```

Add them to your `plugin.yml`:

```yaml
commands:
  foo:
    description: Does things! (actually it doesn't)
    permission: full.permission.string

permissions:
  full.permission.string:
    description: Allows running the foo command
    default: true
```

---

## License

**Menel Permissive Non-Resale License (MPNRL-1.0)**
Copyright (c) 2025 Menel

You may use, modify, and distribute this library freely, **including in commercial plugins**,
**provided** that you do **not** sell or license this library itself for a fee, except as a
dependency of a larger work which substantially extends its functionality.

Plain English:

* ✅ Free plugins: fully allowed
* ✅ Paid plugins using this as a dependency: allowed
* ❌ Selling this library directly: not allowed
* ❌ Premium wrappers with minimal changes: not allowed

Full license text: [LICENSE](./LICENSE)

---
