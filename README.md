# Importing
## Maven
When using maven make sure to add the repository to your list in your pom.xml file
```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
        <!-- All your other repositories -->
    </repositories>
```
And then add it to your dependencies. Make sure to shadow as the library has to come with your plugin!
```xml
    <dependencies>
        <dependency>
            <groupId>com.github.MenelBOT</groupId>
            <artifactId>commandIndex</artifactId>
            <version>1.0.2</version> <!-- Or latest found on https://github.com/MenelBOT/commandIndex/releases --
        </dependency>
        <!-- All your other dependencies -->
    </dependencies>
```
## Gradle
I have never used the Groovy syntax, but adapting the Kotlin instructions to it should be trivial.<br>
When using gradle make sure to add the repository to your list in your build.gradle.kts file
```kts
repositories {
    maven("https://jitpack.io")
    // All your other repositories
}
```
And then add it to your dependencies. Make sure to shadow as the library has to come with your plugin!
```kts
dependencies {
    implementation("com.github.MenelBOT:commandIndex:1.0.2") //  Or latest found on https://github.com/MenelBOT/commandIndex/releases
    // All your other dependencies
}
```

# Usage
## Creating commands
To create a command with this library you must create a new class with a protected constructor and a public static factory method, as well as implementing the onTabComplete and onCommand functions.
Here is an example command that does absolutely nothing
```java
package your.domain.yourPlugin.commandsPackage;

import io.github.menelbot.commandIndex.LambdaExecutor;
import io.github.menelbot.commandIndex.LambdaTabCompleter;
import io.github.menelbot.commandIndex.LibCommand;
import io.github.menelbot.commandIndex.Permissions.BitField;
import io.github.menelbot.commandIndex.Permissions.Flags;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class FooCommand extends LibCommand {
    protected FooCommand(String name, List<String> aliases, String usage, String permission, LambdaExecutor executor, LambdaTabCompleter tabCompleter, BitField data) {
        super(name, aliases, usage, permission, executor, tabCompleter, data);
    }

    public static FooCommand create() {
        FooCommand instance = new FooCommand(
                "commandname",
                List.of(), // Or, List.of("foo", "bar")...
                "/usagehere <required> [optional]",
                "full.permission.string",
                null, // very important
                null, // these two must be null when instantiating
                new BitField(Arrays.asList( // For a full list of flags check the Flags enum.
                        Flags.SUPPORTS_PLAYER,
                        Flags.REQUIRES_PERMISSION
                ))
        );
        // These next two lines actually hook up the functions here to the command in-game, do not delete them
        instance.tabCompleter = new LambdaTabCompleter(instance::onTabComplete);
        instance.executor = new LambdaExecutor(instance::onCommand);

        return instance;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        return List.of();
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return true;
    }
}

```

## Registering commands
Then, to register your command, in your main plugin file, in the onEnable function call the CommandIndex and give it all the command instances you have written
```java
    public void onEnable() {
        // ...
        CommandIndex.registerCommands(this,
            FooCommand.create() // and all the other commands
        );
        // ...
    }
```
And don't forget to list them in your plugin.yml file
```yaml
commands:
  foo:
    description: Does things! (actually it doesn't)
    permission: full.permission.string # not required

# and if applicable 
permissions:
  full.permission.string:
    description: Allows you to run the foo command!
    default: true
```
